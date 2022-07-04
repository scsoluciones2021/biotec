package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.ObsAntecPersonal;
import rpt.repository.ObsAntecPersonalRepository;
import rpt.service.ObsAntecPersonalService;
import rpt.service.dto.ObsAntecPersonalDTO;
import rpt.service.mapper.ObsAntecPersonalMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static rpt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObsAntecPersonalResource REST controller.
 *
 * @see ObsAntecPersonalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class ObsAntecPersonalResourceIntTest {

    private static final Integer DEFAULT_ID_ESPECIALIDAD = 1;
    private static final Integer UPDATED_ID_ESPECIALIDAD = 2;

    private static final Integer DEFAULT_ID_MEDICO = 1;
    private static final Integer UPDATED_ID_MEDICO = 2;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ObsAntecPersonalRepository obsAntecPersonalRepository;


    @Autowired
    private ObsAntecPersonalMapper obsAntecPersonalMapper;
    

    @Autowired
    private ObsAntecPersonalService obsAntecPersonalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restObsAntecPersonalMockMvc;

    private ObsAntecPersonal obsAntecPersonal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObsAntecPersonalResource obsAntecPersonalResource = new ObsAntecPersonalResource(obsAntecPersonalService);
        this.restObsAntecPersonalMockMvc = MockMvcBuilders.standaloneSetup(obsAntecPersonalResource)
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
    public static ObsAntecPersonal createEntity(EntityManager em) {
        ObsAntecPersonal obsAntecPersonal = new ObsAntecPersonal()
            .idEspecialidad(DEFAULT_ID_ESPECIALIDAD)
            .idMedico(DEFAULT_ID_MEDICO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .fecha(DEFAULT_FECHA);
        return obsAntecPersonal;
    }

    @Before
    public void initTest() {
        obsAntecPersonal = createEntity(em);
    }

    @Test
    @Transactional
    public void createObsAntecPersonal() throws Exception {
        int databaseSizeBeforeCreate = obsAntecPersonalRepository.findAll().size();

        // Create the ObsAntecPersonal
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);
        restObsAntecPersonalMockMvc.perform(post("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isCreated());

        // Validate the ObsAntecPersonal in the database
        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeCreate + 1);
        ObsAntecPersonal testObsAntecPersonal = obsAntecPersonalList.get(obsAntecPersonalList.size() - 1);
        assertThat(testObsAntecPersonal.getIdEspecialidad()).isEqualTo(DEFAULT_ID_ESPECIALIDAD);
        assertThat(testObsAntecPersonal.getIdMedico()).isEqualTo(DEFAULT_ID_MEDICO);
        assertThat(testObsAntecPersonal.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testObsAntecPersonal.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createObsAntecPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obsAntecPersonalRepository.findAll().size();

        // Create the ObsAntecPersonal with an existing ID
        obsAntecPersonal.setId(1L);
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObsAntecPersonalMockMvc.perform(post("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObsAntecPersonal in the database
        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdEspecialidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecPersonalRepository.findAll().size();
        // set the field null
        obsAntecPersonal.setIdEspecialidad(null);

        // Create the ObsAntecPersonal, which fails.
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);

        restObsAntecPersonalMockMvc.perform(post("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdMedicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecPersonalRepository.findAll().size();
        // set the field null
        obsAntecPersonal.setIdMedico(null);

        // Create the ObsAntecPersonal, which fails.
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);

        restObsAntecPersonalMockMvc.perform(post("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObservacionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecPersonalRepository.findAll().size();
        // set the field null
        obsAntecPersonal.setObservaciones(null);

        // Create the ObsAntecPersonal, which fails.
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);

        restObsAntecPersonalMockMvc.perform(post("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObsAntecPersonals() throws Exception {
        // Initialize the database
        obsAntecPersonalRepository.saveAndFlush(obsAntecPersonal);

        // Get all the obsAntecPersonalList
        restObsAntecPersonalMockMvc.perform(get("/api/obs-antec-personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obsAntecPersonal.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEspecialidad").value(hasItem(DEFAULT_ID_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].idMedico").value(hasItem(DEFAULT_ID_MEDICO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    

    @Test
    @Transactional
    public void getObsAntecPersonal() throws Exception {
        // Initialize the database
        obsAntecPersonalRepository.saveAndFlush(obsAntecPersonal);

        // Get the obsAntecPersonal
        restObsAntecPersonalMockMvc.perform(get("/api/obs-antec-personals/{id}", obsAntecPersonal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obsAntecPersonal.getId().intValue()))
            .andExpect(jsonPath("$.idEspecialidad").value(DEFAULT_ID_ESPECIALIDAD))
            .andExpect(jsonPath("$.idMedico").value(DEFAULT_ID_MEDICO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingObsAntecPersonal() throws Exception {
        // Get the obsAntecPersonal
        restObsAntecPersonalMockMvc.perform(get("/api/obs-antec-personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObsAntecPersonal() throws Exception {
        // Initialize the database
        obsAntecPersonalRepository.saveAndFlush(obsAntecPersonal);

        int databaseSizeBeforeUpdate = obsAntecPersonalRepository.findAll().size();

        // Update the obsAntecPersonal
        ObsAntecPersonal updatedObsAntecPersonal = obsAntecPersonalRepository.findById(obsAntecPersonal.getId()).get();
        // Disconnect from session so that the updates on updatedObsAntecPersonal are not directly saved in db
        em.detach(updatedObsAntecPersonal);
        updatedObsAntecPersonal
            .idEspecialidad(UPDATED_ID_ESPECIALIDAD)
            .idMedico(UPDATED_ID_MEDICO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fecha(UPDATED_FECHA);
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(updatedObsAntecPersonal);

        restObsAntecPersonalMockMvc.perform(put("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isOk());

        // Validate the ObsAntecPersonal in the database
        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeUpdate);
        ObsAntecPersonal testObsAntecPersonal = obsAntecPersonalList.get(obsAntecPersonalList.size() - 1);
        assertThat(testObsAntecPersonal.getIdEspecialidad()).isEqualTo(UPDATED_ID_ESPECIALIDAD);
        assertThat(testObsAntecPersonal.getIdMedico()).isEqualTo(UPDATED_ID_MEDICO);
        assertThat(testObsAntecPersonal.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testObsAntecPersonal.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingObsAntecPersonal() throws Exception {
        int databaseSizeBeforeUpdate = obsAntecPersonalRepository.findAll().size();

        // Create the ObsAntecPersonal
        ObsAntecPersonalDTO obsAntecPersonalDTO = obsAntecPersonalMapper.toDto(obsAntecPersonal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObsAntecPersonalMockMvc.perform(put("/api/obs-antec-personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecPersonalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObsAntecPersonal in the database
        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObsAntecPersonal() throws Exception {
        // Initialize the database
        obsAntecPersonalRepository.saveAndFlush(obsAntecPersonal);

        int databaseSizeBeforeDelete = obsAntecPersonalRepository.findAll().size();

        // Get the obsAntecPersonal
        restObsAntecPersonalMockMvc.perform(delete("/api/obs-antec-personals/{id}", obsAntecPersonal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ObsAntecPersonal> obsAntecPersonalList = obsAntecPersonalRepository.findAll();
        assertThat(obsAntecPersonalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObsAntecPersonal.class);
        ObsAntecPersonal obsAntecPersonal1 = new ObsAntecPersonal();
        obsAntecPersonal1.setId(1L);
        ObsAntecPersonal obsAntecPersonal2 = new ObsAntecPersonal();
        obsAntecPersonal2.setId(obsAntecPersonal1.getId());
        assertThat(obsAntecPersonal1).isEqualTo(obsAntecPersonal2);
        obsAntecPersonal2.setId(2L);
        assertThat(obsAntecPersonal1).isNotEqualTo(obsAntecPersonal2);
        obsAntecPersonal1.setId(null);
        assertThat(obsAntecPersonal1).isNotEqualTo(obsAntecPersonal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObsAntecPersonalDTO.class);
        ObsAntecPersonalDTO obsAntecPersonalDTO1 = new ObsAntecPersonalDTO();
        obsAntecPersonalDTO1.setId(1L);
        ObsAntecPersonalDTO obsAntecPersonalDTO2 = new ObsAntecPersonalDTO();
        assertThat(obsAntecPersonalDTO1).isNotEqualTo(obsAntecPersonalDTO2);
        obsAntecPersonalDTO2.setId(obsAntecPersonalDTO1.getId());
        assertThat(obsAntecPersonalDTO1).isEqualTo(obsAntecPersonalDTO2);
        obsAntecPersonalDTO2.setId(2L);
        assertThat(obsAntecPersonalDTO1).isNotEqualTo(obsAntecPersonalDTO2);
        obsAntecPersonalDTO1.setId(null);
        assertThat(obsAntecPersonalDTO1).isNotEqualTo(obsAntecPersonalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obsAntecPersonalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obsAntecPersonalMapper.fromId(null)).isNull();
    }
}
