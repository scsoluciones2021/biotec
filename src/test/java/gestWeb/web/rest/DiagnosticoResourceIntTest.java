package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.Diagnostico;
import gestWeb.repository.DiagnosticoRepository;
import gestWeb.service.DiagnosticoService;
import gestWeb.service.dto.DiagnosticoDTO;
import gestWeb.service.mapper.DiagnosticoMapper;
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
 * Test class for the DiagnosticoResource REST controller.
 *
 * @see DiagnosticoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class DiagnosticoResourceIntTest {

    private static final String DEFAULT_CODIGO_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DIAGNOSTICO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_DIAGNOSTICO = "BBBBBBBBBB";

    @Autowired
    private DiagnosticoRepository diagnosticoRepository;


    @Autowired
    private DiagnosticoMapper diagnosticoMapper;
    

    @Autowired
    private DiagnosticoService diagnosticoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiagnosticoMockMvc;

    private Diagnostico diagnostico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiagnosticoResource diagnosticoResource = new DiagnosticoResource(diagnosticoService);
        this.restDiagnosticoMockMvc = MockMvcBuilders.standaloneSetup(diagnosticoResource)
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
    public static Diagnostico createEntity(EntityManager em) {
        Diagnostico diagnostico = new Diagnostico()
            .codigoDiagnostico(DEFAULT_CODIGO_DIAGNOSTICO)
            .descripcionDiagnostico(DEFAULT_DESCRIPCION_DIAGNOSTICO);
        return diagnostico;
    }

    @Before
    public void initTest() {
        diagnostico = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnostico() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoRepository.findAll().size();

        // Create the Diagnostico
        DiagnosticoDTO diagnosticoDTO = diagnosticoMapper.toDto(diagnostico);
        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoDTO)))
            .andExpect(status().isCreated());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnostico testDiagnostico = diagnosticoList.get(diagnosticoList.size() - 1);
        assertThat(testDiagnostico.getCodigoDiagnostico()).isEqualTo(DEFAULT_CODIGO_DIAGNOSTICO);
        assertThat(testDiagnostico.getDescripcionDiagnostico()).isEqualTo(DEFAULT_DESCRIPCION_DIAGNOSTICO);
    }

    @Test
    @Transactional
    public void createDiagnosticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoRepository.findAll().size();

        // Create the Diagnostico with an existing ID
        diagnostico.setId(1L);
        DiagnosticoDTO diagnosticoDTO = diagnosticoMapper.toDto(diagnostico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiagnosticos() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        // Get all the diagnosticoList
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnostico.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoDiagnostico").value(hasItem(DEFAULT_CODIGO_DIAGNOSTICO.toString())))
            .andExpect(jsonPath("$.[*].descripcionDiagnostico").value(hasItem(DEFAULT_DESCRIPCION_DIAGNOSTICO.toString())));
    }
    

    @Test
    @Transactional
    public void getDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        // Get the diagnostico
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos/{id}", diagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diagnostico.getId().intValue()))
            .andExpect(jsonPath("$.codigoDiagnostico").value(DEFAULT_CODIGO_DIAGNOSTICO.toString()))
            .andExpect(jsonPath("$.descripcionDiagnostico").value(DEFAULT_DESCRIPCION_DIAGNOSTICO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDiagnostico() throws Exception {
        // Get the diagnostico
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        int databaseSizeBeforeUpdate = diagnosticoRepository.findAll().size();

        // Update the diagnostico
        Diagnostico updatedDiagnostico = diagnosticoRepository.findById(diagnostico.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnostico are not directly saved in db
        em.detach(updatedDiagnostico);
        updatedDiagnostico
            .codigoDiagnostico(UPDATED_CODIGO_DIAGNOSTICO)
            .descripcionDiagnostico(UPDATED_DESCRIPCION_DIAGNOSTICO);
        DiagnosticoDTO diagnosticoDTO = diagnosticoMapper.toDto(updatedDiagnostico);

        restDiagnosticoMockMvc.perform(put("/api/diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoDTO)))
            .andExpect(status().isOk());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeUpdate);
        Diagnostico testDiagnostico = diagnosticoList.get(diagnosticoList.size() - 1);
        assertThat(testDiagnostico.getCodigoDiagnostico()).isEqualTo(UPDATED_CODIGO_DIAGNOSTICO);
        assertThat(testDiagnostico.getDescripcionDiagnostico()).isEqualTo(UPDATED_DESCRIPCION_DIAGNOSTICO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnostico() throws Exception {
        int databaseSizeBeforeUpdate = diagnosticoRepository.findAll().size();

        // Create the Diagnostico
        DiagnosticoDTO diagnosticoDTO = diagnosticoMapper.toDto(diagnostico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiagnosticoMockMvc.perform(put("/api/diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        int databaseSizeBeforeDelete = diagnosticoRepository.findAll().size();

        // Get the diagnostico
        restDiagnosticoMockMvc.perform(delete("/api/diagnosticos/{id}", diagnostico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diagnostico.class);
        Diagnostico diagnostico1 = new Diagnostico();
        diagnostico1.setId(1L);
        Diagnostico diagnostico2 = new Diagnostico();
        diagnostico2.setId(diagnostico1.getId());
        assertThat(diagnostico1).isEqualTo(diagnostico2);
        diagnostico2.setId(2L);
        assertThat(diagnostico1).isNotEqualTo(diagnostico2);
        diagnostico1.setId(null);
        assertThat(diagnostico1).isNotEqualTo(diagnostico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticoDTO.class);
        DiagnosticoDTO diagnosticoDTO1 = new DiagnosticoDTO();
        diagnosticoDTO1.setId(1L);
        DiagnosticoDTO diagnosticoDTO2 = new DiagnosticoDTO();
        assertThat(diagnosticoDTO1).isNotEqualTo(diagnosticoDTO2);
        diagnosticoDTO2.setId(diagnosticoDTO1.getId());
        assertThat(diagnosticoDTO1).isEqualTo(diagnosticoDTO2);
        diagnosticoDTO2.setId(2L);
        assertThat(diagnosticoDTO1).isNotEqualTo(diagnosticoDTO2);
        diagnosticoDTO1.setId(null);
        assertThat(diagnosticoDTO1).isNotEqualTo(diagnosticoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diagnosticoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diagnosticoMapper.fromId(null)).isNull();
    }
}
