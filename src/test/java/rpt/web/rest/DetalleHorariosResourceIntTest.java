package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.DetalleHorarios;
import rpt.repository.DetalleHorariosRepository;
import rpt.service.DetalleHorariosService;
import rpt.service.dto.DetalleHorariosDTO;
import rpt.service.mapper.DetalleHorariosMapper;
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
 * Test class for the DetalleHorariosResource REST controller.
 *
 * @see DetalleHorariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class DetalleHorariosResourceIntTest {

    private static final Long DEFAULT_ID_HORARIO = 1L;
    private static final Long UPDATED_ID_HORARIO = 2L;

    private static final String DEFAULT_HORA_DESDE = "AAAAAAAAAA";
    private static final String UPDATED_HORA_DESDE = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_HASTA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_HASTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_LUNES = 1;
    private static final Integer UPDATED_LUNES = 2;

    private static final Integer DEFAULT_MARTES = 1;
    private static final Integer UPDATED_MARTES = 2;

    private static final Integer DEFAULT_MIERCOLES = 1;
    private static final Integer UPDATED_MIERCOLES = 2;

    private static final Integer DEFAULT_JUEVES = 1;
    private static final Integer UPDATED_JUEVES = 2;

    private static final Integer DEFAULT_VIERNES = 1;
    private static final Integer UPDATED_VIERNES = 2;

    private static final Integer DEFAULT_SABADO = 1;
    private static final Integer UPDATED_SABADO = 2;

    private static final Integer DEFAULT_DOMINGO = 1;
    private static final Integer UPDATED_DOMINGO = 2;

    private static final Integer DEFAULT_INTERVALO = 1;
    private static final Integer UPDATED_INTERVALO = 2;

    private static final Integer DEFAULT_FRECUENCIA = 1;
    private static final Integer UPDATED_FRECUENCIA = 2;

    private static final Integer DEFAULT_CANTIDAD_PACIENTES = 1;
    private static final Integer UPDATED_CANTIDAD_PACIENTES = 2;

    @Autowired
    private DetalleHorariosRepository detalleHorariosRepository;


    @Autowired
    private DetalleHorariosMapper detalleHorariosMapper;
    

    @Autowired
    private DetalleHorariosService detalleHorariosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetalleHorariosMockMvc;

    private DetalleHorarios detalleHorarios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleHorariosResource detalleHorariosResource = new DetalleHorariosResource(detalleHorariosService);
        this.restDetalleHorariosMockMvc = MockMvcBuilders.standaloneSetup(detalleHorariosResource)
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
    public static DetalleHorarios createEntity(EntityManager em) {
        DetalleHorarios detalleHorarios = new DetalleHorarios()
            .idHorario(DEFAULT_ID_HORARIO)
            .horaDesde(DEFAULT_HORA_DESDE)
            .horaHasta(DEFAULT_HORA_HASTA)
            .lunes(DEFAULT_LUNES)
            .martes(DEFAULT_MARTES)
            .miercoles(DEFAULT_MIERCOLES)
            .jueves(DEFAULT_JUEVES)
            .viernes(DEFAULT_VIERNES)
            .sabado(DEFAULT_SABADO)
            .domingo(DEFAULT_DOMINGO)
            .intervalo(DEFAULT_INTERVALO)
            .frecuencia(DEFAULT_FRECUENCIA)
            .cantidadPacientes(DEFAULT_CANTIDAD_PACIENTES);
        return detalleHorarios;
    }

    @Before
    public void initTest() {
        detalleHorarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleHorarios() throws Exception {
        int databaseSizeBeforeCreate = detalleHorariosRepository.findAll().size();

        // Create the DetalleHorarios
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);
        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleHorarios in the database
        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleHorarios testDetalleHorarios = detalleHorariosList.get(detalleHorariosList.size() - 1);
        assertThat(testDetalleHorarios.getIdHorario()).isEqualTo(DEFAULT_ID_HORARIO);
        assertThat(testDetalleHorarios.getHoraDesde()).isEqualTo(DEFAULT_HORA_DESDE);
        assertThat(testDetalleHorarios.getHoraHasta()).isEqualTo(DEFAULT_HORA_HASTA);
        assertThat(testDetalleHorarios.getLunes()).isEqualTo(DEFAULT_LUNES);
        assertThat(testDetalleHorarios.getMartes()).isEqualTo(DEFAULT_MARTES);
        assertThat(testDetalleHorarios.getMiercoles()).isEqualTo(DEFAULT_MIERCOLES);
        assertThat(testDetalleHorarios.getJueves()).isEqualTo(DEFAULT_JUEVES);
        assertThat(testDetalleHorarios.getViernes()).isEqualTo(DEFAULT_VIERNES);
        assertThat(testDetalleHorarios.getSabado()).isEqualTo(DEFAULT_SABADO);
        assertThat(testDetalleHorarios.getDomingo()).isEqualTo(DEFAULT_DOMINGO);
        assertThat(testDetalleHorarios.getIntervalo()).isEqualTo(DEFAULT_INTERVALO);
        assertThat(testDetalleHorarios.getFrecuencia()).isEqualTo(DEFAULT_FRECUENCIA);
        assertThat(testDetalleHorarios.getCantidadPacientes()).isEqualTo(DEFAULT_CANTIDAD_PACIENTES);
    }

    @Test
    @Transactional
    public void createDetalleHorariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleHorariosRepository.findAll().size();

        // Create the DetalleHorarios with an existing ID
        detalleHorarios.setId(1L);
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleHorarios in the database
        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdHorarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setIdHorario(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraDesdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setHoraDesde(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraHastaIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setHoraHasta(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervaloIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setIntervalo(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrecuenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setFrecuencia(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadPacientesIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleHorariosRepository.findAll().size();
        // set the field null
        detalleHorarios.setCantidadPacientes(null);

        // Create the DetalleHorarios, which fails.
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        restDetalleHorariosMockMvc.perform(post("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalleHorarios() throws Exception {
        // Initialize the database
        detalleHorariosRepository.saveAndFlush(detalleHorarios);

        // Get all the detalleHorariosList
        restDetalleHorariosMockMvc.perform(get("/api/detalle-horarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleHorarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHorario").value(hasItem(DEFAULT_ID_HORARIO.intValue())))
            .andExpect(jsonPath("$.[*].horaDesde").value(hasItem(DEFAULT_HORA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].horaHasta").value(hasItem(DEFAULT_HORA_HASTA.toString())))
            .andExpect(jsonPath("$.[*].lunes").value(hasItem(DEFAULT_LUNES)))
            .andExpect(jsonPath("$.[*].martes").value(hasItem(DEFAULT_MARTES)))
            .andExpect(jsonPath("$.[*].miercoles").value(hasItem(DEFAULT_MIERCOLES)))
            .andExpect(jsonPath("$.[*].jueves").value(hasItem(DEFAULT_JUEVES)))
            .andExpect(jsonPath("$.[*].viernes").value(hasItem(DEFAULT_VIERNES)))
            .andExpect(jsonPath("$.[*].sabado").value(hasItem(DEFAULT_SABADO)))
            .andExpect(jsonPath("$.[*].domingo").value(hasItem(DEFAULT_DOMINGO)))
            .andExpect(jsonPath("$.[*].intervalo").value(hasItem(DEFAULT_INTERVALO)))
            .andExpect(jsonPath("$.[*].frecuencia").value(hasItem(DEFAULT_FRECUENCIA)))
            .andExpect(jsonPath("$.[*].cantidadPacientes").value(hasItem(DEFAULT_CANTIDAD_PACIENTES)));
    }
    

    @Test
    @Transactional
    public void getDetalleHorarios() throws Exception {
        // Initialize the database
        detalleHorariosRepository.saveAndFlush(detalleHorarios);

        // Get the detalleHorarios
        restDetalleHorariosMockMvc.perform(get("/api/detalle-horarios/{id}", detalleHorarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleHorarios.getId().intValue()))
            .andExpect(jsonPath("$.idHorario").value(DEFAULT_ID_HORARIO.intValue()))
            .andExpect(jsonPath("$.horaDesde").value(DEFAULT_HORA_DESDE.toString()))
            .andExpect(jsonPath("$.horaHasta").value(DEFAULT_HORA_HASTA.toString()))
            .andExpect(jsonPath("$.lunes").value(DEFAULT_LUNES))
            .andExpect(jsonPath("$.martes").value(DEFAULT_MARTES))
            .andExpect(jsonPath("$.miercoles").value(DEFAULT_MIERCOLES))
            .andExpect(jsonPath("$.jueves").value(DEFAULT_JUEVES))
            .andExpect(jsonPath("$.viernes").value(DEFAULT_VIERNES))
            .andExpect(jsonPath("$.sabado").value(DEFAULT_SABADO))
            .andExpect(jsonPath("$.domingo").value(DEFAULT_DOMINGO))
            .andExpect(jsonPath("$.intervalo").value(DEFAULT_INTERVALO))
            .andExpect(jsonPath("$.frecuencia").value(DEFAULT_FRECUENCIA))
            .andExpect(jsonPath("$.cantidadPacientes").value(DEFAULT_CANTIDAD_PACIENTES));
    }
    @Test
    @Transactional
    public void getNonExistingDetalleHorarios() throws Exception {
        // Get the detalleHorarios
        restDetalleHorariosMockMvc.perform(get("/api/detalle-horarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleHorarios() throws Exception {
        // Initialize the database
        detalleHorariosRepository.saveAndFlush(detalleHorarios);

        int databaseSizeBeforeUpdate = detalleHorariosRepository.findAll().size();

        // Update the detalleHorarios
        DetalleHorarios updatedDetalleHorarios = detalleHorariosRepository.findById(detalleHorarios.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleHorarios are not directly saved in db
        em.detach(updatedDetalleHorarios);
        updatedDetalleHorarios
            .idHorario(UPDATED_ID_HORARIO)
            .horaDesde(UPDATED_HORA_DESDE)
            .horaHasta(UPDATED_HORA_HASTA)
            .lunes(UPDATED_LUNES)
            .martes(UPDATED_MARTES)
            .miercoles(UPDATED_MIERCOLES)
            .jueves(UPDATED_JUEVES)
            .viernes(UPDATED_VIERNES)
            .sabado(UPDATED_SABADO)
            .domingo(UPDATED_DOMINGO)
            .intervalo(UPDATED_INTERVALO)
            .frecuencia(UPDATED_FRECUENCIA)
            .cantidadPacientes(UPDATED_CANTIDAD_PACIENTES);
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(updatedDetalleHorarios);

        restDetalleHorariosMockMvc.perform(put("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleHorarios in the database
        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeUpdate);
        DetalleHorarios testDetalleHorarios = detalleHorariosList.get(detalleHorariosList.size() - 1);
        assertThat(testDetalleHorarios.getIdHorario()).isEqualTo(UPDATED_ID_HORARIO);
        assertThat(testDetalleHorarios.getHoraDesde()).isEqualTo(UPDATED_HORA_DESDE);
        assertThat(testDetalleHorarios.getHoraHasta()).isEqualTo(UPDATED_HORA_HASTA);
        assertThat(testDetalleHorarios.getLunes()).isEqualTo(UPDATED_LUNES);
        assertThat(testDetalleHorarios.getMartes()).isEqualTo(UPDATED_MARTES);
        assertThat(testDetalleHorarios.getMiercoles()).isEqualTo(UPDATED_MIERCOLES);
        assertThat(testDetalleHorarios.getJueves()).isEqualTo(UPDATED_JUEVES);
        assertThat(testDetalleHorarios.getViernes()).isEqualTo(UPDATED_VIERNES);
        assertThat(testDetalleHorarios.getSabado()).isEqualTo(UPDATED_SABADO);
        assertThat(testDetalleHorarios.getDomingo()).isEqualTo(UPDATED_DOMINGO);
        assertThat(testDetalleHorarios.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
        assertThat(testDetalleHorarios.getFrecuencia()).isEqualTo(UPDATED_FRECUENCIA);
        assertThat(testDetalleHorarios.getCantidadPacientes()).isEqualTo(UPDATED_CANTIDAD_PACIENTES);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleHorarios() throws Exception {
        int databaseSizeBeforeUpdate = detalleHorariosRepository.findAll().size();

        // Create the DetalleHorarios
        DetalleHorariosDTO detalleHorariosDTO = detalleHorariosMapper.toDto(detalleHorarios);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetalleHorariosMockMvc.perform(put("/api/detalle-horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleHorariosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleHorarios in the database
        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleHorarios() throws Exception {
        // Initialize the database
        detalleHorariosRepository.saveAndFlush(detalleHorarios);

        int databaseSizeBeforeDelete = detalleHorariosRepository.findAll().size();

        // Get the detalleHorarios
        restDetalleHorariosMockMvc.perform(delete("/api/detalle-horarios/{id}", detalleHorarios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetalleHorarios> detalleHorariosList = detalleHorariosRepository.findAll();
        assertThat(detalleHorariosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleHorarios.class);
        DetalleHorarios detalleHorarios1 = new DetalleHorarios();
        detalleHorarios1.setId(1L);
        DetalleHorarios detalleHorarios2 = new DetalleHorarios();
        detalleHorarios2.setId(detalleHorarios1.getId());
        assertThat(detalleHorarios1).isEqualTo(detalleHorarios2);
        detalleHorarios2.setId(2L);
        assertThat(detalleHorarios1).isNotEqualTo(detalleHorarios2);
        detalleHorarios1.setId(null);
        assertThat(detalleHorarios1).isNotEqualTo(detalleHorarios2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleHorariosDTO.class);
        DetalleHorariosDTO detalleHorariosDTO1 = new DetalleHorariosDTO();
        detalleHorariosDTO1.setId(1L);
        DetalleHorariosDTO detalleHorariosDTO2 = new DetalleHorariosDTO();
        assertThat(detalleHorariosDTO1).isNotEqualTo(detalleHorariosDTO2);
        detalleHorariosDTO2.setId(detalleHorariosDTO1.getId());
        assertThat(detalleHorariosDTO1).isEqualTo(detalleHorariosDTO2);
        detalleHorariosDTO2.setId(2L);
        assertThat(detalleHorariosDTO1).isNotEqualTo(detalleHorariosDTO2);
        detalleHorariosDTO1.setId(null);
        assertThat(detalleHorariosDTO1).isNotEqualTo(detalleHorariosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detalleHorariosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detalleHorariosMapper.fromId(null)).isNull();
    }
}
