package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.ObsAntecFamiliarService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import rpt.service.dto.ObsAntecFamiliarDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ObsAntecFamiliar.
 */
@RestController
@RequestMapping("/api")
public class ObsAntecFamiliarResource {

    private final Logger log = LoggerFactory.getLogger(ObsAntecFamiliarResource.class);

    private static final String ENTITY_NAME = "obsAntecFamiliar";

    private final ObsAntecFamiliarService obsAntecFamiliarService;

    public ObsAntecFamiliarResource(ObsAntecFamiliarService obsAntecFamiliarService) {
        this.obsAntecFamiliarService = obsAntecFamiliarService;
    }

    /**
     * POST  /obs-antec-familiars : Create a new obsAntecFamiliar.
     *
     * @param obsAntecFamiliarDTO the obsAntecFamiliarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obsAntecFamiliarDTO, or with status 400 (Bad Request) if the obsAntecFamiliar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obs-antec-familiars")
    @Timed
    public ResponseEntity<ObsAntecFamiliarDTO> createObsAntecFamiliar(@Valid @RequestBody ObsAntecFamiliarDTO obsAntecFamiliarDTO) throws URISyntaxException {
        log.debug("REST request to save ObsAntecFamiliar : {}", obsAntecFamiliarDTO);
        if (obsAntecFamiliarDTO.getId() != null) {
            throw new BadRequestAlertException("A new obsAntecFamiliar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObsAntecFamiliarDTO result = obsAntecFamiliarService.save(obsAntecFamiliarDTO);
        return ResponseEntity.created(new URI("/api/obs-antec-familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obs-antec-familiars : Updates an existing obsAntecFamiliar.
     *
     * @param obsAntecFamiliarDTO the obsAntecFamiliarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obsAntecFamiliarDTO,
     * or with status 400 (Bad Request) if the obsAntecFamiliarDTO is not valid,
     * or with status 500 (Internal Server Error) if the obsAntecFamiliarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obs-antec-familiars")
    @Timed
    public ResponseEntity<ObsAntecFamiliarDTO> updateObsAntecFamiliar(@Valid @RequestBody ObsAntecFamiliarDTO obsAntecFamiliarDTO) throws URISyntaxException {
        log.debug("REST request to update ObsAntecFamiliar : {}", obsAntecFamiliarDTO);
        if (obsAntecFamiliarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObsAntecFamiliarDTO result = obsAntecFamiliarService.save(obsAntecFamiliarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obsAntecFamiliarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /obs-antec-familiars : get all the obsAntecFamiliars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of obsAntecFamiliars in body
     */
    @GetMapping("/obs-antec-familiars")
    @Timed
    public ResponseEntity<List<ObsAntecFamiliarDTO>> getAllObsAntecFamiliars(Pageable pageable) {
        log.debug("REST request to get a page of ObsAntecFamiliars");
        Page<ObsAntecFamiliarDTO> page = obsAntecFamiliarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obs-antec-familiars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /obs-antec-familiars/:id : get the "id" obsAntecFamiliar.
     *
     * @param id the id of the obsAntecFamiliarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obsAntecFamiliarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/obs-antec-familiars/{id}")
    @Timed
    public ResponseEntity<ObsAntecFamiliarDTO> getObsAntecFamiliar(@PathVariable Long id) {
        log.debug("REST request to get ObsAntecFamiliar : {}", id);
        Optional<ObsAntecFamiliarDTO> obsAntecFamiliarDTO = obsAntecFamiliarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obsAntecFamiliarDTO);
    }

    /**
     * DELETE  /obs-antec-familiars/:id : delete the "id" obsAntecFamiliar.
     *
     * @param id the id of the obsAntecFamiliarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/obs-antec-familiars/{id}")
    @Timed
    public ResponseEntity<Void> deleteObsAntecFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete ObsAntecFamiliar : {}", id);
        obsAntecFamiliarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
