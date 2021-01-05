package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.AntecedentesFamiliares;
import rpt.repository.AntecedentesFamiliaresRepository;
import rpt.service.AntecedentesFamiliaresService;
import rpt.service.dto.AntecedentesFamiliaresDTO;
import rpt.service.mapper.AntecedentesFamiliaresMapper;
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
 * Test class for the AntecedentesFamiliaresResource REST controller.
 *
 * @see AntecedentesFamiliaresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class AntecedentesFamiliaresResourceIntTest {

    private static final Boolean DEFAULT_VIVO_AF = false;
    private static final Boolean UPDATED_VIVO_AF = true;

    private static final String DEFAULT_CAUSA_MUERTE_AF = "AAAAAAAAAA";
    private static final String UPDATED_CAUSA_MUERTE_AF = "BBBBBBBBBB";

    @Autowired
    private AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;


    @Autowired
    private AntecedentesFamiliaresMapper antecedentesFamiliaresMapper;
    

    @Autowired
    private AntecedentesFamiliaresService antecedentesFamiliaresService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAntecedentesFamiliaresMockMvc;

    private AntecedentesFamiliares antecedentesFamiliares;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AntecedentesFamiliaresResource antecedentesFamiliaresResource = new AntecedentesFamiliaresResource(antecedentesFamiliaresService);
        this.restAntecedentesFamiliaresMockMvc = MockMvcBuilders.standaloneSetup(antecedentesFamiliaresResource)
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
    public static AntecedentesFamiliares createEntity(EntityManager em) {
        AntecedentesFamiliares antecedentesFamiliares = new AntecedentesFamiliares()
            .vivoAF(DEFAULT_VIVO_AF)
            .causaMuerteAF(DEFAULT_CAUSA_MUERTE_AF);
        return antecedentesFamiliares;
    }

    @Before
    public void initTest() {
        antecedentesFamiliares = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntecedentesFamiliares() throws Exception {
        int databaseSizeBeforeCreate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO = antecedentesFamiliaresMapper.toDto(antecedentesFamiliares);
        restAntecedentesFamiliaresMockMvc.perform(post("/api/antecedentes-familiares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliaresDTO)))
            .andExpect(status().isCreated());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeCreate + 1);
        AntecedentesFamiliares testAntecedentesFamiliares = antecedentesFamiliaresList.get(antecedentesFamiliaresList.size() - 1);
        assertThat(testAntecedentesFamiliares.isVivoAF()).isEqualTo(DEFAULT_VIVO_AF);
        assertThat(testAntecedentesFamiliares.getCausaMuerteAF()).isEqualTo(DEFAULT_CAUSA_MUERTE_AF);
    }

    @Test
    @Transactional
    public void createAntecedentesFamiliaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares with an existing ID
        antecedentesFamiliares.setId(1L);
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO = antecedentesFamiliaresMapper.toDto(antecedentesFamiliares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntecedentesFamiliaresMockMvc.perform(post("/api/antecedentes-familiares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        // Get all the antecedentesFamiliaresList
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antecedentesFamiliares.getId().intValue())))
            .andExpect(jsonPath("$.[*].vivoAF").value(hasItem(DEFAULT_VIVO_AF.booleanValue())))
            .andExpect(jsonPath("$.[*].causaMuerteAF").value(hasItem(DEFAULT_CAUSA_MUERTE_AF.toString())));
    }
    

    @Test
    @Transactional
    public void getAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        // Get the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares/{id}", antecedentesFamiliares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(antecedentesFamiliares.getId().intValue()))
            .andExpect(jsonPath("$.vivoAF").value(DEFAULT_VIVO_AF.booleanValue()))
            .andExpect(jsonPath("$.causaMuerteAF").value(DEFAULT_CAUSA_MUERTE_AF.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAntecedentesFamiliares() throws Exception {
        // Get the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        int databaseSizeBeforeUpdate = antecedentesFamiliaresRepository.findAll().size();

        // Update the antecedentesFamiliares
        AntecedentesFamiliares updatedAntecedentesFamiliares = antecedentesFamiliaresRepository.findById(antecedentesFamiliares.getId()).get();
        // Disconnect from session so that the updates on updatedAntecedentesFamiliares are not directly saved in db
        em.detach(updatedAntecedentesFamiliares);
        updatedAntecedentesFamiliares
            .vivoAF(UPDATED_VIVO_AF)
            .causaMuerteAF(UPDATED_CAUSA_MUERTE_AF);
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO = antecedentesFamiliaresMapper.toDto(updatedAntecedentesFamiliares);

        restAntecedentesFamiliaresMockMvc.perform(put("/api/antecedentes-familiares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliaresDTO)))
            .andExpect(status().isOk());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeUpdate);
        AntecedentesFamiliares testAntecedentesFamiliares = antecedentesFamiliaresList.get(antecedentesFamiliaresList.size() - 1);
        assertThat(testAntecedentesFamiliares.isVivoAF()).isEqualTo(UPDATED_VIVO_AF);
        assertThat(testAntecedentesFamiliares.getCausaMuerteAF()).isEqualTo(UPDATED_CAUSA_MUERTE_AF);
    }

    @Test
    @Transactional
    public void updateNonExistingAntecedentesFamiliares() throws Exception {
        int databaseSizeBeforeUpdate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO = antecedentesFamiliaresMapper.toDto(antecedentesFamiliares);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAntecedentesFamiliaresMockMvc.perform(put("/api/antecedentes-familiares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        int databaseSizeBeforeDelete = antecedentesFamiliaresRepository.findAll().size();

        // Get the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(delete("/api/antecedentes-familiares/{id}", antecedentesFamiliares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesFamiliares.class);
        AntecedentesFamiliares antecedentesFamiliares1 = new AntecedentesFamiliares();
        antecedentesFamiliares1.setId(1L);
        AntecedentesFamiliares antecedentesFamiliares2 = new AntecedentesFamiliares();
        antecedentesFamiliares2.setId(antecedentesFamiliares1.getId());
        assertThat(antecedentesFamiliares1).isEqualTo(antecedentesFamiliares2);
        antecedentesFamiliares2.setId(2L);
        assertThat(antecedentesFamiliares1).isNotEqualTo(antecedentesFamiliares2);
        antecedentesFamiliares1.setId(null);
        assertThat(antecedentesFamiliares1).isNotEqualTo(antecedentesFamiliares2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesFamiliaresDTO.class);
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO1 = new AntecedentesFamiliaresDTO();
        antecedentesFamiliaresDTO1.setId(1L);
        AntecedentesFamiliaresDTO antecedentesFamiliaresDTO2 = new AntecedentesFamiliaresDTO();
        assertThat(antecedentesFamiliaresDTO1).isNotEqualTo(antecedentesFamiliaresDTO2);
        antecedentesFamiliaresDTO2.setId(antecedentesFamiliaresDTO1.getId());
        assertThat(antecedentesFamiliaresDTO1).isEqualTo(antecedentesFamiliaresDTO2);
        antecedentesFamiliaresDTO2.setId(2L);
        assertThat(antecedentesFamiliaresDTO1).isNotEqualTo(antecedentesFamiliaresDTO2);
        antecedentesFamiliaresDTO1.setId(null);
        assertThat(antecedentesFamiliaresDTO1).isNotEqualTo(antecedentesFamiliaresDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(antecedentesFamiliaresMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(antecedentesFamiliaresMapper.fromId(null)).isNull();
    }
}
