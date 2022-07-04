package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.EnfermedadService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.EnfermedadDTO;
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
 * REST controller for managing Enfermedad.
 */
@RestController
@RequestMapping("/api")
public class EnfermedadResource {

    private final Logger log = LoggerFactory.getLogger(EnfermedadResource.class);

    private static final String ENTITY_NAME = "enfermedad";

    private final EnfermedadService enfermedadService;

    public EnfermedadResource(EnfermedadService enfermedadService) {
        this.enfermedadService = enfermedadService;
    }

    /**
     * POST  /enfermedads : Create a new enfermedad.
     *
     * @param enfermedadDTO the enfermedadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enfermedadDTO, or with status 400 (Bad Request) if the enfermedad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enfermedads")
    @Timed
    public ResponseEntity<EnfermedadDTO> createEnfermedad(@Valid @RequestBody EnfermedadDTO enfermedadDTO) throws URISyntaxException {
        log.debug("REST request to save Enfermedad : {}", enfermedadDTO);
        if (enfermedadDTO.getId() != null) {
            throw new BadRequestAlertException("A new enfermedad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnfermedadDTO result = enfermedadService.save(enfermedadDTO);
        return ResponseEntity.created(new URI("/api/enfermedads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enfermedads : Updates an existing enfermedad.
     *
     * @param enfermedadDTO the enfermedadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enfermedadDTO,
     * or with status 400 (Bad Request) if the enfermedadDTO is not valid,
     * or with status 500 (Internal Server Error) if the enfermedadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enfermedads")
    @Timed
    public ResponseEntity<EnfermedadDTO> updateEnfermedad(@Valid @RequestBody EnfermedadDTO enfermedadDTO) throws URISyntaxException {
        log.debug("REST request to update Enfermedad : {}", enfermedadDTO);
        if (enfermedadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnfermedadDTO result = enfermedadService.save(enfermedadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enfermedadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enfermedads : get all the enfermedads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enfermedads in body
     */
    @GetMapping("/enfermedads")
    @Timed
    public List<EnfermedadDTO> getAllEnfermedads() {
        log.debug("REST request to get all Enfermedads");
        return enfermedadService.findAll();
    }

    /**
     * GET  /enfermedads/:id : get the "id" enfermedad.
     *
     * @param id the id of the enfermedadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enfermedadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enfermedads/{id}")
    @Timed
    public ResponseEntity<EnfermedadDTO> getEnfermedad(@PathVariable Long id) {
        log.debug("REST request to get Enfermedad : {}", id);
        Optional<EnfermedadDTO> enfermedadDTO = enfermedadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enfermedadDTO);
    }

    /**
     * DELETE  /enfermedads/:id : delete the "id" enfermedad.
     *
     * @param id the id of the enfermedadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enfermedads/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnfermedad(@PathVariable Long id) {
        log.debug("REST request to delete Enfermedad : {}", id);
        enfermedadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
