package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.CodigoPostal;
import gestWeb.repository.CodigoPostalRepository;
import gestWeb.service.CodigoPostalService;
import gestWeb.service.dto.CodigoPostalDTO;
import gestWeb.service.mapper.CodigoPostalMapper;
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
 * Test class for the CodigoPostalResource REST controller.
 *
 * @see CodigoPostalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class CodigoPostalResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CIUDAD = "BBBBBBBBBB";

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;


    @Autowired
    private CodigoPostalMapper codigoPostalMapper;
    

    @Autowired
    private CodigoPostalService codigoPostalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodigoPostalMockMvc;

    private CodigoPostal codigoPostal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodigoPostalResource codigoPostalResource = new CodigoPostalResource(codigoPostalService);
        this.restCodigoPostalMockMvc = MockMvcBuilders.standaloneSetup(codigoPostalResource)
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
    public static CodigoPostal createEntity(EntityManager em) {
        CodigoPostal codigoPostal = new CodigoPostal()
            .codigo(DEFAULT_CODIGO)
            .nombreCiudad(DEFAULT_NOMBRE_CIUDAD);
        return codigoPostal;
    }

    @Before
    public void initTest() {
        codigoPostal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodigoPostal() throws Exception {
        int databaseSizeBeforeCreate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);
        restCodigoPostalMockMvc.perform(post("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isCreated());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeCreate + 1);
        CodigoPostal testCodigoPostal = codigoPostalList.get(codigoPostalList.size() - 1);
        assertThat(testCodigoPostal.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCodigoPostal.getNombreCiudad()).isEqualTo(DEFAULT_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void createCodigoPostalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal with an existing ID
        codigoPostal.setId(1L);
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodigoPostalMockMvc.perform(post("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCodigoPostals() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get all the codigoPostalList
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigoPostal.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD.toString())));
    }
    

    @Test
    @Transactional
    public void getCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        // Get the codigoPostal
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/{id}", codigoPostal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codigoPostal.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nombreCiudad").value(DEFAULT_NOMBRE_CIUDAD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCodigoPostal() throws Exception {
        // Get the codigoPostal
        restCodigoPostalMockMvc.perform(get("/api/codigo-postals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        int databaseSizeBeforeUpdate = codigoPostalRepository.findAll().size();

        // Update the codigoPostal
        CodigoPostal updatedCodigoPostal = codigoPostalRepository.findById(codigoPostal.getId()).get();
        // Disconnect from session so that the updates on updatedCodigoPostal are not directly saved in db
        em.detach(updatedCodigoPostal);
        updatedCodigoPostal
            .codigo(UPDATED_CODIGO)
            .nombreCiudad(UPDATED_NOMBRE_CIUDAD);
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(updatedCodigoPostal);

        restCodigoPostalMockMvc.perform(put("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isOk());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeUpdate);
        CodigoPostal testCodigoPostal = codigoPostalList.get(codigoPostalList.size() - 1);
        assertThat(testCodigoPostal.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCodigoPostal.getNombreCiudad()).isEqualTo(UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingCodigoPostal() throws Exception {
        int databaseSizeBeforeUpdate = codigoPostalRepository.findAll().size();

        // Create the CodigoPostal
        CodigoPostalDTO codigoPostalDTO = codigoPostalMapper.toDto(codigoPostal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCodigoPostalMockMvc.perform(put("/api/codigo-postals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codigoPostalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoPostal in the database
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCodigoPostal() throws Exception {
        // Initialize the database
        codigoPostalRepository.saveAndFlush(codigoPostal);

        int databaseSizeBeforeDelete = codigoPostalRepository.findAll().size();

        // Get the codigoPostal
        restCodigoPostalMockMvc.perform(delete("/api/codigo-postals/{id}", codigoPostal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CodigoPostal> codigoPostalList = codigoPostalRepository.findAll();
        assertThat(codigoPostalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoPostal.class);
        CodigoPostal codigoPostal1 = new CodigoPostal();
        codigoPostal1.setId(1L);
        CodigoPostal codigoPostal2 = new CodigoPostal();
        codigoPostal2.setId(codigoPostal1.getId());
        assertThat(codigoPostal1).isEqualTo(codigoPostal2);
        codigoPostal2.setId(2L);
        assertThat(codigoPostal1).isNotEqualTo(codigoPostal2);
        codigoPostal1.setId(null);
        assertThat(codigoPostal1).isNotEqualTo(codigoPostal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoPostalDTO.class);
        CodigoPostalDTO codigoPostalDTO1 = new CodigoPostalDTO();
        codigoPostalDTO1.setId(1L);
        CodigoPostalDTO codigoPostalDTO2 = new CodigoPostalDTO();
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
        codigoPostalDTO2.setId(codigoPostalDTO1.getId());
        assertThat(codigoPostalDTO1).isEqualTo(codigoPostalDTO2);
        codigoPostalDTO2.setId(2L);
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
        codigoPostalDTO1.setId(null);
        assertThat(codigoPostalDTO1).isNotEqualTo(codigoPostalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(codigoPostalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(codigoPostalMapper.fromId(null)).isNull();
    }
}
