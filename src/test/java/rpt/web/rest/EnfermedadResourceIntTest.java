package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.Enfermedad;
import rpt.repository.EnfermedadRepository;
import rpt.service.EnfermedadService;
import rpt.service.dto.EnfermedadDTO;
import rpt.service.mapper.EnfermedadMapper;
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
 * Test class for the EnfermedadResource REST controller.
 *
 * @see EnfermedadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class EnfermedadResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private EnfermedadRepository enfermedadRepository;


    @Autowired
    private EnfermedadMapper enfermedadMapper;
    

    @Autowired
    private EnfermedadService enfermedadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnfermedadMockMvc;

    private Enfermedad enfermedad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnfermedadResource enfermedadResource = new EnfermedadResource(enfermedadService);
        this.restEnfermedadMockMvc = MockMvcBuilders.standaloneSetup(enfermedadResource)
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
    public static Enfermedad createEntity(EntityManager em) {
        Enfermedad enfermedad = new Enfermedad()
            .valor(DEFAULT_VALOR);
        return enfermedad;
    }

    @Before
    public void initTest() {
        enfermedad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnfermedad() throws Exception {
        int databaseSizeBeforeCreate = enfermedadRepository.findAll().size();

        // Create the Enfermedad
        EnfermedadDTO enfermedadDTO = enfermedadMapper.toDto(enfermedad);
        restEnfermedadMockMvc.perform(post("/api/enfermedads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermedadDTO)))
            .andExpect(status().isCreated());

        // Validate the Enfermedad in the database
        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeCreate + 1);
        Enfermedad testEnfermedad = enfermedadList.get(enfermedadList.size() - 1);
        assertThat(testEnfermedad.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createEnfermedadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enfermedadRepository.findAll().size();

        // Create the Enfermedad with an existing ID
        enfermedad.setId(1L);
        EnfermedadDTO enfermedadDTO = enfermedadMapper.toDto(enfermedad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfermedadMockMvc.perform(post("/api/enfermedads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermedadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enfermedad in the database
        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfermedadRepository.findAll().size();
        // set the field null
        enfermedad.setValor(null);

        // Create the Enfermedad, which fails.
        EnfermedadDTO enfermedadDTO = enfermedadMapper.toDto(enfermedad);

        restEnfermedadMockMvc.perform(post("/api/enfermedads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermedadDTO)))
            .andExpect(status().isBadRequest());

        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnfermedads() throws Exception {
        // Initialize the database
        enfermedadRepository.saveAndFlush(enfermedad);

        // Get all the enfermedadList
        restEnfermedadMockMvc.perform(get("/api/enfermedads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfermedad.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getEnfermedad() throws Exception {
        // Initialize the database
        enfermedadRepository.saveAndFlush(enfermedad);

        // Get the enfermedad
        restEnfermedadMockMvc.perform(get("/api/enfermedads/{id}", enfermedad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enfermedad.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEnfermedad() throws Exception {
        // Get the enfermedad
        restEnfermedadMockMvc.perform(get("/api/enfermedads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnfermedad() throws Exception {
        // Initialize the database
        enfermedadRepository.saveAndFlush(enfermedad);

        int databaseSizeBeforeUpdate = enfermedadRepository.findAll().size();

        // Update the enfermedad
        Enfermedad updatedEnfermedad = enfermedadRepository.findById(enfermedad.getId()).get();
        // Disconnect from session so that the updates on updatedEnfermedad are not directly saved in db
        em.detach(updatedEnfermedad);
        updatedEnfermedad
            .valor(UPDATED_VALOR);
        EnfermedadDTO enfermedadDTO = enfermedadMapper.toDto(updatedEnfermedad);

        restEnfermedadMockMvc.perform(put("/api/enfermedads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermedadDTO)))
            .andExpect(status().isOk());

        // Validate the Enfermedad in the database
        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeUpdate);
        Enfermedad testEnfermedad = enfermedadList.get(enfermedadList.size() - 1);
        assertThat(testEnfermedad.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEnfermedad() throws Exception {
        int databaseSizeBeforeUpdate = enfermedadRepository.findAll().size();

        // Create the Enfermedad
        EnfermedadDTO enfermedadDTO = enfermedadMapper.toDto(enfermedad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnfermedadMockMvc.perform(put("/api/enfermedads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermedadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enfermedad in the database
        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnfermedad() throws Exception {
        // Initialize the database
        enfermedadRepository.saveAndFlush(enfermedad);

        int databaseSizeBeforeDelete = enfermedadRepository.findAll().size();

        // Get the enfermedad
        restEnfermedadMockMvc.perform(delete("/api/enfermedads/{id}", enfermedad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enfermedad> enfermedadList = enfermedadRepository.findAll();
        assertThat(enfermedadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enfermedad.class);
        Enfermedad enfermedad1 = new Enfermedad();
        enfermedad1.setId(1L);
        Enfermedad enfermedad2 = new Enfermedad();
        enfermedad2.setId(enfermedad1.getId());
        assertThat(enfermedad1).isEqualTo(enfermedad2);
        enfermedad2.setId(2L);
        assertThat(enfermedad1).isNotEqualTo(enfermedad2);
        enfermedad1.setId(null);
        assertThat(enfermedad1).isNotEqualTo(enfermedad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnfermedadDTO.class);
        EnfermedadDTO enfermedadDTO1 = new EnfermedadDTO();
        enfermedadDTO1.setId(1L);
        EnfermedadDTO enfermedadDTO2 = new EnfermedadDTO();
        assertThat(enfermedadDTO1).isNotEqualTo(enfermedadDTO2);
        enfermedadDTO2.setId(enfermedadDTO1.getId());
        assertThat(enfermedadDTO1).isEqualTo(enfermedadDTO2);
        enfermedadDTO2.setId(2L);
        assertThat(enfermedadDTO1).isNotEqualTo(enfermedadDTO2);
        enfermedadDTO1.setId(null);
        assertThat(enfermedadDTO1).isNotEqualTo(enfermedadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enfermedadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enfermedadMapper.fromId(null)).isNull();
    }
}
