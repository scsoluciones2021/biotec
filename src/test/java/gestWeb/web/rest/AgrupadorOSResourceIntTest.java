package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.AgrupadorOS;
import gestWeb.repository.AgrupadorOSRepository;
import gestWeb.service.AgrupadorOSService;
import gestWeb.service.dto.AgrupadorOSDTO;
import gestWeb.service.mapper.AgrupadorOSMapper;
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
 * Test class for the AgrupadorOSResource REST controller.
 *
 * @see AgrupadorOSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class AgrupadorOSResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AgrupadorOSRepository agrupadorOSRepository;


    @Autowired
    private AgrupadorOSMapper agrupadorOSMapper;
    

    @Autowired
    private AgrupadorOSService agrupadorOSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgrupadorOSMockMvc;

    private AgrupadorOS agrupadorOS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgrupadorOSResource agrupadorOSResource = new AgrupadorOSResource(agrupadorOSService);
        this.restAgrupadorOSMockMvc = MockMvcBuilders.standaloneSetup(agrupadorOSResource)
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
    public static AgrupadorOS createEntity(EntityManager em) {
        AgrupadorOS agrupadorOS = new AgrupadorOS()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return agrupadorOS;
    }

    @Before
    public void initTest() {
        agrupadorOS = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgrupadorOS() throws Exception {
        int databaseSizeBeforeCreate = agrupadorOSRepository.findAll().size();

        // Create the AgrupadorOS
        AgrupadorOSDTO agrupadorOSDTO = agrupadorOSMapper.toDto(agrupadorOS);
        restAgrupadorOSMockMvc.perform(post("/api/agrupador-os")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agrupadorOSDTO)))
            .andExpect(status().isCreated());

        // Validate the AgrupadorOS in the database
        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeCreate + 1);
        AgrupadorOS testAgrupadorOS = agrupadorOSList.get(agrupadorOSList.size() - 1);
        assertThat(testAgrupadorOS.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAgrupadorOS.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAgrupadorOSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agrupadorOSRepository.findAll().size();

        // Create the AgrupadorOS with an existing ID
        agrupadorOS.setId(1L);
        AgrupadorOSDTO agrupadorOSDTO = agrupadorOSMapper.toDto(agrupadorOS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgrupadorOSMockMvc.perform(post("/api/agrupador-os")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agrupadorOSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgrupadorOS in the database
        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = agrupadorOSRepository.findAll().size();
        // set the field null
        agrupadorOS.setNombre(null);

        // Create the AgrupadorOS, which fails.
        AgrupadorOSDTO agrupadorOSDTO = agrupadorOSMapper.toDto(agrupadorOS);

        restAgrupadorOSMockMvc.perform(post("/api/agrupador-os")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agrupadorOSDTO)))
            .andExpect(status().isBadRequest());

        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgrupadorOS() throws Exception {
        // Initialize the database
        agrupadorOSRepository.saveAndFlush(agrupadorOS);

        // Get all the agrupadorOSList
        restAgrupadorOSMockMvc.perform(get("/api/agrupador-os?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agrupadorOS.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    

    @Test
    @Transactional
    public void getAgrupadorOS() throws Exception {
        // Initialize the database
        agrupadorOSRepository.saveAndFlush(agrupadorOS);

        // Get the agrupadorOS
        restAgrupadorOSMockMvc.perform(get("/api/agrupador-os/{id}", agrupadorOS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agrupadorOS.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAgrupadorOS() throws Exception {
        // Get the agrupadorOS
        restAgrupadorOSMockMvc.perform(get("/api/agrupador-os/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgrupadorOS() throws Exception {
        // Initialize the database
        agrupadorOSRepository.saveAndFlush(agrupadorOS);

        int databaseSizeBeforeUpdate = agrupadorOSRepository.findAll().size();

        // Update the agrupadorOS
        AgrupadorOS updatedAgrupadorOS = agrupadorOSRepository.findById(agrupadorOS.getId()).get();
        // Disconnect from session so that the updates on updatedAgrupadorOS are not directly saved in db
        em.detach(updatedAgrupadorOS);
        updatedAgrupadorOS
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        AgrupadorOSDTO agrupadorOSDTO = agrupadorOSMapper.toDto(updatedAgrupadorOS);

        restAgrupadorOSMockMvc.perform(put("/api/agrupador-os")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agrupadorOSDTO)))
            .andExpect(status().isOk());

        // Validate the AgrupadorOS in the database
        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeUpdate);
        AgrupadorOS testAgrupadorOS = agrupadorOSList.get(agrupadorOSList.size() - 1);
        assertThat(testAgrupadorOS.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAgrupadorOS.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAgrupadorOS() throws Exception {
        int databaseSizeBeforeUpdate = agrupadorOSRepository.findAll().size();

        // Create the AgrupadorOS
        AgrupadorOSDTO agrupadorOSDTO = agrupadorOSMapper.toDto(agrupadorOS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgrupadorOSMockMvc.perform(put("/api/agrupador-os")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agrupadorOSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgrupadorOS in the database
        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgrupadorOS() throws Exception {
        // Initialize the database
        agrupadorOSRepository.saveAndFlush(agrupadorOS);

        int databaseSizeBeforeDelete = agrupadorOSRepository.findAll().size();

        // Get the agrupadorOS
        restAgrupadorOSMockMvc.perform(delete("/api/agrupador-os/{id}", agrupadorOS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgrupadorOS> agrupadorOSList = agrupadorOSRepository.findAll();
        assertThat(agrupadorOSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgrupadorOS.class);
        AgrupadorOS agrupadorOS1 = new AgrupadorOS();
        agrupadorOS1.setId(1L);
        AgrupadorOS agrupadorOS2 = new AgrupadorOS();
        agrupadorOS2.setId(agrupadorOS1.getId());
        assertThat(agrupadorOS1).isEqualTo(agrupadorOS2);
        agrupadorOS2.setId(2L);
        assertThat(agrupadorOS1).isNotEqualTo(agrupadorOS2);
        agrupadorOS1.setId(null);
        assertThat(agrupadorOS1).isNotEqualTo(agrupadorOS2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgrupadorOSDTO.class);
        AgrupadorOSDTO agrupadorOSDTO1 = new AgrupadorOSDTO();
        agrupadorOSDTO1.setId(1L);
        AgrupadorOSDTO agrupadorOSDTO2 = new AgrupadorOSDTO();
        assertThat(agrupadorOSDTO1).isNotEqualTo(agrupadorOSDTO2);
        agrupadorOSDTO2.setId(agrupadorOSDTO1.getId());
        assertThat(agrupadorOSDTO1).isEqualTo(agrupadorOSDTO2);
        agrupadorOSDTO2.setId(2L);
        assertThat(agrupadorOSDTO1).isNotEqualTo(agrupadorOSDTO2);
        agrupadorOSDTO1.setId(null);
        assertThat(agrupadorOSDTO1).isNotEqualTo(agrupadorOSDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agrupadorOSMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agrupadorOSMapper.fromId(null)).isNull();
    }
}
