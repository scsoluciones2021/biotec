package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Especialidad;
import rpt.repository.EspecialidadRepository;
import rpt.service.EspecialidadService;
import rpt.service.dto.EspecialidadDTO;
import rpt.service.mapper.EspecialidadMapper;
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
 * Test class for the EspecialidadResource REST controller.
 *
 * @see EspecialidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class EspecialidadResourceIntTest {

    private static final String DEFAULT_CODIGO_ESPECILIDAD = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ESPECILIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ESPECIALIDAD = "BBBBBBBBBB";

    @Autowired
    private EspecialidadRepository especialidadRepository;


    @Autowired
    private EspecialidadMapper especialidadMapper;
    

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEspecialidadMockMvc;

    private Especialidad especialidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspecialidadResource especialidadResource = new EspecialidadResource(especialidadService);
        this.restEspecialidadMockMvc = MockMvcBuilders.standaloneSetup(especialidadResource)
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
    public static Especialidad createEntity(EntityManager em) {
        Especialidad especialidad = new Especialidad()
            .codigoEspecilidad(DEFAULT_CODIGO_ESPECILIDAD)
            .nombreEspecialidad(DEFAULT_NOMBRE_ESPECIALIDAD);
        return especialidad;
    }

    @Before
    public void initTest() {
        especialidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecialidad() throws Exception {
        int databaseSizeBeforeCreate = especialidadRepository.findAll().size();

        // Create the Especialidad
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(especialidad);
        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeCreate + 1);
        Especialidad testEspecialidad = especialidadList.get(especialidadList.size() - 1);
        assertThat(testEspecialidad.getCodigoEspecilidad()).isEqualTo(DEFAULT_CODIGO_ESPECILIDAD);
        assertThat(testEspecialidad.getNombreEspecialidad()).isEqualTo(DEFAULT_NOMBRE_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void createEspecialidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especialidadRepository.findAll().size();

        // Create the Especialidad with an existing ID
        especialidad.setId(1L);
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(especialidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoEspecilidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = especialidadRepository.findAll().size();
        // set the field null
        especialidad.setCodigoEspecilidad(null);

        // Create the Especialidad, which fails.
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(especialidad);

        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isBadRequest());

        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreEspecialidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = especialidadRepository.findAll().size();
        // set the field null
        especialidad.setNombreEspecialidad(null);

        // Create the Especialidad, which fails.
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(especialidad);

        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isBadRequest());

        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspecialidads() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        // Get all the especialidadList
        restEspecialidadMockMvc.perform(get("/api/especialidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoEspecilidad").value(hasItem(DEFAULT_CODIGO_ESPECILIDAD.toString())))
            .andExpect(jsonPath("$.[*].nombreEspecialidad").value(hasItem(DEFAULT_NOMBRE_ESPECIALIDAD.toString())));
    }
    

    @Test
    @Transactional
    public void getEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        // Get the especialidad
        restEspecialidadMockMvc.perform(get("/api/especialidads/{id}", especialidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especialidad.getId().intValue()))
            .andExpect(jsonPath("$.codigoEspecilidad").value(DEFAULT_CODIGO_ESPECILIDAD.toString()))
            .andExpect(jsonPath("$.nombreEspecialidad").value(DEFAULT_NOMBRE_ESPECIALIDAD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEspecialidad() throws Exception {
        // Get the especialidad
        restEspecialidadMockMvc.perform(get("/api/especialidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        int databaseSizeBeforeUpdate = especialidadRepository.findAll().size();

        // Update the especialidad
        Especialidad updatedEspecialidad = especialidadRepository.findById(especialidad.getId()).get();
        // Disconnect from session so that the updates on updatedEspecialidad are not directly saved in db
        em.detach(updatedEspecialidad);
        updatedEspecialidad
            .codigoEspecilidad(UPDATED_CODIGO_ESPECILIDAD)
            .nombreEspecialidad(UPDATED_NOMBRE_ESPECIALIDAD);
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(updatedEspecialidad);

        restEspecialidadMockMvc.perform(put("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isOk());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeUpdate);
        Especialidad testEspecialidad = especialidadList.get(especialidadList.size() - 1);
        assertThat(testEspecialidad.getCodigoEspecilidad()).isEqualTo(UPDATED_CODIGO_ESPECILIDAD);
        assertThat(testEspecialidad.getNombreEspecialidad()).isEqualTo(UPDATED_NOMBRE_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingEspecialidad() throws Exception {
        int databaseSizeBeforeUpdate = especialidadRepository.findAll().size();

        // Create the Especialidad
        EspecialidadDTO especialidadDTO = especialidadMapper.toDto(especialidad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEspecialidadMockMvc.perform(put("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        int databaseSizeBeforeDelete = especialidadRepository.findAll().size();

        // Get the especialidad
        restEspecialidadMockMvc.perform(delete("/api/especialidads/{id}", especialidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidad.class);
        Especialidad especialidad1 = new Especialidad();
        especialidad1.setId(1L);
        Especialidad especialidad2 = new Especialidad();
        especialidad2.setId(especialidad1.getId());
        assertThat(especialidad1).isEqualTo(especialidad2);
        especialidad2.setId(2L);
        assertThat(especialidad1).isNotEqualTo(especialidad2);
        especialidad1.setId(null);
        assertThat(especialidad1).isNotEqualTo(especialidad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialidadDTO.class);
        EspecialidadDTO especialidadDTO1 = new EspecialidadDTO();
        especialidadDTO1.setId(1L);
        EspecialidadDTO especialidadDTO2 = new EspecialidadDTO();
        assertThat(especialidadDTO1).isNotEqualTo(especialidadDTO2);
        especialidadDTO2.setId(especialidadDTO1.getId());
        assertThat(especialidadDTO1).isEqualTo(especialidadDTO2);
        especialidadDTO2.setId(2L);
        assertThat(especialidadDTO1).isNotEqualTo(especialidadDTO2);
        especialidadDTO1.setId(null);
        assertThat(especialidadDTO1).isNotEqualTo(especialidadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(especialidadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(especialidadMapper.fromId(null)).isNull();
    }
}
