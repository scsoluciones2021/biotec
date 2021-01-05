package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.AntecedentesPersonalesService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.service.dto.AntecedentesPersonalesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AntecedentesPersonales.
 */
@RestController
@RequestMapping("/api")
public class AntecedentesPersonalesResource {

    private final Logger log = LoggerFactory.getLogger(AntecedentesPersonalesResource.class);

    private static final String ENTITY_NAME = "antecedentesPersonales";

    private final AntecedentesPersonalesService antecedentesPersonalesService;

    public AntecedentesPersonalesResource(AntecedentesPersonalesService antecedentesPersonalesService) {
        this.antecedentesPersonalesService = antecedentesPersonalesService;
    }

    /**
     * POST  /antecedentes-personales : Create a new antecedentesPersonales.
     *
     * @param antecedentesPersonalesDTO the antecedentesPersonalesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new antecedentesPersonalesDTO, or with status 400 (Bad Request) if the antecedentesPersonales has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/antecedentes-personales")
    @Timed
    public ResponseEntity<AntecedentesPersonalesDTO> createAntecedentesPersonales(@RequestBody AntecedentesPersonalesDTO antecedentesPersonalesDTO) throws URISyntaxException {
        log.debug("REST request to save AntecedentesPersonales : {}", antecedentesPersonalesDTO);
        if (antecedentesPersonalesDTO.getId() != null) {
            throw new BadRequestAlertException("A new antecedentesPersonales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntecedentesPersonalesDTO result = antecedentesPersonalesService.save(antecedentesPersonalesDTO);
        return ResponseEntity.created(new URI("/api/antecedentes-personales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /antecedentes-personales : Updates an existing antecedentesPersonales.
     *
     * @param antecedentesPersonalesDTO the antecedentesPersonalesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated antecedentesPersonalesDTO,
     * or with status 400 (Bad Request) if the antecedentesPersonalesDTO is not valid,
     * or with status 500 (Internal Server Error) if the antecedentesPersonalesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/antecedentes-personales")
    @Timed
    public ResponseEntity<AntecedentesPersonalesDTO> updateAntecedentesPersonales(@RequestBody AntecedentesPersonalesDTO antecedentesPersonalesDTO) throws URISyntaxException {
        log.debug("REST request to update AntecedentesPersonales : {}", antecedentesPersonalesDTO);
        if (antecedentesPersonalesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AntecedentesPersonalesDTO result = antecedentesPersonalesService.save(antecedentesPersonalesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, antecedentesPersonalesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /antecedentes-personales : get all the antecedentesPersonales.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of antecedentesPersonales in body
     */
    @GetMapping("/antecedentes-personales")
    @Timed
    public List<AntecedentesPersonalesDTO> getAllAntecedentesPersonales(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all AntecedentesPersonales");
        return antecedentesPersonalesService.findAll();
    }

    /**
     * GET  /antecedentes-personales/:id : get the "id" antecedentesPersonales.
     *
     * @param id the id of the antecedentesPersonalesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the antecedentesPersonalesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/antecedentes-personales/{id}")
    @Timed
    public ResponseEntity<AntecedentesPersonalesDTO> getAntecedentesPersonales(@PathVariable Long id) {
        log.debug("REST request to get AntecedentesPersonales : {}", id);
        Optional<AntecedentesPersonalesDTO> antecedentesPersonalesDTO = antecedentesPersonalesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(antecedentesPersonalesDTO);
    }

    /**
     * DELETE  /antecedentes-personales/:id : delete the "id" antecedentesPersonales.
     *
     * @param id the id of the antecedentesPersonalesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/antecedentes-personales/{id}")
    @Timed
    public ResponseEntity<Void> deleteAntecedentesPersonales(@PathVariable Long id) {
        log.debug("REST request to delete AntecedentesPersonales : {}", id);
        antecedentesPersonalesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
