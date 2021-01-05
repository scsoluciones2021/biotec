package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Constantes;
import rpt.repository.ConstantesRepository;
import rpt.service.ConstantesService;
import rpt.service.dto.ConstantesDTO;
import rpt.service.mapper.ConstantesMapper;
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
 * Test class for the ConstantesResource REST controller.
 *
 * @see ConstantesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class ConstantesResourceIntTest {

    private static final Long DEFAULT_PESO = 1L;
    private static final Long UPDATED_PESO = 2L;

    private static final Long DEFAULT_ALTURA = 1L;
    private static final Long UPDATED_ALTURA = 2L;

    private static final Long DEFAULT_PRESION_ARTERIAL = 1L;
    private static final Long UPDATED_PRESION_ARTERIAL = 2L;

    @Autowired
    private ConstantesRepository constantesRepository;


    @Autowired
    private ConstantesMapper constantesMapper;
    

    @Autowired
    private ConstantesService constantesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConstantesMockMvc;

    private Constantes constantes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConstantesResource constantesResource = new ConstantesResource(constantesService);
        this.restConstantesMockMvc = MockMvcBuilders.standaloneSetup(constantesResource)
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
    public static Constantes createEntity(EntityManager em) {
        Constantes constantes = new Constantes()
            .peso(DEFAULT_PESO)
            .altura(DEFAULT_ALTURA)
            .presionArterial(DEFAULT_PRESION_ARTERIAL);
        return constantes;
    }

    @Before
    public void initTest() {
        constantes = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstantes() throws Exception {
        int databaseSizeBeforeCreate = constantesRepository.findAll().size();

        // Create the Constantes
        ConstantesDTO constantesDTO = constantesMapper.toDto(constantes);
        restConstantesMockMvc.perform(post("/api/constantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constantesDTO)))
            .andExpect(status().isCreated());

        // Validate the Constantes in the database
        List<Constantes> constantesList = constantesRepository.findAll();
        assertThat(constantesList).hasSize(databaseSizeBeforeCreate + 1);
        Constantes testConstantes = constantesList.get(constantesList.size() - 1);
        assertThat(testConstantes.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testConstantes.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testConstantes.getPresionArterial()).isEqualTo(DEFAULT_PRESION_ARTERIAL);
    }

    @Test
    @Transactional
    public void createConstantesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constantesRepository.findAll().size();

        // Create the Constantes with an existing ID
        constantes.setId(1L);
        ConstantesDTO constantesDTO = constantesMapper.toDto(constantes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstantesMockMvc.perform(post("/api/constantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constantesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Constantes in the database
        List<Constantes> constantesList = constantesRepository.findAll();
        assertThat(constantesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConstantes() throws Exception {
        // Initialize the database
        constantesRepository.saveAndFlush(constantes);

        // Get all the constantesList
        restConstantesMockMvc.perform(get("/api/constantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constantes.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.intValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.intValue())))
            .andExpect(jsonPath("$.[*].presionArterial").value(hasItem(DEFAULT_PRESION_ARTERIAL.intValue())));
    }
    

    @Test
    @Transactional
    public void getConstantes() throws Exception {
        // Initialize the database
        constantesRepository.saveAndFlush(constantes);

        // Get the constantes
        restConstantesMockMvc.perform(get("/api/constantes/{id}", constantes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(constantes.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.intValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.intValue()))
            .andExpect(jsonPath("$.presionArterial").value(DEFAULT_PRESION_ARTERIAL.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConstantes() throws Exception {
        // Get the constantes
        restConstantesMockMvc.perform(get("/api/constantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstantes() throws Exception {
        // Initialize the database
        constantesRepository.saveAndFlush(constantes);

        int databaseSizeBeforeUpdate = constantesRepository.findAll().size();

        // Update the constantes
        Constantes updatedConstantes = constantesRepository.findById(constantes.getId()).get();
        // Disconnect from session so that the updates on updatedConstantes are not directly saved in db
        em.detach(updatedConstantes);
        updatedConstantes
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA)
            .presionArterial(UPDATED_PRESION_ARTERIAL);
        ConstantesDTO constantesDTO = constantesMapper.toDto(updatedConstantes);

        restConstantesMockMvc.perform(put("/api/constantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constantesDTO)))
            .andExpect(status().isOk());

        // Validate the Constantes in the database
        List<Constantes> constantesList = constantesRepository.findAll();
        assertThat(constantesList).hasSize(databaseSizeBeforeUpdate);
        Constantes testConstantes = constantesList.get(constantesList.size() - 1);
        assertThat(testConstantes.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testConstantes.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testConstantes.getPresionArterial()).isEqualTo(UPDATED_PRESION_ARTERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingConstantes() throws Exception {
        int databaseSizeBeforeUpdate = constantesRepository.findAll().size();

        // Create the Constantes
        ConstantesDTO constantesDTO = constantesMapper.toDto(constantes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConstantesMockMvc.perform(put("/api/constantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constantesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Constantes in the database
        List<Constantes> constantesList = constantesRepository.findAll();
        assertThat(constantesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConstantes() throws Exception {
        // Initialize the database
        constantesRepository.saveAndFlush(constantes);

        int databaseSizeBeforeDelete = constantesRepository.findAll().size();

        // Get the constantes
        restConstantesMockMvc.perform(delete("/api/constantes/{id}", constantes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Constantes> constantesList = constantesRepository.findAll();
        assertThat(constantesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Constantes.class);
        Constantes constantes1 = new Constantes();
        constantes1.setId(1L);
        Constantes constantes2 = new Constantes();
        constantes2.setId(constantes1.getId());
        assertThat(constantes1).isEqualTo(constantes2);
        constantes2.setId(2L);
        assertThat(constantes1).isNotEqualTo(constantes2);
        constantes1.setId(null);
        assertThat(constantes1).isNotEqualTo(constantes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstantesDTO.class);
        ConstantesDTO constantesDTO1 = new ConstantesDTO();
        constantesDTO1.setId(1L);
        ConstantesDTO constantesDTO2 = new ConstantesDTO();
        assertThat(constantesDTO1).isNotEqualTo(constantesDTO2);
        constantesDTO2.setId(constantesDTO1.getId());
        assertThat(constantesDTO1).isEqualTo(constantesDTO2);
        constantesDTO2.setId(2L);
        assertThat(constantesDTO1).isNotEqualTo(constantesDTO2);
        constantesDTO1.setId(null);
        assertThat(constantesDTO1).isNotEqualTo(constantesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(constantesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(constantesMapper.fromId(null)).isNull();
    }
}
