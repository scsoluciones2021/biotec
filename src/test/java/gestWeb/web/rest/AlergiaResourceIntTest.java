package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.Alergia;
import gestWeb.repository.AlergiaRepository;
import gestWeb.service.AlergiaService;
import gestWeb.service.dto.AlergiaDTO;
import gestWeb.service.mapper.AlergiaMapper;
import gestWeb.web.rest.errors.ExceptionTranslator;

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


import static gestWeb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AlergiaResource REST controller.
 *
 * @see AlergiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class AlergiaResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private AlergiaRepository alergiaRepository;


    @Autowired
    private AlergiaMapper alergiaMapper;
    

    @Autowired
    private AlergiaService alergiaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlergiaMockMvc;

    private Alergia alergia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlergiaResource alergiaResource = new AlergiaResource(alergiaService);
        this.restAlergiaMockMvc = MockMvcBuilders.standaloneSetup(alergiaResource)
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
    public static Alergia createEntity(EntityManager em) {
        Alergia alergia = new Alergia()
            .valor(DEFAULT_VALOR);
        return alergia;
    }

    @Before
    public void initTest() {
        alergia = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlergia() throws Exception {
        int databaseSizeBeforeCreate = alergiaRepository.findAll().size();

        // Create the Alergia
        AlergiaDTO alergiaDTO = alergiaMapper.toDto(alergia);
        restAlergiaMockMvc.perform(post("/api/alergias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alergiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeCreate + 1);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createAlergiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alergiaRepository.findAll().size();

        // Create the Alergia with an existing ID
        alergia.setId(1L);
        AlergiaDTO alergiaDTO = alergiaMapper.toDto(alergia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlergiaMockMvc.perform(post("/api/alergias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alergiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = alergiaRepository.findAll().size();
        // set the field null
        alergia.setValor(null);

        // Create the Alergia, which fails.
        AlergiaDTO alergiaDTO = alergiaMapper.toDto(alergia);

        restAlergiaMockMvc.perform(post("/api/alergias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alergiaDTO)))
            .andExpect(status().isBadRequest());

        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlergias() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        // Get all the alergiaList
        restAlergiaMockMvc.perform(get("/api/alergias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alergia.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        // Get the alergia
        restAlergiaMockMvc.perform(get("/api/alergias/{id}", alergia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alergia.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlergia() throws Exception {
        // Get the alergia
        restAlergiaMockMvc.perform(get("/api/alergias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();

        // Update the alergia
        Alergia updatedAlergia = alergiaRepository.findById(alergia.getId()).get();
        // Disconnect from session so that the updates on updatedAlergia are not directly saved in db
        em.detach(updatedAlergia);
        updatedAlergia
            .valor(UPDATED_VALOR);
        AlergiaDTO alergiaDTO = alergiaMapper.toDto(updatedAlergia);

        restAlergiaMockMvc.perform(put("/api/alergias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alergiaDTO)))
            .andExpect(status().isOk());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
        Alergia testAlergia = alergiaList.get(alergiaList.size() - 1);
        assertThat(testAlergia.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAlergia() throws Exception {
        int databaseSizeBeforeUpdate = alergiaRepository.findAll().size();

        // Create the Alergia
        AlergiaDTO alergiaDTO = alergiaMapper.toDto(alergia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlergiaMockMvc.perform(put("/api/alergias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alergiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alergia in the database
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlergia() throws Exception {
        // Initialize the database
        alergiaRepository.saveAndFlush(alergia);

        int databaseSizeBeforeDelete = alergiaRepository.findAll().size();

        // Get the alergia
        restAlergiaMockMvc.perform(delete("/api/alergias/{id}", alergia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alergia> alergiaList = alergiaRepository.findAll();
        assertThat(alergiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alergia.class);
        Alergia alergia1 = new Alergia();
        alergia1.setId(1L);
        Alergia alergia2 = new Alergia();
        alergia2.setId(alergia1.getId());
        assertThat(alergia1).isEqualTo(alergia2);
        alergia2.setId(2L);
        assertThat(alergia1).isNotEqualTo(alergia2);
        alergia1.setId(null);
        assertThat(alergia1).isNotEqualTo(alergia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlergiaDTO.class);
        AlergiaDTO alergiaDTO1 = new AlergiaDTO();
        alergiaDTO1.setId(1L);
        AlergiaDTO alergiaDTO2 = new AlergiaDTO();
        assertThat(alergiaDTO1).isNotEqualTo(alergiaDTO2);
        alergiaDTO2.setId(alergiaDTO1.getId());
        assertThat(alergiaDTO1).isEqualTo(alergiaDTO2);
        alergiaDTO2.setId(2L);
        assertThat(alergiaDTO1).isNotEqualTo(alergiaDTO2);
        alergiaDTO1.setId(null);
        assertThat(alergiaDTO1).isNotEqualTo(alergiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alergiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alergiaMapper.fromId(null)).isNull();
    }
}
