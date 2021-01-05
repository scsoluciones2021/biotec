package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.FamiliarService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.service.dto.FamiliarDTO;
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
 * REST controller for managing Familiar.
 */
@RestController
@RequestMapping("/api")
public class FamiliarResource {

    private final Logger log = LoggerFactory.getLogger(FamiliarResource.class);

    private static final String ENTITY_NAME = "familiar";

    private final FamiliarService familiarService;

    public FamiliarResource(FamiliarService familiarService) {
        this.familiarService = familiarService;
    }

    /**
     * POST  /familiars : Create a new familiar.
     *
     * @param familiarDTO the familiarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familiarDTO, or with status 400 (Bad Request) if the familiar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/familiars")
    @Timed
    public ResponseEntity<FamiliarDTO> createFamiliar(@RequestBody FamiliarDTO familiarDTO) throws URISyntaxException {
        log.debug("REST request to save Familiar : {}", familiarDTO);
        if (familiarDTO.getId() != null) {
            throw new BadRequestAlertException("A new familiar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamiliarDTO result = familiarService.save(familiarDTO);
        return ResponseEntity.created(new URI("/api/familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /familiars : Updates an existing familiar.
     *
     * @param familiarDTO the familiarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familiarDTO,
     * or with status 400 (Bad Request) if the familiarDTO is not valid,
     * or with status 500 (Internal Server Error) if the familiarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/familiars")
    @Timed
    public ResponseEntity<FamiliarDTO> updateFamiliar(@RequestBody FamiliarDTO familiarDTO) throws URISyntaxException {
        log.debug("REST request to update Familiar : {}", familiarDTO);
        if (familiarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamiliarDTO result = familiarService.save(familiarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familiarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /familiars : get all the familiars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of familiars in body
     */
    @GetMapping("/familiars")
    @Timed
    public List<FamiliarDTO> getAllFamiliars() {
        log.debug("REST request to get all Familiars");
        return familiarService.findAll();
    }

    /**
     * GET  /familiars/:id : get the "id" familiar.
     *
     * @param id the id of the familiarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familiarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/familiars/{id}")
    @Timed
    public ResponseEntity<FamiliarDTO> getFamiliar(@PathVariable Long id) {
        log.debug("REST request to get Familiar : {}", id);
        Optional<FamiliarDTO> familiarDTO = familiarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familiarDTO);
    }

    /**
     * DELETE  /familiars/:id : delete the "id" familiar.
     *
     * @param id the id of the familiarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/familiars/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete Familiar : {}", id);
        familiarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
