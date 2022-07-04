package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.Ejercicio;
import rpt.repository.EjercicioRepository;
import rpt.service.EjercicioService;
import rpt.service.dto.EjercicioDTO;
import rpt.service.mapper.EjercicioMapper;
import rpt.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static rpt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EjercicioResource REST controller.
 *
 * @see EjercicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class EjercicioResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private EjercicioRepository ejercicioRepository;


    @Autowired
    private EjercicioMapper ejercicioMapper;
    

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEjercicioMockMvc;

    private Ejercicio ejercicio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EjercicioResource ejercicioResource = new EjercicioResource(ejercicioService);
        this.restEjercicioMockMvc = MockMvcBuilders.standaloneSetup(ejercicioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ejercicio createEntity(EntityManager em) {
        Ejercicio ejercicio = new Ejercicio()
            .valor(DEFAULT_VALOR);
        return ejercicio;
    }

    @Before
    public void initTest() {
        ejercicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createEjercicio() throws Exception {
        int databaseSizeBeforeCreate = ejercicioRepository.findAll().size();

        // Create the Ejercicio
        EjercicioDTO ejercicioDTO = ejercicioMapper.toDto(ejercicio);
        restEjercicioMockMvc.perform(post("/api/ejercicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejercicioDTO)))
            .andExpect(status().isCreated());

        // Validate the Ejercicio in the database
        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeCreate + 1);
        Ejercicio testEjercicio = ejercicioList.get(ejercicioList.size() - 1);
        assertThat(testEjercicio.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createEjercicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ejercicioRepository.findAll().size();

        // Create the Ejercicio with an existing ID
        ejercicio.setId(1L);
        EjercicioDTO ejercicioDTO = ejercicioMapper.toDto(ejercicio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEjercicioMockMvc.perform(post("/api/ejercicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejercicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ejercicio in the database
        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = ejercicioRepository.findAll().size();
        // set the field null
        ejercicio.setValor(null);

        // Create the Ejercicio, which fails.
        EjercicioDTO ejercicioDTO = ejercicioMapper.toDto(ejercicio);

        restEjercicioMockMvc.perform(post("/api/ejercicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejercicioDTO)))
            .andExpect(status().isBadRequest());

        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEjercicios() throws Exception {
        // Initialize the database
        ejercicioRepository.saveAndFlush(ejercicio);

        // Get all the ejercicioList
        restEjercicioMockMvc.perform(get("/api/ejercicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ejercicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getEjercicio() throws Exception {
        // Initialize the database
        ejercicioRepository.saveAndFlush(ejercicio);

        // Get the ejercicio
        restEjercicioMockMvc.perform(get("/api/ejercicios/{id}", ejercicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ejercicio.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEjercicio() throws Exception {
        // Get the ejercicio
        restEjercicioMockMvc.perform(get("/api/ejercicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEjercicio() throws Exception {
        // Initialize the database
        ejercicioRepository.saveAndFlush(ejercicio);

        int databaseSizeBeforeUpdate = ejercicioRepository.findAll().size();

        // Update the ejercicio
        Ejercicio updatedEjercicio = ejercicioRepository.findById(ejercicio.getId()).get();
        // Disconnect from session so that the updates on updatedEjercicio are not directly saved in db
        em.detach(updatedEjercicio);
        updatedEjercicio
            .valor(UPDATED_VALOR);
        EjercicioDTO ejercicioDTO = ejercicioMapper.toDto(updatedEjercicio);

        restEjercicioMockMvc.perform(put("/api/ejercicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejercicioDTO)))
            .andExpect(status().isOk());

        // Validate the Ejercicio in the database
        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeUpdate);
        Ejercicio testEjercicio = ejercicioList.get(ejercicioList.size() - 1);
        assertThat(testEjercicio.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEjercicio() throws Exception {
        int databaseSizeBeforeUpdate = ejercicioRepository.findAll().size();

        // Create the Ejercicio
        EjercicioDTO ejercicioDTO = ejercicioMapper.toDto(ejercicio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEjercicioMockMvc.perform(put("/api/ejercicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejercicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ejercicio in the database
        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEjercicio() throws Exception {
        // Initialize the database
        ejercicioRepository.saveAndFlush(ejercicio);

        int databaseSizeBeforeDelete = ejercicioRepository.findAll().size();

        // Get the ejercicio
        restEjercicioMockMvc.perform(delete("/api/ejercicios/{id}", ejercicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ejercicio> ejercicioList = ejercicioRepository.findAll();
        assertThat(ejercicioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ejercicio.class);
        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setId(1L);
        Ejercicio ejercicio2 = new Ejercicio();
        ejercicio2.setId(ejercicio1.getId());
        assertThat(ejercicio1).isEqualTo(ejercicio2);
        ejercicio2.setId(2L);
        assertThat(ejercicio1).isNotEqualTo(ejercicio2);
        ejercicio1.setId(null);
        assertThat(ejercicio1).isNotEqualTo(ejercicio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EjercicioDTO.class);
        EjercicioDTO ejercicioDTO1 = new EjercicioDTO();
        ejercicioDTO1.setId(1L);
        EjercicioDTO ejercicioDTO2 = new EjercicioDTO();
        assertThat(ejercicioDTO1).isNotEqualTo(ejercicioDTO2);
        ejercicioDTO2.setId(ejercicioDTO1.getId());
        assertThat(ejercicioDTO1).isEqualTo(ejercicioDTO2);
        ejercicioDTO2.setId(2L);
        assertThat(ejercicioDTO1).isNotEqualTo(ejercicioDTO2);
        ejercicioDTO1.setId(null);
        assertThat(ejercicioDTO1).isNotEqualTo(ejercicioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ejercicioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ejercicioMapper.fromId(null)).isNull();
    }
}
