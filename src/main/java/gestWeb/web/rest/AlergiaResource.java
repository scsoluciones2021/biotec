package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.AlergiaService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.AlergiaDTO;
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
 * REST controller for managing Alergia.
 */
@RestController
@RequestMapping("/api")
public class AlergiaResource {

    private final Logger log = LoggerFactory.getLogger(AlergiaResource.class);

    private static final String ENTITY_NAME = "alergia";

    private final AlergiaService alergiaService;

    public AlergiaResource(AlergiaService alergiaService) {
        this.alergiaService = alergiaService;
    }

    /**
     * POST  /alergias : Create a new alergia.
     *
     * @param alergiaDTO the alergiaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alergiaDTO, or with status 400 (Bad Request) if the alergia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alergias")
    @Timed
    public ResponseEntity<AlergiaDTO> createAlergia(@Valid @RequestBody AlergiaDTO alergiaDTO) throws URISyntaxException {
        log.debug("REST request to save Alergia : {}", alergiaDTO);
        if (alergiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new alergia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlergiaDTO result = alergiaService.save(alergiaDTO);
        return ResponseEntity.created(new URI("/api/alergias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alergias : Updates an existing alergia.
     *
     * @param alergiaDTO the alergiaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alergiaDTO,
     * or with status 400 (Bad Request) if the alergiaDTO is not valid,
     * or with status 500 (Internal Server Error) if the alergiaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alergias")
    @Timed
    public ResponseEntity<AlergiaDTO> updateAlergia(@Valid @RequestBody AlergiaDTO alergiaDTO) throws URISyntaxException {
        log.debug("REST request to update Alergia : {}", alergiaDTO);
        if (alergiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlergiaDTO result = alergiaService.save(alergiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alergiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alergias : get all the alergias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alergias in body
     */
    @GetMapping("/alergias")
    @Timed
    public List<AlergiaDTO> getAllAlergias() {
        log.debug("REST request to get all Alergias");
        return alergiaService.findAll();
    }

    /**
     * GET  /alergias/:id : get the "id" alergia.
     *
     * @param id the id of the alergiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alergiaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alergias/{id}")
    @Timed
    public ResponseEntity<AlergiaDTO> getAlergia(@PathVariable Long id) {
        log.debug("REST request to get Alergia : {}", id);
        Optional<AlergiaDTO> alergiaDTO = alergiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alergiaDTO);
    }

    /**
     * DELETE  /alergias/:id : delete the "id" alergia.
     *
     * @param id the id of the alergiaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alergias/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlergia(@PathVariable Long id) {
        log.debug("REST request to delete Alergia : {}", id);
        alergiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
