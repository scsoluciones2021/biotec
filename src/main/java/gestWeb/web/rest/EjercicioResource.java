package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.EjercicioService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.EjercicioDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ejercicio.
 */
@RestController
@RequestMapping("/api")
public class EjercicioResource {

    private final Logger log = LoggerFactory.getLogger(EjercicioResource.class);

    private static final String ENTITY_NAME = "ejercicio";

    private final EjercicioService ejercicioService;

    public EjercicioResource(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    /**
     * POST  /ejercicios : Create a new ejercicio.
     *
     * @param ejercicioDTO the ejercicioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ejercicioDTO, or with status 400 (Bad Request) if the ejercicio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ejercicios")
    @Timed
    public ResponseEntity<EjercicioDTO> createEjercicio(@Valid @RequestBody EjercicioDTO ejercicioDTO) throws URISyntaxException {
        log.debug("REST request to save Ejercicio : {}", ejercicioDTO);
        if (ejercicioDTO.getId() != null) {
            throw new BadRequestAlertException("A new ejercicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EjercicioDTO result = ejercicioService.save(ejercicioDTO);
        return ResponseEntity.created(new URI("/api/ejercicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ejercicios : Updates an existing ejercicio.
     *
     * @param ejercicioDTO the ejercicioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ejercicioDTO,
     * or with status 400 (Bad Request) if the ejercicioDTO is not valid,
     * or with status 500 (Internal Server Error) if the ejercicioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ejercicios")
    @Timed
    public ResponseEntity<EjercicioDTO> updateEjercicio(@Valid @RequestBody EjercicioDTO ejercicioDTO) throws URISyntaxException {
        log.debug("REST request to update Ejercicio : {}", ejercicioDTO);
        if (ejercicioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EjercicioDTO result = ejercicioService.save(ejercicioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ejercicioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ejercicios : get all the ejercicios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ejercicios in body
     */
    @GetMapping("/ejercicios")
    @Timed
    public List<EjercicioDTO> getAllEjercicios() {
        log.debug("REST request to get all Ejercicios");
        return ejercicioService.findAll();
    }

    /**
     * GET  /ejercicios/:id : get the "id" ejercicio.
     *
     * @param id the id of the ejercicioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ejercicioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ejercicios/{id}")
    @Timed
    public ResponseEntity<EjercicioDTO> getEjercicio(@PathVariable Long id) {
        log.debug("REST request to get Ejercicio : {}", id);
        Optional<EjercicioDTO> ejercicioDTO = ejercicioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ejercicioDTO);
    }

    /**
     * DELETE  /ejercicios/:id : delete the "id" ejercicio.
     *
     * @param id the id of the ejercicioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ejercicios/{id}")
    @Timed
    public ResponseEntity<Void> deleteEjercicio(@PathVariable Long id) {
        log.debug("REST request to delete Ejercicio : {}", id);
        ejercicioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
