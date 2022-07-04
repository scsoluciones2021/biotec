package gestWeb.web.rest;

import gestWeb.GestWebApp;

import gestWeb.domain.Bebida;
import gestWeb.repository.BebidaRepository;
import gestWeb.service.BebidaService;
import gestWeb.service.dto.BebidaDTO;
import gestWeb.service.mapper.BebidaMapper;
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
 * Test class for the BebidaResource REST controller.
 *
 * @see BebidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestWebApp.class)
public class BebidaResourceIntTest {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private BebidaRepository bebidaRepository;


    @Autowired
    private BebidaMapper bebidaMapper;
    

    @Autowired
    private BebidaService bebidaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBebidaMockMvc;

    private Bebida bebida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BebidaResource bebidaResource = new BebidaResource(bebidaService);
        this.restBebidaMockMvc = MockMvcBuilders.standaloneSetup(bebidaResource)
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
    public static Bebida createEntity(EntityManager em) {
        Bebida bebida = new Bebida()
            .valor(DEFAULT_VALOR);
        return bebida;
    }

    @Before
    public void initTest() {
        bebida = createEntity(em);
    }

    @Test
    @Transactional
    public void createBebida() throws Exception {
        int databaseSizeBeforeCreate = bebidaRepository.findAll().size();

        // Create the Bebida
        BebidaDTO bebidaDTO = bebidaMapper.toDto(bebida);
        restBebidaMockMvc.perform(post("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeCreate + 1);
        Bebida testBebida = bebidaList.get(bebidaList.size() - 1);
        assertThat(testBebida.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createBebidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bebidaRepository.findAll().size();

        // Create the Bebida with an existing ID
        bebida.setId(1L);
        BebidaDTO bebidaDTO = bebidaMapper.toDto(bebida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBebidaMockMvc.perform(post("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = bebidaRepository.findAll().size();
        // set the field null
        bebida.setValor(null);

        // Create the Bebida, which fails.
        BebidaDTO bebidaDTO = bebidaMapper.toDto(bebida);

        restBebidaMockMvc.perform(post("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebidaDTO)))
            .andExpect(status().isBadRequest());

        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBebidas() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        // Get all the bebidaList
        restBebidaMockMvc.perform(get("/api/bebidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bebida.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    

    @Test
    @Transactional
    public void getBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        // Get the bebida
        restBebidaMockMvc.perform(get("/api/bebidas/{id}", bebida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bebida.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBebida() throws Exception {
        // Get the bebida
        restBebidaMockMvc.perform(get("/api/bebidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        int databaseSizeBeforeUpdate = bebidaRepository.findAll().size();

        // Update the bebida
        Bebida updatedBebida = bebidaRepository.findById(bebida.getId()).get();
        // Disconnect from session so that the updates on updatedBebida are not directly saved in db
        em.detach(updatedBebida);
        updatedBebida
            .valor(UPDATED_VALOR);
        BebidaDTO bebidaDTO = bebidaMapper.toDto(updatedBebida);

        restBebidaMockMvc.perform(put("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebidaDTO)))
            .andExpect(status().isOk());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeUpdate);
        Bebida testBebida = bebidaList.get(bebidaList.size() - 1);
        assertThat(testBebida.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingBebida() throws Exception {
        int databaseSizeBeforeUpdate = bebidaRepository.findAll().size();

        // Create the Bebida
        BebidaDTO bebidaDTO = bebidaMapper.toDto(bebida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBebidaMockMvc.perform(put("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        int databaseSizeBeforeDelete = bebidaRepository.findAll().size();

        // Get the bebida
        restBebidaMockMvc.perform(delete("/api/bebidas/{id}", bebida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bebida.class);
        Bebida bebida1 = new Bebida();
        bebida1.setId(1L);
        Bebida bebida2 = new Bebida();
        bebida2.setId(bebida1.getId());
        assertThat(bebida1).isEqualTo(bebida2);
        bebida2.setId(2L);
        assertThat(bebida1).isNotEqualTo(bebida2);
        bebida1.setId(null);
        assertThat(bebida1).isNotEqualTo(bebida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BebidaDTO.class);
        BebidaDTO bebidaDTO1 = new BebidaDTO();
        bebidaDTO1.setId(1L);
        BebidaDTO bebidaDTO2 = new BebidaDTO();
        assertThat(bebidaDTO1).isNotEqualTo(bebidaDTO2);
        bebidaDTO2.setId(bebidaDTO1.getId());
        assertThat(bebidaDTO1).isEqualTo(bebidaDTO2);
        bebidaDTO2.setId(2L);
        assertThat(bebidaDTO1).isNotEqualTo(bebidaDTO2);
        bebidaDTO1.setId(null);
        assertThat(bebidaDTO1).isNotEqualTo(bebidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bebidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bebidaMapper.fromId(null)).isNull();
    }
}
