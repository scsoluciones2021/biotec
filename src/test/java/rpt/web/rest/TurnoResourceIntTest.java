package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.Turno;
import rpt.domain.Especialidad;
import rpt.domain.Profesional;
import rpt.repository.TurnoRepository;
import rpt.service.TurnoService;
import rpt.service.dto.TurnoDTO;
import rpt.service.mapper.TurnoMapper;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


import static rpt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TurnoResource REST controller.
 *
 * @see TurnoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class TurnoResourceIntTest {

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DIA = LocalDate.now(ZoneId.systemDefault());

   //  private static final LocalDate DEFAULT_HORA = LocalDateTime.(0L);
    private static final LocalDate UPDATED_HORA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_HORARIO = 1;
    private static final Integer UPDATED_ID_HORARIO = 2;

    @Autowired
    private TurnoRepository turnoRepository;


    @Autowired
    private TurnoMapper turnoMapper;
    

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTurnoMockMvc;

    private Turno turno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurnoResource turnoResource = new TurnoResource(turnoService);
        this.restTurnoMockMvc = MockMvcBuilders.standaloneSetup(turnoResource)
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
    public static Turno createEntity(EntityManager em) {
        Turno turno = new Turno()
            .dni(DEFAULT_DNI)
            .apellido(DEFAULT_APELLIDO)
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .dia(DEFAULT_DIA)
           // .hora(DEFAULT_HORA)
            .idHorario(DEFAULT_ID_HORARIO);
        // Add required entity
        Especialidad especialidad = EspecialidadResourceIntTest.createEntity(em);
        em.persist(especialidad);
        em.flush();
        turno.setTur_esp_rel(especialidad);
        // Add required entity
        Profesional profesional = ProfesionalResourceIntTest.createEntity(em);
        em.persist(profesional);
        em.flush();
        turno.setTur_prof_rel(profesional);
        return turno;
    }

    @Before
    public void initTest() {
        turno = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurno() throws Exception {
        int databaseSizeBeforeCreate = turnoRepository.findAll().size();

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);
        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isCreated());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate + 1);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testTurno.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testTurno.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTurno.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testTurno.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTurno.getDia()).isEqualTo(DEFAULT_DIA);
     //   assertThat(testTurno.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testTurno.getIdHorario()).isEqualTo(DEFAULT_ID_HORARIO);
    }

    @Test
    @Transactional
    public void createTurnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnoRepository.findAll().size();

        // Create the Turno with an existing ID
        turno.setId(1L);
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setDni(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setApellido(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setNombre(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setDia(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setHora(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdHorarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnoRepository.findAll().size();
        // set the field null
        turno.setIdHorario(null);

        // Create the Turno, which fails.
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTurnos() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get all the turnoList
        restTurnoMockMvc.perform(get("/api/turnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turno.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
           // .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA.toString())))
            .andExpect(jsonPath("$.[*].idHorario").value(hasItem(DEFAULT_ID_HORARIO)));
    }
    

    @Test
    @Transactional
    public void getTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get the turno
        restTurnoMockMvc.perform(get("/api/turnos/{id}", turno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turno.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
           // .andExpect(jsonPath("$.hora").value(DEFAULT_HORA.toString()))
            .andExpect(jsonPath("$.idHorario").value(DEFAULT_ID_HORARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTurno() throws Exception {
        // Get the turno
        restTurnoMockMvc.perform(get("/api/turnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Update the turno
        Turno updatedTurno = turnoRepository.findById(turno.getId()).get();
        // Disconnect from session so that the updates on updatedTurno are not directly saved in db
        em.detach(updatedTurno);
        updatedTurno
            .dni(UPDATED_DNI)
            .apellido(UPDATED_APELLIDO)
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .dia(UPDATED_DIA)
          //  .hora(UPDATED_HORA)
            .idHorario(UPDATED_ID_HORARIO);
        TurnoDTO turnoDTO = turnoMapper.toDto(updatedTurno);

        restTurnoMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isOk());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testTurno.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testTurno.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTurno.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testTurno.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTurno.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testTurno.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testTurno.getIdHorario()).isEqualTo(UPDATED_ID_HORARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Create the Turno
        TurnoDTO turnoDTO = turnoMapper.toDto(turno);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTurnoMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeDelete = turnoRepository.findAll().size();

        // Get the turno
        restTurnoMockMvc.perform(delete("/api/turnos/{id}", turno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turno.class);
        Turno turno1 = new Turno();
        turno1.setId(1L);
        Turno turno2 = new Turno();
        turno2.setId(turno1.getId());
        assertThat(turno1).isEqualTo(turno2);
        turno2.setId(2L);
        assertThat(turno1).isNotEqualTo(turno2);
        turno1.setId(null);
        assertThat(turno1).isNotEqualTo(turno2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TurnoDTO.class);
        TurnoDTO turnoDTO1 = new TurnoDTO();
        turnoDTO1.setId(1L);
        TurnoDTO turnoDTO2 = new TurnoDTO();
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
        turnoDTO2.setId(turnoDTO1.getId());
        assertThat(turnoDTO1).isEqualTo(turnoDTO2);
        turnoDTO2.setId(2L);
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
        turnoDTO1.setId(null);
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(turnoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(turnoMapper.fromId(null)).isNull();
    }
}
