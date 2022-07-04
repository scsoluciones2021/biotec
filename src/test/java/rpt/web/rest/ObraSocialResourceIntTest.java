package rpt.web.rest;

import rpt.GestWebApp;

import rpt.domain.ObraSocial;
import rpt.repository.ObraSocialRepository;
import rpt.service.ObraSocialService;
import rpt.service.dto.ObraSocialDTO;
import rpt.service.mapper.ObraSocialMapper;
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
 * Test class for the ObraSocialResource REST controller.
 *
 * @see ObraSocialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class ObraSocialResourceIntTest {

    private static final String DEFAULT_CODIGO_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_OBRA_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_OBRA_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_OBRA_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_OBRA_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_OBRA_SOCIAL = "BBBBBBBBBB";

    @Autowired
    private ObraSocialRepository obraSocialRepository;


    @Autowired
    private ObraSocialMapper obraSocialMapper;
    

    @Autowired
    private ObraSocialService obraSocialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restObraSocialMockMvc;

    private ObraSocial obraSocial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObraSocialResource obraSocialResource = new ObraSocialResource(obraSocialService);
        this.restObraSocialMockMvc = MockMvcBuilders.standaloneSetup(obraSocialResource)
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
    public static ObraSocial createEntity(EntityManager em) {
        ObraSocial obraSocial = new ObraSocial()
            .codigoObraSocial(DEFAULT_CODIGO_OBRA_SOCIAL)
            .nombreObraSocial(DEFAULT_NOMBRE_OBRA_SOCIAL)
            .direccionObraSocial(DEFAULT_DIRECCION_OBRA_SOCIAL)
            .telefonoObraSocial(DEFAULT_TELEFONO_OBRA_SOCIAL)
            .emailObraSocial(DEFAULT_EMAIL_OBRA_SOCIAL);
        return obraSocial;
    }

    @Before
    public void initTest() {
        obraSocial = createEntity(em);
    }

    @Test
    @Transactional
    public void createObraSocial() throws Exception {
        int databaseSizeBeforeCreate = obraSocialRepository.findAll().size();

        // Create the ObraSocial
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);
        restObraSocialMockMvc.perform(post("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isCreated());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeCreate + 1);
        ObraSocial testObraSocial = obraSocialList.get(obraSocialList.size() - 1);
        assertThat(testObraSocial.getCodigoObraSocial()).isEqualTo(DEFAULT_CODIGO_OBRA_SOCIAL);
        assertThat(testObraSocial.getNombreObraSocial()).isEqualTo(DEFAULT_NOMBRE_OBRA_SOCIAL);
        assertThat(testObraSocial.getDireccionObraSocial()).isEqualTo(DEFAULT_DIRECCION_OBRA_SOCIAL);
        assertThat(testObraSocial.getTelefonoObraSocial()).isEqualTo(DEFAULT_TELEFONO_OBRA_SOCIAL);
        assertThat(testObraSocial.getEmailObraSocial()).isEqualTo(DEFAULT_EMAIL_OBRA_SOCIAL);
    }

    @Test
    @Transactional
    public void createObraSocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obraSocialRepository.findAll().size();

        // Create the ObraSocial with an existing ID
        obraSocial.setId(1L);
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraSocialMockMvc.perform(post("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllObraSocials() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        // Get all the obraSocialList
        restObraSocialMockMvc.perform(get("/api/obra-socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obraSocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoObraSocial").value(hasItem(DEFAULT_CODIGO_OBRA_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].nombreObraSocial").value(hasItem(DEFAULT_NOMBRE_OBRA_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].direccionObraSocial").value(hasItem(DEFAULT_DIRECCION_OBRA_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].telefonoObraSocial").value(hasItem(DEFAULT_TELEFONO_OBRA_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].emailObraSocial").value(hasItem(DEFAULT_EMAIL_OBRA_SOCIAL.toString())));
    }
    

    @Test
    @Transactional
    public void getObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        // Get the obraSocial
        restObraSocialMockMvc.perform(get("/api/obra-socials/{id}", obraSocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obraSocial.getId().intValue()))
            .andExpect(jsonPath("$.codigoObraSocial").value(DEFAULT_CODIGO_OBRA_SOCIAL.toString()))
            .andExpect(jsonPath("$.nombreObraSocial").value(DEFAULT_NOMBRE_OBRA_SOCIAL.toString()))
            .andExpect(jsonPath("$.direccionObraSocial").value(DEFAULT_DIRECCION_OBRA_SOCIAL.toString()))
            .andExpect(jsonPath("$.telefonoObraSocial").value(DEFAULT_TELEFONO_OBRA_SOCIAL.toString()))
            .andExpect(jsonPath("$.emailObraSocial").value(DEFAULT_EMAIL_OBRA_SOCIAL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingObraSocial() throws Exception {
        // Get the obraSocial
        restObraSocialMockMvc.perform(get("/api/obra-socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        int databaseSizeBeforeUpdate = obraSocialRepository.findAll().size();

        // Update the obraSocial
        ObraSocial updatedObraSocial = obraSocialRepository.findById(obraSocial.getId()).get();
        // Disconnect from session so that the updates on updatedObraSocial are not directly saved in db
        em.detach(updatedObraSocial);
        updatedObraSocial
            .codigoObraSocial(UPDATED_CODIGO_OBRA_SOCIAL)
            .nombreObraSocial(UPDATED_NOMBRE_OBRA_SOCIAL)
            .direccionObraSocial(UPDATED_DIRECCION_OBRA_SOCIAL)
            .telefonoObraSocial(UPDATED_TELEFONO_OBRA_SOCIAL)
            .emailObraSocial(UPDATED_EMAIL_OBRA_SOCIAL);
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(updatedObraSocial);

        restObraSocialMockMvc.perform(put("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isOk());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeUpdate);
        ObraSocial testObraSocial = obraSocialList.get(obraSocialList.size() - 1);
        assertThat(testObraSocial.getCodigoObraSocial()).isEqualTo(UPDATED_CODIGO_OBRA_SOCIAL);
        assertThat(testObraSocial.getNombreObraSocial()).isEqualTo(UPDATED_NOMBRE_OBRA_SOCIAL);
        assertThat(testObraSocial.getDireccionObraSocial()).isEqualTo(UPDATED_DIRECCION_OBRA_SOCIAL);
        assertThat(testObraSocial.getTelefonoObraSocial()).isEqualTo(UPDATED_TELEFONO_OBRA_SOCIAL);
        assertThat(testObraSocial.getEmailObraSocial()).isEqualTo(UPDATED_EMAIL_OBRA_SOCIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingObraSocial() throws Exception {
        int databaseSizeBeforeUpdate = obraSocialRepository.findAll().size();

        // Create the ObraSocial
        ObraSocialDTO obraSocialDTO = obraSocialMapper.toDto(obraSocial);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObraSocialMockMvc.perform(put("/api/obra-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obraSocialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObraSocial in the database
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObraSocial() throws Exception {
        // Initialize the database
        obraSocialRepository.saveAndFlush(obraSocial);

        int databaseSizeBeforeDelete = obraSocialRepository.findAll().size();

        // Get the obraSocial
        restObraSocialMockMvc.perform(delete("/api/obra-socials/{id}", obraSocial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ObraSocial> obraSocialList = obraSocialRepository.findAll();
        assertThat(obraSocialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraSocial.class);
        ObraSocial obraSocial1 = new ObraSocial();
        obraSocial1.setId(1L);
        ObraSocial obraSocial2 = new ObraSocial();
        obraSocial2.setId(obraSocial1.getId());
        assertThat(obraSocial1).isEqualTo(obraSocial2);
        obraSocial2.setId(2L);
        assertThat(obraSocial1).isNotEqualTo(obraSocial2);
        obraSocial1.setId(null);
        assertThat(obraSocial1).isNotEqualTo(obraSocial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraSocialDTO.class);
        ObraSocialDTO obraSocialDTO1 = new ObraSocialDTO();
        obraSocialDTO1.setId(1L);
        ObraSocialDTO obraSocialDTO2 = new ObraSocialDTO();
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
        obraSocialDTO2.setId(obraSocialDTO1.getId());
        assertThat(obraSocialDTO1).isEqualTo(obraSocialDTO2);
        obraSocialDTO2.setId(2L);
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
        obraSocialDTO1.setId(null);
        assertThat(obraSocialDTO1).isNotEqualTo(obraSocialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(obraSocialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(obraSocialMapper.fromId(null)).isNull();
    }
}
