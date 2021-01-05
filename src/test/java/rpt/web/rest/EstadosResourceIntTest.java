package rpt.web.rest;

import rpt.CpsjApp;

import rpt.domain.Estados;
import rpt.repository.EstadosRepository;
import rpt.service.EstadosService;
import rpt.service.dto.EstadosDTO;
import rpt.service.mapper.EstadosMapper;
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
 * Test class for the EstadosResource REST controller.
 *
 * @see EstadosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)
public class EstadosResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EstadosRepository estadosRepository;


    @Autowired
    private EstadosMapper estadosMapper;
    

    @Autowired
    private EstadosService estadosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstadosMockMvc;

    private Estados estados;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadosResource estadosResource = new EstadosResource(estadosService);
        this.restEstadosMockMvc = MockMvcBuilders.standaloneSetup(estadosResource)
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
    public static Estados createEntity(EntityManager em) {
        Estados estados = new Estados()
            .nombre(DEFAULT_NOMBRE);
        return estados;
    }

    @Before
    public void initTest() {
        estados = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstados() throws Exception {
        int databaseSizeBeforeCreate = estadosRepository.findAll().size();

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);
        restEstadosMockMvc.perform(post("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isCreated());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate + 1);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createEstadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadosRepository.findAll().size();

        // Create the Estados with an existing ID
        estados.setId(1L);
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadosMockMvc.perform(post("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get all the estadosList
        restEstadosMockMvc.perform(get("/api/estados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    

    @Test
    @Transactional
    public void getEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get the estados
        restEstadosMockMvc.perform(get("/api/estados/{id}", estados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estados.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEstados() throws Exception {
        // Get the estados
        restEstadosMockMvc.perform(get("/api/estados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Update the estados
        Estados updatedEstados = estadosRepository.findById(estados.getId()).get();
        // Disconnect from session so that the updates on updatedEstados are not directly saved in db
        em.detach(updatedEstados);
        updatedEstados
            .nombre(UPDATED_NOMBRE);
        EstadosDTO estadosDTO = estadosMapper.toDto(updatedEstados);

        restEstadosMockMvc.perform(put("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isOk());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstadosMockMvc.perform(put("/api/estados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeDelete = estadosRepository.findAll().size();

        // Get the estados
        restEstadosMockMvc.perform(delete("/api/estados/{id}", estados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estados.class);
        Estados estados1 = new Estados();
        estados1.setId(1L);
        Estados estados2 = new Estados();
        estados2.setId(estados1.getId());
        assertThat(estados1).isEqualTo(estados2);
        estados2.setId(2L);
        assertThat(estados1).isNotEqualTo(estados2);
        estados1.setId(null);
        assertThat(estados1).isNotEqualTo(estados2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadosDTO.class);
        EstadosDTO estadosDTO1 = new EstadosDTO();
        estadosDTO1.setId(1L);
        EstadosDTO estadosDTO2 = new EstadosDTO();
        assertThat(estadosDTO1).isNotEqualTo(estadosDTO2);
        estadosDTO2.setId(estadosDTO1.getId());
        assertThat(estadosDTO1).isEqualTo(estadosDTO2);
        estadosDTO2.setId(2L);
        assertThat(estadosDTO1).isNotEqualTo(estadosDTO2);
        estadosDTO1.setId(null);
        assertThat(estadosDTO1).isNotEqualTo(estadosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estadosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estadosMapper.fromId(null)).isNull();
    }
}
