package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.AntecedentesPersonales;
import gestWeb.repository.AntecedentesPersonalesRepository;
import gestWeb.service.AntecedentesPersonalesService;
import gestWeb.service.dto.AntecedentesPersonalesDTO;
import gestWeb.service.mapper.AntecedentesPersonalesMapper;
import gestWeb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static gestWeb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AntecedentesPersonalesResource REST controller.
 *
 * @see AntecedentesPersonalesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class AntecedentesPersonalesResourceIntTest {

    private static final Boolean DEFAULT_TABACO = false;
    private static final Boolean UPDATED_TABACO = true;

    private static final String DEFAULT_TABACO_OBSERV = "AAAAAAAAAA";
    private static final String UPDATED_TABACO_OBSERV = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TECAFE = false;
    private static final Boolean UPDATED_TECAFE = true;

    @Autowired
    private AntecedentesPersonalesRepository antecedentesPersonalesRepository;
    @Mock
    private AntecedentesPersonalesRepository antecedentesPersonalesRepositoryMock;

    @Autowired
    private AntecedentesPersonalesMapper antecedentesPersonalesMapper;
    
    @Mock
    private AntecedentesPersonalesService antecedentesPersonalesServiceMock;

    @Autowired
    private AntecedentesPersonalesService antecedentesPersonalesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAntecedentesPersonalesMockMvc;

    private AntecedentesPersonales antecedentesPersonales;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AntecedentesPersonalesResource antecedentesPersonalesResource = new AntecedentesPersonalesResource(antecedentesPersonalesService);
        this.restAntecedentesPersonalesMockMvc = MockMvcBuilders.standaloneSetup(antecedentesPersonalesResource)
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
    public static AntecedentesPersonales createEntity(EntityManager em) {
        AntecedentesPersonales antecedentesPersonales = new AntecedentesPersonales()
            .tabaco(DEFAULT_TABACO)
            .tabacoObserv(DEFAULT_TABACO_OBSERV)
            .tecafe(DEFAULT_TECAFE);
        return antecedentesPersonales;
    }

    @Before
    public void initTest() {
        antecedentesPersonales = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntecedentesPersonales() throws Exception {
        int databaseSizeBeforeCreate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales
        AntecedentesPersonalesDTO antecedentesPersonalesDTO = antecedentesPersonalesMapper.toDto(antecedentesPersonales);
        restAntecedentesPersonalesMockMvc.perform(post("/api/antecedentes-personales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonalesDTO)))
            .andExpect(status().isCreated());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeCreate + 1);
        AntecedentesPersonales testAntecedentesPersonales = antecedentesPersonalesList.get(antecedentesPersonalesList.size() - 1);
        assertThat(testAntecedentesPersonales.isTabaco()).isEqualTo(DEFAULT_TABACO);
        assertThat(testAntecedentesPersonales.getTabacoObserv()).isEqualTo(DEFAULT_TABACO_OBSERV);
        assertThat(testAntecedentesPersonales.isTecafe()).isEqualTo(DEFAULT_TECAFE);
    }

    @Test
    @Transactional
    public void createAntecedentesPersonalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales with an existing ID
        antecedentesPersonales.setId(1L);
        AntecedentesPersonalesDTO antecedentesPersonalesDTO = antecedentesPersonalesMapper.toDto(antecedentesPersonales);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntecedentesPersonalesMockMvc.perform(post("/api/antecedentes-personales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonalesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        // Get all the antecedentesPersonalesList
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antecedentesPersonales.getId().intValue())))
            .andExpect(jsonPath("$.[*].tabaco").value(hasItem(DEFAULT_TABACO.booleanValue())))
            .andExpect(jsonPath("$.[*].tabacoObserv").value(hasItem(DEFAULT_TABACO_OBSERV.toString())))
            .andExpect(jsonPath("$.[*].tecafe").value(hasItem(DEFAULT_TECAFE.booleanValue())));
    }
    
    public void getAllAntecedentesPersonalesWithEagerRelationshipsIsEnabled() throws Exception {
        AntecedentesPersonalesResource antecedentesPersonalesResource = new AntecedentesPersonalesResource(antecedentesPersonalesServiceMock);
        when(antecedentesPersonalesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAntecedentesPersonalesMockMvc = MockMvcBuilders.standaloneSetup(antecedentesPersonalesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales?eagerload=true"))
        .andExpect(status().isOk());

        verify(antecedentesPersonalesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllAntecedentesPersonalesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AntecedentesPersonalesResource antecedentesPersonalesResource = new AntecedentesPersonalesResource(antecedentesPersonalesServiceMock);
            when(antecedentesPersonalesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAntecedentesPersonalesMockMvc = MockMvcBuilders.standaloneSetup(antecedentesPersonalesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales?eagerload=true"))
        .andExpect(status().isOk());

            verify(antecedentesPersonalesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        // Get the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales/{id}", antecedentesPersonales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(antecedentesPersonales.getId().intValue()))
            .andExpect(jsonPath("$.tabaco").value(DEFAULT_TABACO.booleanValue()))
            .andExpect(jsonPath("$.tabacoObserv").value(DEFAULT_TABACO_OBSERV.toString()))
            .andExpect(jsonPath("$.tecafe").value(DEFAULT_TECAFE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAntecedentesPersonales() throws Exception {
        // Get the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        int databaseSizeBeforeUpdate = antecedentesPersonalesRepository.findAll().size();

        // Update the antecedentesPersonales
        AntecedentesPersonales updatedAntecedentesPersonales = antecedentesPersonalesRepository.findById(antecedentesPersonales.getId()).get();
        // Disconnect from session so that the updates on updatedAntecedentesPersonales are not directly saved in db
        em.detach(updatedAntecedentesPersonales);
        updatedAntecedentesPersonales
            .tabaco(UPDATED_TABACO)
            .tabacoObserv(UPDATED_TABACO_OBSERV)
            .tecafe(UPDATED_TECAFE);
        AntecedentesPersonalesDTO antecedentesPersonalesDTO = antecedentesPersonalesMapper.toDto(updatedAntecedentesPersonales);

        restAntecedentesPersonalesMockMvc.perform(put("/api/antecedentes-personales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonalesDTO)))
            .andExpect(status().isOk());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeUpdate);
        AntecedentesPersonales testAntecedentesPersonales = antecedentesPersonalesList.get(antecedentesPersonalesList.size() - 1);
        assertThat(testAntecedentesPersonales.isTabaco()).isEqualTo(UPDATED_TABACO);
        assertThat(testAntecedentesPersonales.getTabacoObserv()).isEqualTo(UPDATED_TABACO_OBSERV);
        assertThat(testAntecedentesPersonales.isTecafe()).isEqualTo(UPDATED_TECAFE);
    }

    @Test
    @Transactional
    public void updateNonExistingAntecedentesPersonales() throws Exception {
        int databaseSizeBeforeUpdate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales
        AntecedentesPersonalesDTO antecedentesPersonalesDTO = antecedentesPersonalesMapper.toDto(antecedentesPersonales);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAntecedentesPersonalesMockMvc.perform(put("/api/antecedentes-personales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonalesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        int databaseSizeBeforeDelete = antecedentesPersonalesRepository.findAll().size();

        // Get the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(delete("/api/antecedentes-personales/{id}", antecedentesPersonales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesPersonales.class);
        AntecedentesPersonales antecedentesPersonales1 = new AntecedentesPersonales();
        antecedentesPersonales1.setId(1L);
        AntecedentesPersonales antecedentesPersonales2 = new AntecedentesPersonales();
        antecedentesPersonales2.setId(antecedentesPersonales1.getId());
        assertThat(antecedentesPersonales1).isEqualTo(antecedentesPersonales2);
        antecedentesPersonales2.setId(2L);
        assertThat(antecedentesPersonales1).isNotEqualTo(antecedentesPersonales2);
        antecedentesPersonales1.setId(null);
        assertThat(antecedentesPersonales1).isNotEqualTo(antecedentesPersonales2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesPersonalesDTO.class);
        AntecedentesPersonalesDTO antecedentesPersonalesDTO1 = new AntecedentesPersonalesDTO();
        antecedentesPersonalesDTO1.setId(1L);
        AntecedentesPersonalesDTO antecedentesPersonalesDTO2 = new AntecedentesPersonalesDTO();
        assertThat(antecedentesPersonalesDTO1).isNotEqualTo(antecedentesPersonalesDTO2);
        antecedentesPersonalesDTO2.setId(antecedentesPersonalesDTO1.getId());
        assertThat(antecedentesPersonalesDTO1).isEqualTo(antecedentesPersonalesDTO2);
        antecedentesPersonalesDTO2.setId(2L);
        assertThat(antecedentesPersonalesDTO1).isNotEqualTo(antecedentesPersonalesDTO2);
        antecedentesPersonalesDTO1.setId(null);
        assertThat(antecedentesPersonalesDTO1).isNotEqualTo(antecedentesPersonalesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(antecedentesPersonalesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(antecedentesPersonalesMapper.fromId(null)).isNull();
    }
}
