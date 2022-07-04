package gestWeb.web.rest;

import gestWeb.GestWebApp;
import gestWeb.domain.Diagnosticoscie10;
import gestWeb.service.mapper.Diagnosticoscie10Mapper;
import gestWeb.repository.Diagnosticoscie10Repository;
import gestWeb.service.Diagnosticoscie10Service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Diagnosticoscie10Resource} REST controller.
 */
@SpringBootTest(classes = GestWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Diagnosticoscie10ResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDCATEGORIA = 1;
    private static final Integer UPDATED_IDCATEGORIA = 2;

    @Autowired
    private Diagnosticoscie10Repository diagnosticoscie10Repository;

    @Autowired
    private Diagnosticoscie10Mapper diagnosticoscie10Mapper;

    @Autowired
    private Diagnosticoscie10Service diagnosticoscie10Service;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosticoscie10MockMvc;

    private Diagnosticoscie10 diagnosticoscie10;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnosticoscie10 createEntity(EntityManager em) {
        Diagnosticoscie10 diagnosticoscie10 = new Diagnosticoscie10()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .idcategoria(DEFAULT_IDCATEGORIA);
        return diagnosticoscie10;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnosticoscie10 createUpdatedEntity(EntityManager em) {
        Diagnosticoscie10 diagnosticoscie10 = new Diagnosticoscie10()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idcategoria(UPDATED_IDCATEGORIA);
        return diagnosticoscie10;
    }

    @Before
    public void initTest() {
        diagnosticoscie10 = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnosticoscie10() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoscie10Repository.findAll().size();
        // Create the Diagnosticoscie10
        restDiagnosticoscie10MockMvc.perform(post("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isCreated());

        // Validate the Diagnosticoscie10 in the database
        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeCreate + 1);
        Diagnosticoscie10 testDiagnosticoscie10 = diagnosticoscie10List.get(diagnosticoscie10List.size() - 1);
        assertThat(testDiagnosticoscie10.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testDiagnosticoscie10.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testDiagnosticoscie10.getIdcategoria()).isEqualTo(DEFAULT_IDCATEGORIA);
    }

    @Test
    @Transactional
    public void createDiagnosticoscie10WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoscie10Repository.findAll().size();

        // Create the Diagnosticoscie10 with an existing ID
        diagnosticoscie10.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticoscie10MockMvc.perform(post("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnosticoscie10 in the database
        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoscie10Repository.findAll().size();
        // set the field null
        diagnosticoscie10.setClave(null);

        // Create the Diagnosticoscie10, which fails.


        restDiagnosticoscie10MockMvc.perform(post("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isBadRequest());

        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoscie10Repository.findAll().size();
        // set the field null
        diagnosticoscie10.setDescripcion(null);

        // Create the Diagnosticoscie10, which fails.


        restDiagnosticoscie10MockMvc.perform(post("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isBadRequest());

        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdcategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoscie10Repository.findAll().size();
        // set the field null
        diagnosticoscie10.setIdcategoria(null);

        // Create the Diagnosticoscie10, which fails.


        restDiagnosticoscie10MockMvc.perform(post("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isBadRequest());

        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiagnosticoscie10s() throws Exception {
        // Initialize the database
        diagnosticoscie10Repository.saveAndFlush(diagnosticoscie10);

        // Get all the diagnosticoscie10List
        restDiagnosticoscie10MockMvc.perform(get("/api/diagnosticoscie10?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosticoscie10.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].idcategoria").value(hasItem(DEFAULT_IDCATEGORIA)));
    }
    
    @Test
    @Transactional
    public void getDiagnosticoscie10() throws Exception {
        // Initialize the database
        diagnosticoscie10Repository.saveAndFlush(diagnosticoscie10);

        // Get the diagnosticoscie10
        restDiagnosticoscie10MockMvc.perform(get("/api/diagnosticoscie10/{id}", diagnosticoscie10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosticoscie10.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.idcategoria").value(DEFAULT_IDCATEGORIA));
    }
    @Test
    @Transactional
    public void getNonExistingDiagnosticoscie10() throws Exception {
        // Get the diagnosticoscie10
        restDiagnosticoscie10MockMvc.perform(get("/api/diagnosticoscie10/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnosticoscie10() throws Exception {
        // Initialize the database
        diagnosticoscie10Repository.saveAndFlush(diagnosticoscie10);

        int databaseSizeBeforeUpdate = diagnosticoscie10Repository.findAll().size();

        // Update the diagnosticoscie10
        Diagnosticoscie10 updatedDiagnosticoscie10 = diagnosticoscie10Repository.findById(diagnosticoscie10.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnosticoscie10 are not directly saved in db
        em.detach(updatedDiagnosticoscie10);
        updatedDiagnosticoscie10
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idcategoria(UPDATED_IDCATEGORIA);

        restDiagnosticoscie10MockMvc.perform(put("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiagnosticoscie10)))
            .andExpect(status().isOk());

        // Validate the Diagnosticoscie10 in the database
        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeUpdate);
        Diagnosticoscie10 testDiagnosticoscie10 = diagnosticoscie10List.get(diagnosticoscie10List.size() - 1);
        assertThat(testDiagnosticoscie10.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testDiagnosticoscie10.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testDiagnosticoscie10.getIdcategoria()).isEqualTo(UPDATED_IDCATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnosticoscie10() throws Exception {
        int databaseSizeBeforeUpdate = diagnosticoscie10Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticoscie10MockMvc.perform(put("/api/diagnosticoscie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticoscie10)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnosticoscie10 in the database
        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiagnosticoscie10() throws Exception {
        // Initialize the database
        diagnosticoscie10Repository.saveAndFlush(diagnosticoscie10);

        int databaseSizeBeforeDelete = diagnosticoscie10Repository.findAll().size();

        // Delete the diagnosticoscie10
        restDiagnosticoscie10MockMvc.perform(delete("/api/diagnosticoscie10/{id}", diagnosticoscie10.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diagnosticoscie10> diagnosticoscie10List = diagnosticoscie10Repository.findAll();
        assertThat(diagnosticoscie10List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
