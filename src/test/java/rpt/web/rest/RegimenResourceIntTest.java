package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Regimen;
import rpt.repository.RegimenRepository;
import rpt.service.RegimenService;
import rpt.service.dto.RegimenDTO;
import rpt.service.mapper.RegimenMapper;
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
 * Test class for the RegimenResource REST controller.
 *
 * @see RegimenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class RegimenResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private RegimenRepository regimenRepository;


    @Autowired
    private RegimenMapper regimenMapper;
    

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegimenMockMvc;

    private Regimen regimen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimenResource regimenResource = new RegimenResource(regimenService);
        this.restRegimenMockMvc = MockMvcBuilders.standaloneSetup(regimenResource)
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
    public static Regimen createEntity(EntityManager em) {
        Regimen regimen = new Regimen()
            .valor(DEFAULT_VALOR);
        return regimen;
    }

    @Before
    public void initTest() {
        regimen = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegimen() throws Exception {
        int databaseSizeBeforeCreate = regimenRepository.findAll().size();

        // Create the Regimen
        RegimenDTO regimenDTO = regimenMapper.toDto(regimen);
        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimenDTO)))
            .andExpect(status().isCreated());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeCreate + 1);
        Regimen testRegimen = regimenList.get(regimenList.size() - 1);
        assertThat(testRegimen.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createRegimenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimenRepository.findAll().size();

        // Create the Regimen with an existing ID
        regimen.setId(1L);
        RegimenDTO regimenDTO = regimenMapper.toDto(regimen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimenRepository.findAll().size();
        // set the field null
        regimen.setValor(null);

        // Create the Regimen, which fails.
        RegimenDTO regimenDTO = regimenMapper.toDto(regimen);

        restRegimenMockMvc.perform(post("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimenDTO)))
            .andExpect(status().isBadRequest());

        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        // Get all the regimenList
        restRegimenMockMvc.perform(get("/api/regimen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimen.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        // Get the regimen
        restRegimenMockMvc.perform(get("/api/regimen/{id}", regimen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regimen.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRegimen() throws Exception {
        // Get the regimen
        restRegimenMockMvc.perform(get("/api/regimen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        int databaseSizeBeforeUpdate = regimenRepository.findAll().size();

        // Update the regimen
        Regimen updatedRegimen = regimenRepository.findById(regimen.getId()).get();
        // Disconnect from session so that the updates on updatedRegimen are not directly saved in db
        em.detach(updatedRegimen);
        updatedRegimen
            .valor(UPDATED_VALOR);
        RegimenDTO regimenDTO = regimenMapper.toDto(updatedRegimen);

        restRegimenMockMvc.perform(put("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimenDTO)))
            .andExpect(status().isOk());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeUpdate);
        Regimen testRegimen = regimenList.get(regimenList.size() - 1);
        assertThat(testRegimen.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingRegimen() throws Exception {
        int databaseSizeBeforeUpdate = regimenRepository.findAll().size();

        // Create the Regimen
        RegimenDTO regimenDTO = regimenMapper.toDto(regimen);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegimenMockMvc.perform(put("/api/regimen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regimen in the database
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegimen() throws Exception {
        // Initialize the database
        regimenRepository.saveAndFlush(regimen);

        int databaseSizeBeforeDelete = regimenRepository.findAll().size();

        // Get the regimen
        restRegimenMockMvc.perform(delete("/api/regimen/{id}", regimen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regimen> regimenList = regimenRepository.findAll();
        assertThat(regimenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regimen.class);
        Regimen regimen1 = new Regimen();
        regimen1.setId(1L);
        Regimen regimen2 = new Regimen();
        regimen2.setId(regimen1.getId());
        assertThat(regimen1).isEqualTo(regimen2);
        regimen2.setId(2L);
        assertThat(regimen1).isNotEqualTo(regimen2);
        regimen1.setId(null);
        assertThat(regimen1).isNotEqualTo(regimen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimenDTO.class);
        RegimenDTO regimenDTO1 = new RegimenDTO();
        regimenDTO1.setId(1L);
        RegimenDTO regimenDTO2 = new RegimenDTO();
        assertThat(regimenDTO1).isNotEqualTo(regimenDTO2);
        regimenDTO2.setId(regimenDTO1.getId());
        assertThat(regimenDTO1).isEqualTo(regimenDTO2);
        regimenDTO2.setId(2L);
        assertThat(regimenDTO1).isNotEqualTo(regimenDTO2);
        regimenDTO1.setId(null);
        assertThat(regimenDTO1).isNotEqualTo(regimenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regimenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regimenMapper.fromId(null)).isNull();
    }
}
