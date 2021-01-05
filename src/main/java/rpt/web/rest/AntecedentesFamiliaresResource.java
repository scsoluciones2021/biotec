package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.AntecedentesFamiliaresService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.service.dto.AntecedentesFamiliaresDTO;
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
 * REST controller for managing AntecedentesFamiliares.
 */
@RestController
@RequestMapping("/api")
public class AntecedentesFamiliaresResource {

    private final Logger log = LoggerFactory.getLogger(AntecedentesFamiliaresResource.class);

    private static final String ENTITY_NAME = "antecedentesFamiliares";

    private final AntecedentesFamiliaresService antecedentesFamiliaresService;

    public AntecedentesFamiliaresResource(AntecedentesFamiliaresService antecedentesFamiliaresService) {
        this.antecedentesFamiliaresService = antecedentesFamiliaresService;
    }

    /**
     * POST  /antecedentes-familiares : Create a new antecedentesFamiliares.
     *
     * @param antecedentesFamiliaresDTO the antecedentesFamiliaresDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new antecedentesFamiliaresDTO, or with status 400 (Bad Request) if the antecedentesFamiliares has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/antecedentes-familiares")
    @Timed
    public ResponseEntity<AntecedentesFamiliaresDTO> createAntecedentesFamiliares(@RequestBody AntecedentesFamiliaresDTO antecedentesFamiliaresDTO) throws URISyntaxException {
        log.debug("REST request to save AntecedentesFamiliares : {}", antecedentesFamiliaresDTO);
        if (antecedentesFamiliaresDTO.getId() != null) {
            throw new BadRequestAlertException("A new antecedentesFamiliares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntecedentesFamiliaresDTO result = antecedentesFamiliaresService.save(antecedentesFamiliaresDTO);
        return ResponseEntity.created(new URI("/api/antecedentes-familiares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /antecedentes-familiares : Updates an existing antecedentesFamiliares.
     *
     * @param antecedentesFamiliaresDTO the antecedentesFamiliaresDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated antecedentesFamiliaresDTO,
     * or with status 400 (Bad Request) if the antecedentesFamiliaresDTO is not valid,
     * or with status 500 (Internal Server Error) if the antecedentesFamiliaresDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/antecedentes-familiares")
    @Timed
    public ResponseEntity<AntecedentesFamiliaresDTO> updateAntecedentesFamiliares(@RequestBody AntecedentesFamiliaresDTO antecedentesFamiliaresDTO) throws URISyntaxException {
        log.debug("REST request to update AntecedentesFamiliares : {}", antecedentesFamiliaresDTO);
        if (antecedentesFamiliaresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AntecedentesFamiliaresDTO result = antecedentesFamiliaresService.save(antecedentesFamiliaresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, antecedentesFamiliaresDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /antecedentes-familiares : get all the antecedentesFamiliares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of antecedentesFamiliares in body
     */
    @GetMapping("/antecedentes-familiares")
    @Timed
    public List<AntecedentesFamiliaresDTO> getAllAntecedentesFamiliares() {
        log.debug("REST request to get all AntecedentesFamiliares");
        return antecedentesFamiliaresService.findAll();
    }

    /**
     * GET  /antecedentes-familiares/:id : get the "id" antecedentesFamiliares.
     *
     * @param id the id of the antecedentesFamiliaresDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the antecedentesFamiliaresDTO, or with status 404 (Not Found)
     */
    @GetMapping("/antecedentes-familiares/{id}")
    @Timed
    public ResponseEntity<AntecedentesFamiliaresDTO> getAntecedentesFamiliares(@PathVariable Long id) {
        log.debug("REST request to get AntecedentesFamiliares : {}", id);
        Optional<AntecedentesFamiliaresDTO> antecedentesFamiliaresDTO = antecedentesFamiliaresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(antecedentesFamiliaresDTO);
    }

    /**
     * DELETE  /antecedentes-familiares/:id : delete the "id" antecedentesFamiliares.
     *
     * @param id the id of the antecedentesFamiliaresDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/antecedentes-familiares/{id}")
    @Timed
    public ResponseEntity<Void> deleteAntecedentesFamiliares(@PathVariable Long id) {
        log.debug("REST request to delete AntecedentesFamiliares : {}", id);
        antecedentesFamiliaresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
