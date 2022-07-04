package gestWeb.web.rest;

import gestWeb.GestWebApp;
import gestWeb.service.dto.Categoriascie10DTO;
import gestWeb.service.mapper.Categoriascie10Mapper;
import gestWeb.domain.Categoriascie10;
import gestWeb.repository.Categoriascie10Repository;
import gestWeb.service.Categoriascie10Service;

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
 * Integration tests for the {@link Categoriascie10Resource} REST controller.
 */
@SpringBootTest(classes = GestWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Categoriascie10ResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDSUBGRUPO = 1;
    private static final Integer UPDATED_IDSUBGRUPO = 2;

    @Autowired
    private Categoriascie10Repository categoriascie10Repository;

    @Autowired
    private Categoriascie10Mapper categoriascie10Mapper;

    @Autowired
    private Categoriascie10Service categoriascie10Service;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriascie10MockMvc;

    private Categoriascie10 categoriascie10;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categoriascie10 createEntity(EntityManager em) {
        Categoriascie10 categoriascie10 = new Categoriascie10()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .idsubgrupo(DEFAULT_IDSUBGRUPO);
        return categoriascie10;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categoriascie10 createUpdatedEntity(EntityManager em) {
        Categoriascie10 categoriascie10 = new Categoriascie10()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idsubgrupo(UPDATED_IDSUBGRUPO);
        return categoriascie10;
    }

    @Before
    public void initTest() {
        categoriascie10 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriascie10() throws Exception {
        int databaseSizeBeforeCreate = categoriascie10Repository.findAll().size();
        // Create the Categoriascie10
        restCategoriascie10MockMvc.perform(post("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isCreated());

        // Validate the Categoriascie10 in the database
        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeCreate + 1);
        Categoriascie10 testCategoriascie10 = categoriascie10List.get(categoriascie10List.size() - 1);
        assertThat(testCategoriascie10.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testCategoriascie10.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCategoriascie10.getIdsubgrupo()).isEqualTo(DEFAULT_IDSUBGRUPO);
    }

    @Test
    @Transactional
    public void createCategoriascie10WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriascie10Repository.findAll().size();

        // Create the Categoriascie10 with an existing ID
        categoriascie10.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriascie10MockMvc.perform(post("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isBadRequest());

        // Validate the Categoriascie10 in the database
        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriascie10Repository.findAll().size();
        // set the field null
        categoriascie10.setClave(null);

        // Create the Categoriascie10, which fails.


        restCategoriascie10MockMvc.perform(post("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isBadRequest());

        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriascie10Repository.findAll().size();
        // set the field null
        categoriascie10.setDescripcion(null);

        // Create the Categoriascie10, which fails.


        restCategoriascie10MockMvc.perform(post("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isBadRequest());

        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdsubgrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriascie10Repository.findAll().size();
        // set the field null
        categoriascie10.setIdsubgrupo(null);

        // Create the Categoriascie10, which fails.


        restCategoriascie10MockMvc.perform(post("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isBadRequest());

        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriascie10s() throws Exception {
        // Initialize the database
        categoriascie10Repository.saveAndFlush(categoriascie10);

        // Get all the categoriascie10List
        restCategoriascie10MockMvc.perform(get("/api/categoriascie10?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriascie10.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].idsubgrupo").value(hasItem(DEFAULT_IDSUBGRUPO)));
    }
    
    @Test
    @Transactional
    public void getCategoriascie10() throws Exception {
        // Initialize the database
        categoriascie10Repository.saveAndFlush(categoriascie10);

        // Get the categoriascie10
        restCategoriascie10MockMvc.perform(get("/api/categoriascie10/{id}", categoriascie10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriascie10.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.idsubgrupo").value(DEFAULT_IDSUBGRUPO));
    }
    @Test
    @Transactional
    public void getNonExistingCategoriascie10() throws Exception {
        // Get the categoriascie10
        restCategoriascie10MockMvc.perform(get("/api/categoriascie10/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriascie10() throws Exception {
         // Initialize the database
        categoriascie10Repository.saveAndFlush(categoriascie10);

        int databaseSizeBeforeUpdate = categoriascie10Repository.findAll().size();

        // Update the categoriascie10
        Categoriascie10 updatedCategoriascie10 = categoriascie10Repository.findById(categoriascie10.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriascie10 are not directly saved in db
        em.detach(updatedCategoriascie10);
        updatedCategoriascie10
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idsubgrupo(UPDATED_IDSUBGRUPO);

        restCategoriascie10MockMvc.perform(put("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriascie10)))
            .andExpect(status().isOk());

        // Validate the Categoriascie10 in the database
        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeUpdate);
        Categoriascie10 testCategoriascie10 = categoriascie10List.get(categoriascie10List.size() - 1);
        assertThat(testCategoriascie10.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testCategoriascie10.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCategoriascie10.getIdsubgrupo()).isEqualTo(UPDATED_IDSUBGRUPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriascie10() throws Exception {
        int databaseSizeBeforeUpdate = categoriascie10Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriascie10MockMvc.perform(put("/api/categoriascie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriascie10)))
            .andExpect(status().isBadRequest());

        // Validate the Categoriascie10 in the database
        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriascie10() throws Exception {
        // Initialize the database
        categoriascie10Repository.saveAndFlush(categoriascie10);

        int databaseSizeBeforeDelete = categoriascie10Repository.findAll().size();

        // Delete the categoriascie10
        restCategoriascie10MockMvc.perform(delete("/api/categoriascie10/{id}", categoriascie10.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categoriascie10> categoriascie10List = categoriascie10Repository.findAll();
        assertThat(categoriascie10List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
