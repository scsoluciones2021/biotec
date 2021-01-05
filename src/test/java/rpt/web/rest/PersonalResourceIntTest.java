package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Personal;
import rpt.repository.PersonalRepository;
import rpt.service.PersonalService;
import rpt.service.dto.PersonalDTO;
import rpt.service.mapper.PersonalMapper;
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
 * Test class for the PersonalResource REST controller.
 *
 * @see PersonalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class PersonalResourceIntTest {

    private static final String DEFAULT_NOMBRE_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PERSONAL = "BBBBBBBBBB";

    @Autowired
    private PersonalRepository personalRepository;


    @Autowired
    private PersonalMapper personalMapper;
    

    @Autowired
    private PersonalService personalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalMockMvc;

    private Personal personal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalResource personalResource = new PersonalResource(personalService);
        this.restPersonalMockMvc = MockMvcBuilders.standaloneSetup(personalResource)
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
    public static Personal createEntity(EntityManager em) {
        Personal personal = new Personal()
            .nombrePersonal(DEFAULT_NOMBRE_PERSONAL)
            .apellidoPersonal(DEFAULT_APELLIDO_PERSONAL)
            .documentoPersonal(DEFAULT_DOCUMENTO_PERSONAL)
            .direccionPersonal(DEFAULT_DIRECCION_PERSONAL)
            .telefonoPersonal(DEFAULT_TELEFONO_PERSONAL)
            .emailPersonal(DEFAULT_EMAIL_PERSONAL);
        return personal;
    }

    @Before
    public void initTest() {
        personal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonal() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal
        PersonalDTO personalDTO = personalMapper.toDto(personal);
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isCreated());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate + 1);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getNombrePersonal()).isEqualTo(DEFAULT_NOMBRE_PERSONAL);
        assertThat(testPersonal.getApellidoPersonal()).isEqualTo(DEFAULT_APELLIDO_PERSONAL);
        assertThat(testPersonal.getDocumentoPersonal()).isEqualTo(DEFAULT_DOCUMENTO_PERSONAL);
        assertThat(testPersonal.getDireccionPersonal()).isEqualTo(DEFAULT_DIRECCION_PERSONAL);
        assertThat(testPersonal.getTelefonoPersonal()).isEqualTo(DEFAULT_TELEFONO_PERSONAL);
        assertThat(testPersonal.getEmailPersonal()).isEqualTo(DEFAULT_EMAIL_PERSONAL);
    }

    @Test
    @Transactional
    public void createPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal with an existing ID
        personal.setId(1L);
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombrePersonalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setNombrePersonal(null);

        // Create the Personal, which fails.
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoPersonalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setApellidoPersonal(null);

        // Create the Personal, which fails.
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentoPersonalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setDocumentoPersonal(null);

        // Create the Personal, which fails.
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPersonalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setEmailPersonal(null);

        // Create the Personal, which fails.
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonals() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePersonal").value(hasItem(DEFAULT_NOMBRE_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].apellidoPersonal").value(hasItem(DEFAULT_APELLIDO_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].documentoPersonal").value(hasItem(DEFAULT_DOCUMENTO_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].direccionPersonal").value(hasItem(DEFAULT_DIRECCION_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].telefonoPersonal").value(hasItem(DEFAULT_TELEFONO_PERSONAL.toString())))
            .andExpect(jsonPath("$.[*].emailPersonal").value(hasItem(DEFAULT_EMAIL_PERSONAL.toString())));
    }
    

    @Test
    @Transactional
    public void getPersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", personal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personal.getId().intValue()))
            .andExpect(jsonPath("$.nombrePersonal").value(DEFAULT_NOMBRE_PERSONAL.toString()))
            .andExpect(jsonPath("$.apellidoPersonal").value(DEFAULT_APELLIDO_PERSONAL.toString()))
            .andExpect(jsonPath("$.documentoPersonal").value(DEFAULT_DOCUMENTO_PERSONAL.toString()))
            .andExpect(jsonPath("$.direccionPersonal").value(DEFAULT_DIRECCION_PERSONAL.toString()))
            .andExpect(jsonPath("$.telefonoPersonal").value(DEFAULT_TELEFONO_PERSONAL.toString()))
            .andExpect(jsonPath("$.emailPersonal").value(DEFAULT_EMAIL_PERSONAL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPersonal() throws Exception {
        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Update the personal
        Personal updatedPersonal = personalRepository.findById(personal.getId()).get();
        // Disconnect from session so that the updates on updatedPersonal are not directly saved in db
        em.detach(updatedPersonal);
        updatedPersonal
            .nombrePersonal(UPDATED_NOMBRE_PERSONAL)
            .apellidoPersonal(UPDATED_APELLIDO_PERSONAL)
            .documentoPersonal(UPDATED_DOCUMENTO_PERSONAL)
            .direccionPersonal(UPDATED_DIRECCION_PERSONAL)
            .telefonoPersonal(UPDATED_TELEFONO_PERSONAL)
            .emailPersonal(UPDATED_EMAIL_PERSONAL);
        PersonalDTO personalDTO = personalMapper.toDto(updatedPersonal);

        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isOk());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getNombrePersonal()).isEqualTo(UPDATED_NOMBRE_PERSONAL);
        assertThat(testPersonal.getApellidoPersonal()).isEqualTo(UPDATED_APELLIDO_PERSONAL);
        assertThat(testPersonal.getDocumentoPersonal()).isEqualTo(UPDATED_DOCUMENTO_PERSONAL);
        assertThat(testPersonal.getDireccionPersonal()).isEqualTo(UPDATED_DIRECCION_PERSONAL);
        assertThat(testPersonal.getTelefonoPersonal()).isEqualTo(UPDATED_TELEFONO_PERSONAL);
        assertThat(testPersonal.getEmailPersonal()).isEqualTo(UPDATED_EMAIL_PERSONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonal() throws Exception {
        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Create the Personal
        PersonalDTO personalDTO = personalMapper.toDto(personal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        int databaseSizeBeforeDelete = personalRepository.findAll().size();

        // Get the personal
        restPersonalMockMvc.perform(delete("/api/personals/{id}", personal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personal.class);
        Personal personal1 = new Personal();
        personal1.setId(1L);
        Personal personal2 = new Personal();
        personal2.setId(personal1.getId());
        assertThat(personal1).isEqualTo(personal2);
        personal2.setId(2L);
        assertThat(personal1).isNotEqualTo(personal2);
        personal1.setId(null);
        assertThat(personal1).isNotEqualTo(personal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalDTO.class);
        PersonalDTO personalDTO1 = new PersonalDTO();
        personalDTO1.setId(1L);
        PersonalDTO personalDTO2 = new PersonalDTO();
        assertThat(personalDTO1).isNotEqualTo(personalDTO2);
        personalDTO2.setId(personalDTO1.getId());
        assertThat(personalDTO1).isEqualTo(personalDTO2);
        personalDTO2.setId(2L);
        assertThat(personalDTO1).isNotEqualTo(personalDTO2);
        personalDTO1.setId(null);
        assertThat(personalDTO1).isNotEqualTo(personalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personalMapper.fromId(null)).isNull();
    }
}
