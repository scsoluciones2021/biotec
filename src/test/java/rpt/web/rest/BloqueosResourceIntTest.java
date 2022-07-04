package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.Bloqueos;
import rpt.repository.BloqueosRepository;
import rpt.service.BloqueosService;
import rpt.service.dto.BloqueosDTO;
import rpt.service.mapper.BloqueosMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static rpt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BloqueosResource REST controller.
 *
 * @see BloqueosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class BloqueosResourceIntTest {

    private static final String DEFAULT_NOMBRE_BLOQUEO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_BLOQUEO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_HASTA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_DESDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_DESDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_HASTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_HASTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BloqueosRepository bloqueosRepository;


    @Autowired
    private BloqueosMapper bloqueosMapper;
    

    @Autowired
    private BloqueosService bloqueosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBloqueosMockMvc;

    private Bloqueos bloqueos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BloqueosResource bloqueosResource = new BloqueosResource(bloqueosService);
        this.restBloqueosMockMvc = MockMvcBuilders.standaloneSetup(bloqueosResource)
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
    public static Bloqueos createEntity(EntityManager em) {
        Bloqueos bloqueos = new Bloqueos()
            .nombreBloqueo(DEFAULT_NOMBRE_BLOQUEO)
            .fechaDesde(DEFAULT_FECHA_DESDE)
            .fechaHasta(DEFAULT_FECHA_HASTA)
            .horaDesde(DEFAULT_HORA_DESDE)
            .horaHasta(DEFAULT_HORA_HASTA);
        return bloqueos;
    }

    @Before
    public void initTest() {
        bloqueos = createEntity(em);
    }

    @Test
    @Transactional
    public void createBloqueos() throws Exception {
        int databaseSizeBeforeCreate = bloqueosRepository.findAll().size();

        // Create the Bloqueos
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);
        restBloqueosMockMvc.perform(post("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isCreated());

        // Validate the Bloqueos in the database
        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeCreate + 1);
        Bloqueos testBloqueos = bloqueosList.get(bloqueosList.size() - 1);
        assertThat(testBloqueos.getNombreBloqueo()).isEqualTo(DEFAULT_NOMBRE_BLOQUEO);
        assertThat(testBloqueos.getFechaDesde()).isEqualTo(DEFAULT_FECHA_DESDE);
        assertThat(testBloqueos.getFechaHasta()).isEqualTo(DEFAULT_FECHA_HASTA);
        assertThat(testBloqueos.getHoraDesde()).isEqualTo(DEFAULT_HORA_DESDE);
        assertThat(testBloqueos.getHoraHasta()).isEqualTo(DEFAULT_HORA_HASTA);
    }

    @Test
    @Transactional
    public void createBloqueosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bloqueosRepository.findAll().size();

        // Create the Bloqueos with an existing ID
        bloqueos.setId(1L);
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloqueosMockMvc.perform(post("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bloqueos in the database
        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreBloqueoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloqueosRepository.findAll().size();
        // set the field null
        bloqueos.setNombreBloqueo(null);

        // Create the Bloqueos, which fails.
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);

        restBloqueosMockMvc.perform(post("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isBadRequest());

        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaDesdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloqueosRepository.findAll().size();
        // set the field null
        bloqueos.setFechaDesde(null);

        // Create the Bloqueos, which fails.
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);

        restBloqueosMockMvc.perform(post("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isBadRequest());

        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaHastaIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloqueosRepository.findAll().size();
        // set the field null
        bloqueos.setFechaHasta(null);

        // Create the Bloqueos, which fails.
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);

        restBloqueosMockMvc.perform(post("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isBadRequest());

        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBloqueos() throws Exception {
        // Initialize the database
        bloqueosRepository.saveAndFlush(bloqueos);

        // Get all the bloqueosList
        restBloqueosMockMvc.perform(get("/api/bloqueos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloqueos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreBloqueo").value(hasItem(DEFAULT_NOMBRE_BLOQUEO.toString())))
            .andExpect(jsonPath("$.[*].fechaDesde").value(hasItem(DEFAULT_FECHA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].fechaHasta").value(hasItem(DEFAULT_FECHA_HASTA.toString())))
            .andExpect(jsonPath("$.[*].horaDesde").value(hasItem(DEFAULT_HORA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].horaHasta").value(hasItem(DEFAULT_HORA_HASTA.toString())));
    }
    

    @Test
    @Transactional
    public void getBloqueos() throws Exception {
        // Initialize the database
        bloqueosRepository.saveAndFlush(bloqueos);

        // Get the bloqueos
        restBloqueosMockMvc.perform(get("/api/bloqueos/{id}", bloqueos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bloqueos.getId().intValue()))
            .andExpect(jsonPath("$.nombreBloqueo").value(DEFAULT_NOMBRE_BLOQUEO.toString()))
            .andExpect(jsonPath("$.fechaDesde").value(DEFAULT_FECHA_DESDE.toString()))
            .andExpect(jsonPath("$.fechaHasta").value(DEFAULT_FECHA_HASTA.toString()))
            .andExpect(jsonPath("$.horaDesde").value(DEFAULT_HORA_DESDE.toString()))
            .andExpect(jsonPath("$.horaHasta").value(DEFAULT_HORA_HASTA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBloqueos() throws Exception {
        // Get the bloqueos
        restBloqueosMockMvc.perform(get("/api/bloqueos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBloqueos() throws Exception {
        // Initialize the database
        bloqueosRepository.saveAndFlush(bloqueos);

        int databaseSizeBeforeUpdate = bloqueosRepository.findAll().size();

        // Update the bloqueos
        Bloqueos updatedBloqueos = bloqueosRepository.findById(bloqueos.getId()).get();
        // Disconnect from session so that the updates on updatedBloqueos are not directly saved in db
        em.detach(updatedBloqueos);
        updatedBloqueos
            .nombreBloqueo(UPDATED_NOMBRE_BLOQUEO)
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA)
            .horaDesde(UPDATED_HORA_DESDE)
            .horaHasta(UPDATED_HORA_HASTA);
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(updatedBloqueos);

        restBloqueosMockMvc.perform(put("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isOk());

        // Validate the Bloqueos in the database
        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeUpdate);
        Bloqueos testBloqueos = bloqueosList.get(bloqueosList.size() - 1);
        assertThat(testBloqueos.getNombreBloqueo()).isEqualTo(UPDATED_NOMBRE_BLOQUEO);
        assertThat(testBloqueos.getFechaDesde()).isEqualTo(UPDATED_FECHA_DESDE);
        assertThat(testBloqueos.getFechaHasta()).isEqualTo(UPDATED_FECHA_HASTA);
        assertThat(testBloqueos.getHoraDesde()).isEqualTo(UPDATED_HORA_DESDE);
        assertThat(testBloqueos.getHoraHasta()).isEqualTo(UPDATED_HORA_HASTA);
    }

    @Test
    @Transactional
    public void updateNonExistingBloqueos() throws Exception {
        int databaseSizeBeforeUpdate = bloqueosRepository.findAll().size();

        // Create the Bloqueos
        BloqueosDTO bloqueosDTO = bloqueosMapper.toDto(bloqueos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBloqueosMockMvc.perform(put("/api/bloqueos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bloqueos in the database
        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBloqueos() throws Exception {
        // Initialize the database
        bloqueosRepository.saveAndFlush(bloqueos);

        int databaseSizeBeforeDelete = bloqueosRepository.findAll().size();

        // Get the bloqueos
        restBloqueosMockMvc.perform(delete("/api/bloqueos/{id}", bloqueos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bloqueos> bloqueosList = bloqueosRepository.findAll();
        assertThat(bloqueosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bloqueos.class);
        Bloqueos bloqueos1 = new Bloqueos();
        bloqueos1.setId(1L);
        Bloqueos bloqueos2 = new Bloqueos();
        bloqueos2.setId(bloqueos1.getId());
        assertThat(bloqueos1).isEqualTo(bloqueos2);
        bloqueos2.setId(2L);
        assertThat(bloqueos1).isNotEqualTo(bloqueos2);
        bloqueos1.setId(null);
        assertThat(bloqueos1).isNotEqualTo(bloqueos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloqueosDTO.class);
        BloqueosDTO bloqueosDTO1 = new BloqueosDTO();
        bloqueosDTO1.setId(1L);
        BloqueosDTO bloqueosDTO2 = new BloqueosDTO();
        assertThat(bloqueosDTO1).isNotEqualTo(bloqueosDTO2);
        bloqueosDTO2.setId(bloqueosDTO1.getId());
        assertThat(bloqueosDTO1).isEqualTo(bloqueosDTO2);
        bloqueosDTO2.setId(2L);
        assertThat(bloqueosDTO1).isNotEqualTo(bloqueosDTO2);
        bloqueosDTO1.setId(null);
        assertThat(bloqueosDTO1).isNotEqualTo(bloqueosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bloqueosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bloqueosMapper.fromId(null)).isNull();
    }
}
