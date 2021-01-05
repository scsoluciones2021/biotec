package rpt.web.rest;

import rpt.CpsjApp;
import rpt.domain.PagoConsulta;
import rpt.repository.PagoConsultaRepository;
import rpt.service.PagoConsultaService;
import rpt.service.dto.PagoConsultaDTO;
import rpt.service.mapper.PagoConsultaMapper;
import rpt.web.rest.errors.ExceptionTranslator;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Integration tests for the {@link PagoConsultaResource} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpsjApp.class)

public class PagoConsultaResourceIT {

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;
    private static final Double SMALLER_MONTO = 1D - 1D;

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUPONES = 1;
    private static final Integer UPDATED_CUPONES = 2;
    private static final Integer SMALLER_CUPONES = 1 - 1;

    @Autowired
    private PagoConsultaRepository pagoConsultaRepository;

    @Autowired
    private PagoConsultaMapper pagoConsultaMapper;

    @Autowired
    private PagoConsultaService pagoConsultaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPagoConsultaMockMvc;

    private PagoConsulta pagoConsulta;

   
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagoConsultaResource pagoConsultaResource = new PagoConsultaResource(pagoConsultaService);
        this.restPagoConsultaMockMvc = MockMvcBuilders.standaloneSetup(pagoConsultaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PagoConsulta createEntity(EntityManager em) {
        PagoConsulta pagoConsulta = new PagoConsulta()
            .monto(DEFAULT_MONTO)
            .tipo(DEFAULT_TIPO)
            .cupones(DEFAULT_CUPONES);
        return pagoConsulta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PagoConsulta createUpdatedEntity(EntityManager em) {
        PagoConsulta pagoConsulta = new PagoConsulta()
            .monto(UPDATED_MONTO)
            .tipo(UPDATED_TIPO)
            .cupones(UPDATED_CUPONES);
        return pagoConsulta;
    }

    
    public void initTest() {
        pagoConsulta = createEntity(em);
    }

    
    @Transactional
    public void createPagoConsulta() throws Exception {
        int databaseSizeBeforeCreate = pagoConsultaRepository.findAll().size();

        // Create the PagoConsulta
        PagoConsultaDTO pagoConsultaDTO = pagoConsultaMapper.toDto(pagoConsulta);
        restPagoConsultaMockMvc.perform(post("/api/pago-consultas"));

        // Validate the PagoConsulta in the database
        List<PagoConsulta> pagoConsultaList = pagoConsultaRepository.findAll();
        assertThat(pagoConsultaList).hasSize(databaseSizeBeforeCreate + 1);
        PagoConsulta testPagoConsulta = pagoConsultaList.get(pagoConsultaList.size() - 1);
        assertThat(testPagoConsulta.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testPagoConsulta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPagoConsulta.getCupones()).isEqualTo(DEFAULT_CUPONES);
    }

    
    @Transactional
    public void createPagoConsultaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagoConsultaRepository.findAll().size();

        // Create the PagoConsulta with an existing ID
        pagoConsulta.setId(1L);
        PagoConsultaDTO pagoConsultaDTO = pagoConsultaMapper.toDto(pagoConsulta);


        // Validate the PagoConsulta in the database
        List<PagoConsulta> pagoConsultaList = pagoConsultaRepository.findAll();
        assertThat(pagoConsultaList).hasSize(databaseSizeBeforeCreate);
    }


    
    @Transactional
    public void getAllPagoConsultas() throws Exception {
        // Initialize the database
        pagoConsultaRepository.saveAndFlush(pagoConsulta);

        // Get all the pagoConsultaList
        restPagoConsultaMockMvc.perform(get("/api/pago-consultas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagoConsulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].cupones").value(hasItem(DEFAULT_CUPONES)));
    }
    
    
    @Transactional
    public void getPagoConsulta() throws Exception {
        // Initialize the database
        pagoConsultaRepository.saveAndFlush(pagoConsulta);

        // Get the pagoConsulta
        restPagoConsultaMockMvc.perform(get("/api/pago-consultas/{id}", pagoConsulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagoConsulta.getId().intValue()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.cupones").value(DEFAULT_CUPONES));
    }

    
    @Transactional
    public void getNonExistingPagoConsulta() throws Exception {
        // Get the pagoConsulta
        restPagoConsultaMockMvc.perform(get("/api/pago-consultas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    
    @Transactional
    public void updatePagoConsulta() throws Exception {
        // Initialize the database
        pagoConsultaRepository.saveAndFlush(pagoConsulta);

        int databaseSizeBeforeUpdate = pagoConsultaRepository.findAll().size();

        // Update the pagoConsulta
        PagoConsulta updatedPagoConsulta = pagoConsultaRepository.findById(pagoConsulta.getId()).get();
        // Disconnect from session so that the updates on updatedPagoConsulta are not directly saved in db
        em.detach(updatedPagoConsulta);
        updatedPagoConsulta
            .monto(UPDATED_MONTO)
            .tipo(UPDATED_TIPO)
            .cupones(UPDATED_CUPONES);
        PagoConsultaDTO pagoConsultaDTO = pagoConsultaMapper.toDto(updatedPagoConsulta);


        // Validate the PagoConsulta in the database
        List<PagoConsulta> pagoConsultaList = pagoConsultaRepository.findAll();
        assertThat(pagoConsultaList).hasSize(databaseSizeBeforeUpdate);
        PagoConsulta testPagoConsulta = pagoConsultaList.get(pagoConsultaList.size() - 1);
        assertThat(testPagoConsulta.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testPagoConsulta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPagoConsulta.getCupones()).isEqualTo(UPDATED_CUPONES);
    }

    
    @Transactional
    public void updateNonExistingPagoConsulta() throws Exception {
        int databaseSizeBeforeUpdate = pagoConsultaRepository.findAll().size();

        // Create the PagoConsulta
        PagoConsultaDTO pagoConsultaDTO = pagoConsultaMapper.toDto(pagoConsulta);


        // Validate the PagoConsulta in the database
        List<PagoConsulta> pagoConsultaList = pagoConsultaRepository.findAll();
        assertThat(pagoConsultaList).hasSize(databaseSizeBeforeUpdate);
    }

    
    @Transactional
    public void deletePagoConsulta() throws Exception {
        // Initialize the database
        pagoConsultaRepository.saveAndFlush(pagoConsulta);

        int databaseSizeBeforeDelete = pagoConsultaRepository.findAll().size();


        // Validate the database contains one less item
        List<PagoConsulta> pagoConsultaList = pagoConsultaRepository.findAll();
        assertThat(pagoConsultaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    
    @Transactional
    public void equalsVerifier() throws Exception {
        PagoConsulta pagoConsulta1 = new PagoConsulta();
        pagoConsulta1.setId(1L);
        PagoConsulta pagoConsulta2 = new PagoConsulta();
        pagoConsulta2.setId(pagoConsulta1.getId());
        assertThat(pagoConsulta1).isEqualTo(pagoConsulta2);
        pagoConsulta2.setId(2L);
        assertThat(pagoConsulta1).isNotEqualTo(pagoConsulta2);
        pagoConsulta1.setId(null);
        assertThat(pagoConsulta1).isNotEqualTo(pagoConsulta2);
    }

    
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        PagoConsultaDTO pagoConsultaDTO1 = new PagoConsultaDTO();
        pagoConsultaDTO1.setId(1L);
        PagoConsultaDTO pagoConsultaDTO2 = new PagoConsultaDTO();
        assertThat(pagoConsultaDTO1).isNotEqualTo(pagoConsultaDTO2);
        pagoConsultaDTO2.setId(pagoConsultaDTO1.getId());
        assertThat(pagoConsultaDTO1).isEqualTo(pagoConsultaDTO2);
        pagoConsultaDTO2.setId(2L);
        assertThat(pagoConsultaDTO1).isNotEqualTo(pagoConsultaDTO2);
        pagoConsultaDTO1.setId(null);
        assertThat(pagoConsultaDTO1).isNotEqualTo(pagoConsultaDTO2);
    }

    
    @Transactional
    public void testEntityFromId() {
        assertThat(pagoConsultaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pagoConsultaMapper.fromId(null)).isNull();
    }
}
