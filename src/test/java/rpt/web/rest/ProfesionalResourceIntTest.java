package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.Profesional;
import rpt.repository.ProfesionalRepository;
import rpt.service.ProfesionalService;
import rpt.service.dto.ProfesionalDTO;
import rpt.service.mapper.ProfesionalMapper;
import rpt.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static rpt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfesionalResource REST controller.
 *
 * @see ProfesionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class ProfesionalResourceIntTest {

    private static final String DEFAULT_NOMBRE_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA_PROFESIONAL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_PROFESIONAL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_PROFESIONAL = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_PROFESIONAL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_PROFESIONAL_CONTENT_TYPE = "image/png";

    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Mock
    private ProfesionalRepository profesionalRepositoryMock;

    @Autowired
    private ProfesionalMapper profesionalMapper;
    
    @Mock
    private ProfesionalService profesionalServiceMock;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfesionalMockMvc;

    private Profesional profesional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfesionalResource profesionalResource = new ProfesionalResource(profesionalService);
        this.restProfesionalMockMvc = MockMvcBuilders.standaloneSetup(profesionalResource)
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
    public static Profesional createEntity(EntityManager em) {
        Profesional profesional = new Profesional()
            .nombreProfesional(DEFAULT_NOMBRE_PROFESIONAL)
            .documentoProfesional(DEFAULT_DOCUMENTO_PROFESIONAL)
            .direccionProfesional(DEFAULT_DIRECCION_PROFESIONAL)
            .telefonoProfesional(DEFAULT_TELEFONO_PROFESIONAL)
            .emailProfesional(DEFAULT_EMAIL_PROFESIONAL)
            .matriculaProfesional(DEFAULT_MATRICULA_PROFESIONAL)
            .imagenProfesional(DEFAULT_IMAGEN_PROFESIONAL)
            .imagenProfesionalContentType(DEFAULT_IMAGEN_PROFESIONAL_CONTENT_TYPE);
        return profesional;
    }

    @Before
    public void initTest() {
        profesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesional() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isCreated());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate + 1);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getNombreProfesional()).isEqualTo(DEFAULT_NOMBRE_PROFESIONAL);
        assertThat(testProfesional.getDocumentoProfesional()).isEqualTo(DEFAULT_DOCUMENTO_PROFESIONAL);
        assertThat(testProfesional.getDireccionProfesional()).isEqualTo(DEFAULT_DIRECCION_PROFESIONAL);
        assertThat(testProfesional.getTelefonoProfesional()).isEqualTo(DEFAULT_TELEFONO_PROFESIONAL);
        assertThat(testProfesional.getEmailProfesional()).isEqualTo(DEFAULT_EMAIL_PROFESIONAL);
        assertThat(testProfesional.getMatriculaProfesional()).isEqualTo(DEFAULT_MATRICULA_PROFESIONAL);
        assertThat(testProfesional.getImagenProfesional()).isEqualTo(DEFAULT_IMAGEN_PROFESIONAL);
        assertThat(testProfesional.getImagenProfesionalContentType()).isEqualTo(DEFAULT_IMAGEN_PROFESIONAL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional with an existing ID
        profesional.setId(1L);
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreProfesionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesionalRepository.findAll().size();
        // set the field null
        profesional.setNombreProfesional(null);

        // Create the Profesional, which fails.
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentoProfesionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesionalRepository.findAll().size();
        // set the field null
        profesional.setDocumentoProfesional(null);

        // Create the Profesional, which fails.
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatriculaProfesionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesionalRepository.findAll().size();
        // set the field null
        profesional.setMatriculaProfesional(null);

        // Create the Profesional, which fails.
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfesionals() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get all the profesionalList
        restProfesionalMockMvc.perform(get("/api/profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProfesional").value(hasItem(DEFAULT_NOMBRE_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].documentoProfesional").value(hasItem(DEFAULT_DOCUMENTO_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].direccionProfesional").value(hasItem(DEFAULT_DIRECCION_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].telefonoProfesional").value(hasItem(DEFAULT_TELEFONO_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].emailProfesional").value(hasItem(DEFAULT_EMAIL_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].matriculaProfesional").value(hasItem(DEFAULT_MATRICULA_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].imagenProfesionalContentType").value(hasItem(DEFAULT_IMAGEN_PROFESIONAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenProfesional").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_PROFESIONAL))));
    }
    
    public void getAllProfesionalsWithEagerRelationshipsIsEnabled() throws Exception {
        ProfesionalResource profesionalResource = new ProfesionalResource(profesionalServiceMock);
        when(profesionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProfesionalMockMvc = MockMvcBuilders.standaloneSetup(profesionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfesionalMockMvc.perform(get("/api/profesionals?eagerload=true"))
        .andExpect(status().isOk());

        verify(profesionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllProfesionalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProfesionalResource profesionalResource = new ProfesionalResource(profesionalServiceMock);
            when(profesionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProfesionalMockMvc = MockMvcBuilders.standaloneSetup(profesionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfesionalMockMvc.perform(get("/api/profesionals?eagerload=true"))
        .andExpect(status().isOk());

            verify(profesionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", profesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profesional.getId().intValue()))
            .andExpect(jsonPath("$.nombreProfesional").value(DEFAULT_NOMBRE_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.documentoProfesional").value(DEFAULT_DOCUMENTO_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.direccionProfesional").value(DEFAULT_DIRECCION_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.telefonoProfesional").value(DEFAULT_TELEFONO_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.emailProfesional").value(DEFAULT_EMAIL_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.matriculaProfesional").value(DEFAULT_MATRICULA_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.imagenProfesionalContentType").value(DEFAULT_IMAGEN_PROFESIONAL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenProfesional").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_PROFESIONAL)));
    }
    @Test
    @Transactional
    public void getNonExistingProfesional() throws Exception {
        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Update the profesional
        Profesional updatedProfesional = profesionalRepository.findById(profesional.getId()).get();
        // Disconnect from session so that the updates on updatedProfesional are not directly saved in db
        em.detach(updatedProfesional);
        updatedProfesional
            .nombreProfesional(UPDATED_NOMBRE_PROFESIONAL)
            .documentoProfesional(UPDATED_DOCUMENTO_PROFESIONAL)
            .direccionProfesional(UPDATED_DIRECCION_PROFESIONAL)
            .telefonoProfesional(UPDATED_TELEFONO_PROFESIONAL)
            .emailProfesional(UPDATED_EMAIL_PROFESIONAL)
            .matriculaProfesional(UPDATED_MATRICULA_PROFESIONAL)
            .imagenProfesional(UPDATED_IMAGEN_PROFESIONAL)
            .imagenProfesionalContentType(UPDATED_IMAGEN_PROFESIONAL_CONTENT_TYPE);
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(updatedProfesional);

        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isOk());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getNombreProfesional()).isEqualTo(UPDATED_NOMBRE_PROFESIONAL);
        assertThat(testProfesional.getDocumentoProfesional()).isEqualTo(UPDATED_DOCUMENTO_PROFESIONAL);
        assertThat(testProfesional.getDireccionProfesional()).isEqualTo(UPDATED_DIRECCION_PROFESIONAL);
        assertThat(testProfesional.getTelefonoProfesional()).isEqualTo(UPDATED_TELEFONO_PROFESIONAL);
        assertThat(testProfesional.getEmailProfesional()).isEqualTo(UPDATED_EMAIL_PROFESIONAL);
        assertThat(testProfesional.getMatriculaProfesional()).isEqualTo(UPDATED_MATRICULA_PROFESIONAL);
        assertThat(testProfesional.getImagenProfesional()).isEqualTo(UPDATED_IMAGEN_PROFESIONAL);
        assertThat(testProfesional.getImagenProfesionalContentType()).isEqualTo(UPDATED_IMAGEN_PROFESIONAL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesional() throws Exception {
        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Create the Profesional
        ProfesionalDTO profesionalDTO = profesionalMapper.toDto(profesional);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        int databaseSizeBeforeDelete = profesionalRepository.findAll().size();

        // Get the profesional
        restProfesionalMockMvc.perform(delete("/api/profesionals/{id}", profesional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesional.class);
        Profesional profesional1 = new Profesional();
        profesional1.setId(1L);
        Profesional profesional2 = new Profesional();
        profesional2.setId(profesional1.getId());
        assertThat(profesional1).isEqualTo(profesional2);
        profesional2.setId(2L);
        assertThat(profesional1).isNotEqualTo(profesional2);
        profesional1.setId(null);
        assertThat(profesional1).isNotEqualTo(profesional2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfesionalDTO.class);
        ProfesionalDTO profesionalDTO1 = new ProfesionalDTO();
        profesionalDTO1.setId(1L);
        ProfesionalDTO profesionalDTO2 = new ProfesionalDTO();
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
        profesionalDTO2.setId(profesionalDTO1.getId());
        assertThat(profesionalDTO1).isEqualTo(profesionalDTO2);
        profesionalDTO2.setId(2L);
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
        profesionalDTO1.setId(null);
        assertThat(profesionalDTO1).isNotEqualTo(profesionalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profesionalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profesionalMapper.fromId(null)).isNull();
    }
}
