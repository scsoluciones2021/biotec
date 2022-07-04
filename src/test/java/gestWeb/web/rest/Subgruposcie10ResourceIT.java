package gestWeb.web.rest;

import gestWeb.GestWebApp;
import gestWeb.domain.Subgruposcie10;
import gestWeb.repository.Subgruposcie10Repository;
import gestWeb.service.Subgruposcie10Service;

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
 * Integration tests for the {@link Subgruposcie10Resource} REST controller.
 */
@SpringBootTest(classes = GestWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Subgruposcie10ResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_GRUPO = 1;
    private static final Integer UPDATED_ID_GRUPO = 2;

    @Autowired
    private Subgruposcie10Repository subgruposcie10Repository;

    @Autowired
    private Subgruposcie10Service subgruposcie10Service;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubgruposcie10MockMvc;

    private Subgruposcie10 subgruposcie10;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgruposcie10 createEntity(EntityManager em) {
        Subgruposcie10 subgruposcie10 = new Subgruposcie10()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .idGrupo(DEFAULT_ID_GRUPO);
        return subgruposcie10;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgruposcie10 createUpdatedEntity(EntityManager em) {
        Subgruposcie10 subgruposcie10 = new Subgruposcie10()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idGrupo(UPDATED_ID_GRUPO);
        return subgruposcie10;
    }

    @Before
    public void initTest() {
        subgruposcie10 = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubgruposcie10() throws Exception {
        int databaseSizeBeforeCreate = subgruposcie10Repository.findAll().size();
        // Create the Subgruposcie10
        restSubgruposcie10MockMvc.perform(post("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isCreated());

        // Validate the Subgruposcie10 in the database
        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeCreate + 1);
        Subgruposcie10 testSubgruposcie10 = subgruposcie10List.get(subgruposcie10List.size() - 1);
        assertThat(testSubgruposcie10.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testSubgruposcie10.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSubgruposcie10.getIdGrupo()).isEqualTo(DEFAULT_ID_GRUPO);
    }

    @Test
    @Transactional
    public void createSubgruposcie10WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subgruposcie10Repository.findAll().size();

        // Create the Subgruposcie10 with an existing ID
        subgruposcie10.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubgruposcie10MockMvc.perform(post("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isBadRequest());

        // Validate the Subgruposcie10 in the database
        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = subgruposcie10Repository.findAll().size();
        // set the field null
        subgruposcie10.setClave(null);

        // Create the Subgruposcie10, which fails.


        restSubgruposcie10MockMvc.perform(post("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isBadRequest());

        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = subgruposcie10Repository.findAll().size();
        // set the field null
        subgruposcie10.setDescripcion(null);

        // Create the Subgruposcie10, which fails.


        restSubgruposcie10MockMvc.perform(post("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isBadRequest());

        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdGrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = subgruposcie10Repository.findAll().size();
        // set the field null
        subgruposcie10.setIdGrupo(null);

        // Create the Subgruposcie10, which fails.


        restSubgruposcie10MockMvc.perform(post("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isBadRequest());

        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubgruposcie10s() throws Exception {
        // Initialize the database
        subgruposcie10Repository.saveAndFlush(subgruposcie10);

        // Get all the subgruposcie10List
        restSubgruposcie10MockMvc.perform(get("/api/subgruposcie10?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subgruposcie10.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].idGrupo").value(hasItem(DEFAULT_ID_GRUPO)));
    }
    
    @Test
    @Transactional
    public void getSubgruposcie10() throws Exception {
        // Initialize the database
        subgruposcie10Repository.saveAndFlush(subgruposcie10);

        // Get the subgruposcie10
        restSubgruposcie10MockMvc.perform(get("/api/subgruposcie10/{id}", subgruposcie10.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subgruposcie10.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.idGrupo").value(DEFAULT_ID_GRUPO));
    }
    @Test
    @Transactional
    public void getNonExistingSubgruposcie10() throws Exception {
        // Get the subgruposcie10
        restSubgruposcie10MockMvc.perform(get("/api/subgruposcie10/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubgruposcie10() throws Exception {
        // Initialize the database
        subgruposcie10Repository.saveAndFlush(subgruposcie10);

        int databaseSizeBeforeUpdate = subgruposcie10Repository.findAll().size();

        // Update the subgruposcie10
        Subgruposcie10 updatedSubgruposcie10 = subgruposcie10Repository.findById(subgruposcie10.getId()).get();
        // Disconnect from session so that the updates on updatedSubgruposcie10 are not directly saved in db
        em.detach(updatedSubgruposcie10);
        updatedSubgruposcie10
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .idGrupo(UPDATED_ID_GRUPO);

        restSubgruposcie10MockMvc.perform(put("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubgruposcie10)))
            .andExpect(status().isOk());

        // Validate the Subgruposcie10 in the database
        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeUpdate);
        Subgruposcie10 testSubgruposcie10 = subgruposcie10List.get(subgruposcie10List.size() - 1);
        assertThat(testSubgruposcie10.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testSubgruposcie10.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSubgruposcie10.getIdGrupo()).isEqualTo(UPDATED_ID_GRUPO);
    }

    @Test
    @Transactional
    public void updateNonExistingSubgruposcie10() throws Exception {
        int databaseSizeBeforeUpdate = subgruposcie10Repository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubgruposcie10MockMvc.perform(put("/api/subgruposcie10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subgruposcie10)))
            .andExpect(status().isBadRequest());

        // Validate the Subgruposcie10 in the database
        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubgruposcie10() throws Exception {
        // Initialize the database
        subgruposcie10Repository.saveAndFlush(subgruposcie10);

        int databaseSizeBeforeDelete = subgruposcie10Repository.findAll().size();

        // Delete the subgruposcie10
        restSubgruposcie10MockMvc.perform(delete("/api/subgruposcie10/{id}", subgruposcie10.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subgruposcie10> subgruposcie10List = subgruposcie10Repository.findAll();
        assertThat(subgruposcie10List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
