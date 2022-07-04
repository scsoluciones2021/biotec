package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.ObsAntecFamiliar;
import gestWeb.repository.ObsAntecFamiliarRepository;
import gestWeb.service.ObsAntecFamiliarService;
import gestWeb.service.dto.ObsAntecFamiliarDTO;
import gestWeb.service.mapper.ObsAntecFamiliarMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static gestWeb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObsAntecFamiliarResource REST controller.
 *
 * @see ObsAntecFamiliarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class ObsAntecFamiliarResourceIntTest {

    private static final Integer DEFAULT_ID_ESPECIALIDAD = 1;
    private static final Integer UPDATED_ID_ESPECIALIDAD = 2;

    private static final Integer DEFAULT_ID_MEDICO = 1;
    private static final Integer UPDATED_ID_MEDICO = 2;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ObsAntecFamiliarRepository obsAntecFamiliarRepository;


    @Autowired
    private ObsAntecFamiliarMapper obsAntecFamiliarMapper;
    

    @Autowired
    private ObsAntecFamiliarService obsAntecFamiliarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restObsAntecFamiliarMockMvc;

    private ObsAntecFamiliar obsAntecFamiliar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObsAntecFamiliarResource obsAntecFamiliarResource = new ObsAntecFamiliarResource(obsAntecFamiliarService);
        this.restObsAntecFamiliarMockMvc = MockMvcBuilders.standaloneSetup(obsAntecFamiliarResource)
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
    public static ObsAntecFamiliar createEntity(EntityManager em) {
        ObsAntecFamiliar obsAntecFamiliar = new ObsAntecFamiliar()
            .idEspecialidad(DEFAULT_ID_ESPECIALIDAD)
            .idMedico(DEFAULT_ID_MEDICO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .fecha(DEFAULT_FECHA);
        return obsAntecFamiliar;
    }

    @Before
    public void initTest() {
        obsAntecFamiliar = createEntity(em);
    }

    @Test
    @Transactional
    public void createObsAntecFamiliar() throws Exception {
        int databaseSizeBeforeCreate = obsAntecFamiliarRepository.findAll().size();

        // Create the ObsAntecFamiliar
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);
        restObsAntecFamiliarMockMvc.perform(post("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isCreated());

        // Validate the ObsAntecFamiliar in the database
        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeCreate + 1);
        ObsAntecFamiliar testObsAntecFamiliar = obsAntecFamiliarList.get(obsAntecFamiliarList.size() - 1);
        assertThat(testObsAntecFamiliar.getIdEspecialidad()).isEqualTo(DEFAULT_ID_ESPECIALIDAD);
        assertThat(testObsAntecFamiliar.getIdMedico()).isEqualTo(DEFAULT_ID_MEDICO);
        assertThat(testObsAntecFamiliar.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testObsAntecFamiliar.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createObsAntecFamiliarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obsAntecFamiliarRepository.findAll().size();

        // Create the ObsAntecFamiliar with an existing ID
        obsAntecFamiliar.setId(1L);
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObsAntecFamiliarMockMvc.perform(post("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObsAntecFamiliar in the database
        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdEspecialidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecFamiliarRepository.findAll().size();
        // set the field null
        obsAntecFamiliar.setIdEspecialidad(null);

        // Create the ObsAntecFamiliar, which fails.
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);

        restObsAntecFamiliarMockMvc.perform(post("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdMedicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecFamiliarRepository.findAll().size();
        // set the field null
        obsAntecFamiliar.setIdMedico(null);

        // Create the ObsAntecFamiliar, which fails.
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);

        restObsAntecFamiliarMockMvc.perform(post("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObservacionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = obsAntecFamiliarRepository.findAll().size();
        // set the field null
        obsAntecFamiliar.setObservaciones(null);

        // Create the ObsAntecFamiliar, which fails.
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);

        restObsAntecFamiliarMockMvc.perform(post("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isBadRequest());

        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObsAntecFamiliars() throws Exception {
        // Initialize the database
        obsAntecFamiliarRepository.saveAndFlush(obsAntecFamiliar);

        // Get all the obsAntecFamiliarList
        restObsAntecFamiliarMockMvc.perform(get("/api/obs-antec-familiars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obsAntecFamiliar.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEspecialidad").value(hasItem(DEFAULT_ID_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].idMedico").value(hasItem(DEFAULT_ID_MEDICO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    

    @Test
    @Transactional
    public void getObsAntecFamiliar() throws Exception {
        // Initialize the database
        obsAntecFamiliarRepository.saveAndFlush(obsAntecFamiliar);

        // Get the obsAntecFamiliar
        restObsAntecFamiliarMockMvc.perform(get("/api/obs-antec-familiars/{id}", obsAntecFamiliar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obsAntecFamiliar.getId().intValue()))
            .andExpect(jsonPath("$.idEspecialidad").value(DEFAULT_ID_ESPECIALIDAD))
            .andExpect(jsonPath("$.idMedico").value(DEFAULT_ID_MEDICO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingObsAntecFamiliar() throws Exception {
        // Get the obsAntecFamiliar
        restObsAntecFamiliarMockMvc.perform(get("/api/obs-antec-familiars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObsAntecFamiliar() throws Exception {
        // Initialize the database
        obsAntecFamiliarRepository.saveAndFlush(obsAntecFamiliar);

        int databaseSizeBeforeUpdate = obsAntecFamiliarRepository.findAll().size();

        // Update the obsAntecFamiliar
        ObsAntecFamiliar updatedObsAntecFamiliar = obsAntecFamiliarRepository.findById(obsAntecFamiliar.getId()).get();
        // Disconnect from session so that the updates on updatedObsAntecFamiliar are not directly saved in db
        em.detach(updatedObsAntecFamiliar);
        updatedObsAntecFamiliar
            .idEspecialidad(UPDATED_ID_ESPECIALIDAD)
            .idMedico(UPDATED_ID_MEDICO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fecha(UPDATED_FECHA);
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(updatedObsAntecFamiliar);

        restObsAntecFamiliarMockMvc.perform(put("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isOk());

        // Validate the ObsAntecFamiliar in the database
        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeUpdate);
        ObsAntecFamiliar testObsAntecFamiliar = obsAntecFamiliarList.get(obsAntecFamiliarList.size() - 1);
        assertThat(testObsAntecFamiliar.getIdEspecialidad()).isEqualTo(UPDATED_ID_ESPECIALIDAD);
        assertThat(testObsAntecFamiliar.getIdMedico()).isEqualTo(UPDATED_ID_MEDICO);
        assertThat(testObsAntecFamiliar.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testObsAntecFamiliar.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingObsAntecFamiliar() throws Exception {
        int databaseSizeBeforeUpdate = obsAntecFamiliarRepository.findAll().size();

        // Create the ObsAntecFamiliar
        ObsAntecFamiliarDTO obsAntecFamiliarDTO = obsAntecFamiliarMapper.toDto(obsAntecFamiliar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObsAntecFamiliarMockMvc.perform(put("/api/obs-antec-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obsAntecFamiliarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObsAntecFamiliar in the database
        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObsAntecFamiliar() throws Exception {
        // Initialize the database
        obsAntecFamiliarRepository.saveAndFlush(obsAntecFamiliar);

        int databaseSizeBeforeDelete = obsAntecFamiliarRepository.findAll().size();

        // Get the obsAntecFamiliar
        restObsAntecFamiliarMockMvc.perform(delete("/api/obs-antec-familiars/{id}", obsAntecFamiliar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ObsAntecFamiliar> obsAntecFamiliarList = obsAntecFamiliarRepository.findAll();
        assertThat(obsAntecFamiliarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObsAntecFamiliar.class);
        ObsAntecFamiliar obsAntecFamiliar1 = new ObsAntecFamiliar();
        obsAntecFamiliar1.setId(1L);
        ObsAntecFamiliar obsAntecFamiliar2 = new ObsAntecFamiliar();
        obsAntecFamiliar2.setId(obsAntecFamiliar1.getId());
        assertThat(obsAntecFamiliar1).isEqualTo(obsAntecFamiliar2);
        obsAntecFamiliar2.setId(2L);
        assertThat(obsAntecFamiliar1).isNotEqualTo(obsAntecFamiliar2);
        obsAntecFamiliar1.setId(null);
        assertThat(obsAntecFamiliar1).isNotEqualTo(obsAntecFamiliar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObsAntecFamiliarDTO.class);
        ObsAntecFamiliarDTO obsAntecFamiliarDTO1 = new ObsAntecFamiliarDTO();
        obsAntecFamiliarDTO1.setId(1L);
        ObsAntecFamiliarDTO obsAntecFamiliarDTO2 = new ObsAntecFamiliarDTO();
        assertThat(obsAntecFamiliarDTO1).isNotEqualTo(obsAntecFamiliarDTO2);
        obsAntecFamiliarDTO2.setId(obsAntecFamiliarDTO1.getId());
        assertThat(obsAntecFamiliarDTO1).isEqualTo(obsAntecFamiliarDTO2);
        obsAntecFamiliarDTO2.setId(2L);
        assertThat(obsAntecFamiliarDTO1).isNotEqualTo(obsAntecFamiliarDTO2);
        obsAntecFamiliarDTO1.setId(null);
        assertThat(obsAntecFamiliarDTO1).isNotEqualTo(obsAntecFamiliarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obsAntecFamiliarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obsAntecFamiliarMapper.fromId(null)).isNull();
    }
}
