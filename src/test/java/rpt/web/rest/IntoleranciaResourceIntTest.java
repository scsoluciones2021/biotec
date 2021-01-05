package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Intolerancia;
import rpt.repository.IntoleranciaRepository;
import rpt.service.IntoleranciaService;
import rpt.service.dto.IntoleranciaDTO;
import rpt.service.mapper.IntoleranciaMapper;
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
 * Test class for the IntoleranciaResource REST controller.
 *
 * @see IntoleranciaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class IntoleranciaResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private IntoleranciaRepository intoleranciaRepository;


    @Autowired
    private IntoleranciaMapper intoleranciaMapper;
    

    @Autowired
    private IntoleranciaService intoleranciaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntoleranciaMockMvc;

    private Intolerancia intolerancia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntoleranciaResource intoleranciaResource = new IntoleranciaResource(intoleranciaService);
        this.restIntoleranciaMockMvc = MockMvcBuilders.standaloneSetup(intoleranciaResource)
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
    public static Intolerancia createEntity(EntityManager em) {
        Intolerancia intolerancia = new Intolerancia()
            .valor(DEFAULT_VALOR);
        return intolerancia;
    }

    @Before
    public void initTest() {
        intolerancia = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntolerancia() throws Exception {
        int databaseSizeBeforeCreate = intoleranciaRepository.findAll().size();

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);
        restIntoleranciaMockMvc.perform(post("/api/intolerancias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeCreate + 1);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createIntoleranciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intoleranciaRepository.findAll().size();

        // Create the Intolerancia with an existing ID
        intolerancia.setId(1L);
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntoleranciaMockMvc.perform(post("/api/intolerancias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = intoleranciaRepository.findAll().size();
        // set the field null
        intolerancia.setValor(null);

        // Create the Intolerancia, which fails.
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        restIntoleranciaMockMvc.perform(post("/api/intolerancias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO)))
            .andExpect(status().isBadRequest());

        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntolerancias() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        // Get all the intoleranciaList
        restIntoleranciaMockMvc.perform(get("/api/intolerancias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intolerancia.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        // Get the intolerancia
        restIntoleranciaMockMvc.perform(get("/api/intolerancias/{id}", intolerancia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intolerancia.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingIntolerancia() throws Exception {
        // Get the intolerancia
        restIntoleranciaMockMvc.perform(get("/api/intolerancias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();

        // Update the intolerancia
        Intolerancia updatedIntolerancia = intoleranciaRepository.findById(intolerancia.getId()).get();
        // Disconnect from session so that the updates on updatedIntolerancia are not directly saved in db
        em.detach(updatedIntolerancia);
        updatedIntolerancia
            .valor(UPDATED_VALOR);
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(updatedIntolerancia);

        restIntoleranciaMockMvc.perform(put("/api/intolerancias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO)))
            .andExpect(status().isOk());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
        Intolerancia testIntolerancia = intoleranciaList.get(intoleranciaList.size() - 1);
        assertThat(testIntolerancia.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingIntolerancia() throws Exception {
        int databaseSizeBeforeUpdate = intoleranciaRepository.findAll().size();

        // Create the Intolerancia
        IntoleranciaDTO intoleranciaDTO = intoleranciaMapper.toDto(intolerancia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntoleranciaMockMvc.perform(put("/api/intolerancias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intoleranciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intolerancia in the database
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntolerancia() throws Exception {
        // Initialize the database
        intoleranciaRepository.saveAndFlush(intolerancia);

        int databaseSizeBeforeDelete = intoleranciaRepository.findAll().size();

        // Get the intolerancia
        restIntoleranciaMockMvc.perform(delete("/api/intolerancias/{id}", intolerancia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Intolerancia> intoleranciaList = intoleranciaRepository.findAll();
        assertThat(intoleranciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intolerancia.class);
        Intolerancia intolerancia1 = new Intolerancia();
        intolerancia1.setId(1L);
        Intolerancia intolerancia2 = new Intolerancia();
        intolerancia2.setId(intolerancia1.getId());
        assertThat(intolerancia1).isEqualTo(intolerancia2);
        intolerancia2.setId(2L);
        assertThat(intolerancia1).isNotEqualTo(intolerancia2);
        intolerancia1.setId(null);
        assertThat(intolerancia1).isNotEqualTo(intolerancia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntoleranciaDTO.class);
        IntoleranciaDTO intoleranciaDTO1 = new IntoleranciaDTO();
        intoleranciaDTO1.setId(1L);
        IntoleranciaDTO intoleranciaDTO2 = new IntoleranciaDTO();
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
        intoleranciaDTO2.setId(intoleranciaDTO1.getId());
        assertThat(intoleranciaDTO1).isEqualTo(intoleranciaDTO2);
        intoleranciaDTO2.setId(2L);
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
        intoleranciaDTO1.setId(null);
        assertThat(intoleranciaDTO1).isNotEqualTo(intoleranciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(intoleranciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(intoleranciaMapper.fromId(null)).isNull();
    }
}
