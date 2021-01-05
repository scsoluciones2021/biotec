package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.ObsAntecPersonalService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import rpt.service.dto.ObsAntecPersonalDTO;
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
 * REST controller for managing ObsAntecPersonal.
 */
@RestController
@RequestMapping("/api")
public class ObsAntecPersonalResource {

    private final Logger log = LoggerFactory.getLogger(ObsAntecPersonalResource.class);

    private static final String ENTITY_NAME = "obsAntecPersonal";

    private final ObsAntecPersonalService obsAntecPersonalService;

    public ObsAntecPersonalResource(ObsAntecPersonalService obsAntecPersonalService) {
        this.obsAntecPersonalService = obsAntecPersonalService;
    }

    /**
     * POST  /obs-antec-personals : Create a new obsAntecPersonal.
     *
     * @param obsAntecPersonalDTO the obsAntecPersonalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obsAntecPersonalDTO, or with status 400 (Bad Request) if the obsAntecPersonal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obs-antec-personals")
    @Timed
    public ResponseEntity<ObsAntecPersonalDTO> createObsAntecPersonal(@Valid @RequestBody ObsAntecPersonalDTO obsAntecPersonalDTO) throws URISyntaxException {
        log.debug("REST request to save ObsAntecPersonal : {}", obsAntecPersonalDTO);
        if (obsAntecPersonalDTO.getId() != null) {
            throw new BadRequestAlertException("A new obsAntecPersonal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObsAntecPersonalDTO result = obsAntecPersonalService.save(obsAntecPersonalDTO);
        return ResponseEntity.created(new URI("/api/obs-antec-personals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obs-antec-personals : Updates an existing obsAntecPersonal.
     *
     * @param obsAntecPersonalDTO the obsAntecPersonalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obsAntecPersonalDTO,
     * or with status 400 (Bad Request) if the obsAntecPersonalDTO is not valid,
     * or with status 500 (Internal Server Error) if the obsAntecPersonalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obs-antec-personals")
    @Timed
    public ResponseEntity<ObsAntecPersonalDTO> updateObsAntecPersonal(@Valid @RequestBody ObsAntecPersonalDTO obsAntecPersonalDTO) throws URISyntaxException {
        log.debug("REST request to update ObsAntecPersonal : {}", obsAntecPersonalDTO);
        if (obsAntecPersonalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObsAntecPersonalDTO result = obsAntecPersonalService.save(obsAntecPersonalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obsAntecPersonalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /obs-antec-personals : get all the obsAntecPersonals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of obsAntecPersonals in body
     */
    @GetMapping("/obs-antec-personals")
    @Timed
    public ResponseEntity<List<ObsAntecPersonalDTO>> getAllObsAntecPersonals(Pageable pageable) {
        log.debug("REST request to get a page of ObsAntecPersonals");
        Page<ObsAntecPersonalDTO> page = obsAntecPersonalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obs-antec-personals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /obs-antec-personals/:id : get the "id" obsAntecPersonal.
     *
     * @param id the id of the obsAntecPersonalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obsAntecPersonalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/obs-antec-personals/{id}")
    @Timed
    public ResponseEntity<ObsAntecPersonalDTO> getObsAntecPersonal(@PathVariable Long id) {
        log.debug("REST request to get ObsAntecPersonal : {}", id);
        Optional<ObsAntecPersonalDTO> obsAntecPersonalDTO = obsAntecPersonalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obsAntecPersonalDTO);
    }

    /**
     * DELETE  /obs-antec-personals/:id : delete the "id" obsAntecPersonal.
     *
     * @param id the id of the obsAntecPersonalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/obs-antec-personals/{id}")
    @Timed
    public ResponseEntity<Void> deleteObsAntecPersonal(@PathVariable Long id) {
        log.debug("REST request to delete ObsAntecPersonal : {}", id);
        obsAntecPersonalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
