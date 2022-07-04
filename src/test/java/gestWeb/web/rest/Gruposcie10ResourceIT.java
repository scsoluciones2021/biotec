package gestWeb.web.rest;

import gestWeb.GestWebApp;
import gestWeb.domain.Gruposcie10;
import gestWeb.service.dto.Gruposcie10DTO;
import gestWeb.service.mapper.Gruposcie10Mapper;
import gestWeb.repository.Gruposcie10Repository;
import gestWeb.service.Gruposcie10Service;

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
 * Integration tests for the {@link Gruposcie10Resource} REST controller.
 */
@SpringBootTest(classes = GestWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Gruposcie10ResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private Gruposcie10Repository gruposcie10Repository;

    @Autowired
    private Gruposcie10Mapper gruposcie10Mapper;

    @Autowired
    private Gruposcie10Service gruposcie10Service;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGruposcie10MockMvc;

    private Gruposcie10 gruposcie10;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gruposcie10 createEntity(EntityManager em) {
        Gruposcie10 gruposcie10 = new Gruposcie10()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION);
        return gruposcie10;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gruposcie10 createUpdatedEntity(EntityManager em) {
        Gruposcie10 gruposcie10 = new Gruposcie10()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);
        return gruposcie10;
    }

    @Before
    public void initTest() {
        gruposcie10 = createEntity(em);
    }

    @Test
    @Transactional
    public void createGruposcie10() throws Exception {
        int databaseSizeBeforeCreate = gruposcie10Repository.findAll().size();
        // Create the Gruposcie10
        restGruposcie10MockMvc.perform(post("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gruposcie10)))
            .andExpect(status().isCreated());

        // Validate the Gruposcie10 in the database
        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeCreate + 1);
        Gruposcie10 testGruposcie10 = gruposcie10List.get(gruposcie10List.size() - 1);
        assertThat(testGruposcie10.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testGruposcie10.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createGruposcie10WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gruposcie10Repository.findAll().size();

        // Create the Gruposcie10 with an existing ID
        gruposcie10.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruposcie10MockMvc.perform(post("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gruposcie10)))
            .andExpect(status().isBadRequest());

        // Validate the Gruposcie10 in the database
        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruposcie10Repository.findAll().size();
        // set the field null
        gruposcie10.setClave(null);

        // Create the Gruposcie10, which fails.


        restGruposcie10MockMvc.perform(post("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gruposcie10)))
            .andExpect(status().isBadRequest());

        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruposcie10Repository.findAll().size();
        // set the field null
        gruposcie10.setDescripcion(null);

        // Create the Gruposcie10, which fails.


        restGruposcie10MockMvc.perform(post("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gruposcie10)))
            .andExpect(status().isBadRequest());

        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGruposcie10s() throws Exception {
        // Initialize the database
        gruposcie10Repository.saveAndFlush(gruposcie10);

        // Get all the gruposcie10List
        restGruposcie10MockMvc.perform(get("/api/gruposcie10?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruposcie10.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getGruposcie10() throws Exception {
        // Initialize the database
        gruposcie10Repository.saveAndFlush(gruposcie10);

        // Get the gruposcie10
        restGruposcie10MockMvc.perform(get("/api/gruposcie10/{id}", gruposcie10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gruposcie10.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }
    @Test
    @Transactional
    public void getNonExistingGruposcie10() throws Exception {
        // Get the gruposcie10
        restGruposcie10MockMvc.perform(get("/api/gruposcie10/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGruposcie10() throws Exception {
        // Initialize the database
        gruposcie10Repository.saveAndFlush(gruposcie10);

        int databaseSizeBeforeUpdate = gruposcie10Repository.findAll().size();

        // Update the gruposcie10
        Gruposcie10 updatedGruposcie10 = gruposcie10Repository.findById(gruposcie10.getId()).get();
        // Disconnect from session so that the updates on updatedGruposcie10 are not directly saved in db
        em.detach(updatedGruposcie10);
        updatedGruposcie10
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION);

        restGruposcie10MockMvc.perform(put("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGruposcie10)))
            .andExpect(status().isOk());

        // Validate the Gruposcie10 in the database
        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeUpdate);
        Gruposcie10 testGruposcie10 = gruposcie10List.get(gruposcie10List.size() - 1);
        assertThat(testGruposcie10.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testGruposcie10.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingGruposcie10() throws Exception {
        int databaseSizeBeforeUpdate = gruposcie10Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruposcie10MockMvc.perform(put("/api/gruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gruposcie10)))
            .andExpect(status().isBadRequest());

        // Validate the Gruposcie10 in the database
        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGruposcie10() throws Exception {
        // Initialize the database
        gruposcie10Repository.saveAndFlush(gruposcie10);

        int databaseSizeBeforeDelete = gruposcie10Repository.findAll().size();

        // Delete the gruposcie10
        restGruposcie10MockMvc.perform(delete("/api/gruposcie10/{id}", gruposcie10.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gruposcie10> gruposcie10List = gruposcie10Repository.findAll();
        assertThat(gruposcie10List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
