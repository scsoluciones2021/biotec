package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.Paciente;
import gestWeb.repository.PacienteRepository;
import gestWeb.service.PacienteService;
import gestWeb.service.dto.PacienteDTO;
import gestWeb.service.mapper.PacienteMapper;
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
 * Test class for the PacienteResource REST controller.
 *
 * @see PacienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class PacienteResourceIntTest {

    private static final String DEFAULT_NOMBRE_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PACIENTE = "BBBBBBBBBB";

    @Autowired
    private PacienteRepository pacienteRepository;


    @Autowired
    private PacienteMapper pacienteMapper;
    

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacienteResource pacienteResource = new PacienteResource(pacienteService);
        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
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
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombrePaciente(DEFAULT_NOMBRE_PACIENTE)
            .apellidoPaciente(DEFAULT_APELLIDO_PACIENTE)
            .documentoPaciente(DEFAULT_DOCUMENTO_PACIENTE)
            .direccionPaciente(DEFAULT_DIRECCION_PACIENTE)
            .telefonoPaciente(DEFAULT_TELEFONO_PACIENTE)
            .emailPaciente(DEFAULT_EMAIL_PACIENTE);
        return paciente;
    }

    @Before
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombrePaciente()).isEqualTo(DEFAULT_NOMBRE_PACIENTE);
        assertThat(testPaciente.getApellidoPaciente()).isEqualTo(DEFAULT_APELLIDO_PACIENTE);
        assertThat(testPaciente.getDocumentoPaciente()).isEqualTo(DEFAULT_DOCUMENTO_PACIENTE);
        assertThat(testPaciente.getDireccionPaciente()).isEqualTo(DEFAULT_DIRECCION_PACIENTE);
        assertThat(testPaciente.getTelefonoPaciente()).isEqualTo(DEFAULT_TELEFONO_PACIENTE);
        assertThat(testPaciente.getEmailPaciente()).isEqualTo(DEFAULT_EMAIL_PACIENTE);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombrePacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setNombrePaciente(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setApellidoPaciente(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentoPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setDocumentoPaciente(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setEmailPaciente(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePaciente").value(hasItem(DEFAULT_NOMBRE_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].apellidoPaciente").value(hasItem(DEFAULT_APELLIDO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].documentoPaciente").value(hasItem(DEFAULT_DOCUMENTO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].direccionPaciente").value(hasItem(DEFAULT_DIRECCION_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].telefonoPaciente").value(hasItem(DEFAULT_TELEFONO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].emailPaciente").value(hasItem(DEFAULT_EMAIL_PACIENTE.toString())));
    }
    

    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nombrePaciente").value(DEFAULT_NOMBRE_PACIENTE.toString()))
            .andExpect(jsonPath("$.apellidoPaciente").value(DEFAULT_APELLIDO_PACIENTE.toString()))
            .andExpect(jsonPath("$.documentoPaciente").value(DEFAULT_DOCUMENTO_PACIENTE.toString()))
            .andExpect(jsonPath("$.direccionPaciente").value(DEFAULT_DIRECCION_PACIENTE.toString()))
            .andExpect(jsonPath("$.telefonoPaciente").value(DEFAULT_TELEFONO_PACIENTE.toString()))
            .andExpect(jsonPath("$.emailPaciente").value(DEFAULT_EMAIL_PACIENTE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nombrePaciente(UPDATED_NOMBRE_PACIENTE)
            .apellidoPaciente(UPDATED_APELLIDO_PACIENTE)
            .documentoPaciente(UPDATED_DOCUMENTO_PACIENTE)
            .direccionPaciente(UPDATED_DIRECCION_PACIENTE)
            .telefonoPaciente(UPDATED_TELEFONO_PACIENTE)
            .emailPaciente(UPDATED_EMAIL_PACIENTE);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(updatedPaciente);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombrePaciente()).isEqualTo(UPDATED_NOMBRE_PACIENTE);
        assertThat(testPaciente.getApellidoPaciente()).isEqualTo(UPDATED_APELLIDO_PACIENTE);
        assertThat(testPaciente.getDocumentoPaciente()).isEqualTo(UPDATED_DOCUMENTO_PACIENTE);
        assertThat(testPaciente.getDireccionPaciente()).isEqualTo(UPDATED_DIRECCION_PACIENTE);
        assertThat(testPaciente.getTelefonoPaciente()).isEqualTo(UPDATED_TELEFONO_PACIENTE);
        assertThat(testPaciente.getEmailPaciente()).isEqualTo(UPDATED_EMAIL_PACIENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Get the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        Paciente paciente2 = new Paciente();
        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);
        paciente2.setId(2L);
        assertThat(paciente1).isNotEqualTo(paciente2);
        paciente1.setId(null);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacienteDTO.class);
        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        PacienteDTO pacienteDTO2 = new PacienteDTO();
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO2.setId(pacienteDTO1.getId());
        assertThat(pacienteDTO1).isEqualTo(pacienteDTO2);
        pacienteDTO2.setId(2L);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO1.setId(null);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pacienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pacienteMapper.fromId(null)).isNull();
    }
}
