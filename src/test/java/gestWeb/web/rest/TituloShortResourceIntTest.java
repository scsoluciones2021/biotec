package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.TituloShort;
import gestWeb.repository.TituloShortRepository;
import gestWeb.service.TituloShortService;
import gestWeb.service.dto.TituloShortDTO;
import gestWeb.service.mapper.TituloShortMapper;
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
 * Test class for the TituloShortResource REST controller.
 *
 * @see TituloShortResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class TituloShortResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private TituloShortRepository tituloShortRepository;


    @Autowired
    private TituloShortMapper tituloShortMapper;
    

    @Autowired
    private TituloShortService tituloShortService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTituloShortMockMvc;

    private TituloShort tituloShort;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TituloShortResource tituloShortResource = new TituloShortResource(tituloShortService);
        this.restTituloShortMockMvc = MockMvcBuilders.standaloneSetup(tituloShortResource)
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
    public static TituloShort createEntity(EntityManager em) {
        TituloShort tituloShort = new TituloShort()
            .valor(DEFAULT_VALOR);
        return tituloShort;
    }

    @Before
    public void initTest() {
        tituloShort = createEntity(em);
    }

    @Test
    @Transactional
    public void createTituloShort() throws Exception {
        int databaseSizeBeforeCreate = tituloShortRepository.findAll().size();

        // Create the TituloShort
        TituloShortDTO tituloShortDTO = tituloShortMapper.toDto(tituloShort);
        restTituloShortMockMvc.perform(post("/api/titulo-shorts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloShortDTO)))
            .andExpect(status().isCreated());

        // Validate the TituloShort in the database
        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeCreate + 1);
        TituloShort testTituloShort = tituloShortList.get(tituloShortList.size() - 1);
        assertThat(testTituloShort.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createTituloShortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tituloShortRepository.findAll().size();

        // Create the TituloShort with an existing ID
        tituloShort.setId(1L);
        TituloShortDTO tituloShortDTO = tituloShortMapper.toDto(tituloShort);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTituloShortMockMvc.perform(post("/api/titulo-shorts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloShortDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TituloShort in the database
        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloShortRepository.findAll().size();
        // set the field null
        tituloShort.setValor(null);

        // Create the TituloShort, which fails.
        TituloShortDTO tituloShortDTO = tituloShortMapper.toDto(tituloShort);

        restTituloShortMockMvc.perform(post("/api/titulo-shorts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloShortDTO)))
            .andExpect(status().isBadRequest());

        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTituloShorts() throws Exception {
        // Initialize the database
        tituloShortRepository.saveAndFlush(tituloShort);

        // Get all the tituloShortList
        restTituloShortMockMvc.perform(get("/api/titulo-shorts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tituloShort.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getTituloShort() throws Exception {
        // Initialize the database
        tituloShortRepository.saveAndFlush(tituloShort);

        // Get the tituloShort
        restTituloShortMockMvc.perform(get("/api/titulo-shorts/{id}", tituloShort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tituloShort.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTituloShort() throws Exception {
        // Get the tituloShort
        restTituloShortMockMvc.perform(get("/api/titulo-shorts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTituloShort() throws Exception {
        // Initialize the database
        tituloShortRepository.saveAndFlush(tituloShort);

        int databaseSizeBeforeUpdate = tituloShortRepository.findAll().size();

        // Update the tituloShort
        TituloShort updatedTituloShort = tituloShortRepository.findById(tituloShort.getId()).get();
        // Disconnect from session so that the updates on updatedTituloShort are not directly saved in db
        em.detach(updatedTituloShort);
        updatedTituloShort
            .valor(UPDATED_VALOR);
        TituloShortDTO tituloShortDTO = tituloShortMapper.toDto(updatedTituloShort);

        restTituloShortMockMvc.perform(put("/api/titulo-shorts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloShortDTO)))
            .andExpect(status().isOk());

        // Validate the TituloShort in the database
        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeUpdate);
        TituloShort testTituloShort = tituloShortList.get(tituloShortList.size() - 1);
        assertThat(testTituloShort.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTituloShort() throws Exception {
        int databaseSizeBeforeUpdate = tituloShortRepository.findAll().size();

        // Create the TituloShort
        TituloShortDTO tituloShortDTO = tituloShortMapper.toDto(tituloShort);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTituloShortMockMvc.perform(put("/api/titulo-shorts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloShortDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TituloShort in the database
        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTituloShort() throws Exception {
        // Initialize the database
        tituloShortRepository.saveAndFlush(tituloShort);

        int databaseSizeBeforeDelete = tituloShortRepository.findAll().size();

        // Get the tituloShort
        restTituloShortMockMvc.perform(delete("/api/titulo-shorts/{id}", tituloShort.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TituloShort> tituloShortList = tituloShortRepository.findAll();
        assertThat(tituloShortList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TituloShort.class);
        TituloShort tituloShort1 = new TituloShort();
        tituloShort1.setId(1L);
        TituloShort tituloShort2 = new TituloShort();
        tituloShort2.setId(tituloShort1.getId());
        assertThat(tituloShort1).isEqualTo(tituloShort2);
        tituloShort2.setId(2L);
        assertThat(tituloShort1).isNotEqualTo(tituloShort2);
        tituloShort1.setId(null);
        assertThat(tituloShort1).isNotEqualTo(tituloShort2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TituloShortDTO.class);
        TituloShortDTO tituloShortDTO1 = new TituloShortDTO();
        tituloShortDTO1.setId(1L);
        TituloShortDTO tituloShortDTO2 = new TituloShortDTO();
        assertThat(tituloShortDTO1).isNotEqualTo(tituloShortDTO2);
        tituloShortDTO2.setId(tituloShortDTO1.getId());
        assertThat(tituloShortDTO1).isEqualTo(tituloShortDTO2);
        tituloShortDTO2.setId(2L);
        assertThat(tituloShortDTO1).isNotEqualTo(tituloShortDTO2);
        tituloShortDTO1.setId(null);
        assertThat(tituloShortDTO1).isNotEqualTo(tituloShortDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tituloShortMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tituloShortMapper.fromId(null)).isNull();
    }
}
