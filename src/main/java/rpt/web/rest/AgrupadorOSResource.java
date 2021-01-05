package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.AgrupadorOSService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import rpt.service.dto.AgrupadorOSDTO;
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
 * REST controller for managing AgrupadorOS.
 */
@RestController
@RequestMapping("/api")
public class AgrupadorOSResource {

    private final Logger log = LoggerFactory.getLogger(AgrupadorOSResource.class);

    private static final String ENTITY_NAME = "agrupadorOS";

    private final AgrupadorOSService agrupadorOSService;

    public AgrupadorOSResource(AgrupadorOSService agrupadorOSService) {
        this.agrupadorOSService = agrupadorOSService;
    }

    /**
     * POST  /agrupador-os : Create a new agrupadorOS.
     *
     * @param agrupadorOSDTO the agrupadorOSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agrupadorOSDTO, or with status 400 (Bad Request) if the agrupadorOS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agrupador-os")
    @Timed
    public ResponseEntity<AgrupadorOSDTO> createAgrupadorOS(@Valid @RequestBody AgrupadorOSDTO agrupadorOSDTO) throws URISyntaxException {
        log.debug("REST request to save AgrupadorOS : {}", agrupadorOSDTO);
        if (agrupadorOSDTO.getId() != null) {
            throw new BadRequestAlertException("A new agrupadorOS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgrupadorOSDTO result = agrupadorOSService.save(agrupadorOSDTO);
        return ResponseEntity.created(new URI("/api/agrupador-os/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agrupador-os : Updates an existing agrupadorOS.
     *
     * @param agrupadorOSDTO the agrupadorOSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agrupadorOSDTO,
     * or with status 400 (Bad Request) if the agrupadorOSDTO is not valid,
     * or with status 500 (Internal Server Error) if the agrupadorOSDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agrupador-os")
    @Timed
    public ResponseEntity<AgrupadorOSDTO> updateAgrupadorOS(@Valid @RequestBody AgrupadorOSDTO agrupadorOSDTO) throws URISyntaxException {
        log.debug("REST request to update AgrupadorOS : {}", agrupadorOSDTO);
        if (agrupadorOSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgrupadorOSDTO result = agrupadorOSService.save(agrupadorOSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agrupadorOSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agrupador-os : get all the agrupadorOS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agrupadorOS in body
     */
    @GetMapping("/agrupador-os")
    @Timed
    public ResponseEntity<List<AgrupadorOSDTO>> getAllAgrupadorOS(Pageable pageable) {
        log.debug("REST request to get a page of AgrupadorOS");
        Page<AgrupadorOSDTO> page = agrupadorOSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agrupador-os");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agrupador-os/:id : get the "id" agrupadorOS.
     *
     * @param id the id of the agrupadorOSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agrupadorOSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agrupador-os/{id}")
    @Timed
    public ResponseEntity<AgrupadorOSDTO> getAgrupadorOS(@PathVariable Long id) {
        log.debug("REST request to get AgrupadorOS : {}", id);
        Optional<AgrupadorOSDTO> agrupadorOSDTO = agrupadorOSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agrupadorOSDTO);
    }

    /**
     * DELETE  /agrupador-os/:id : delete the "id" agrupadorOS.
     *
     * @param id the id of the agrupadorOSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agrupador-os/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgrupadorOS(@PathVariable Long id) {
        log.debug("REST request to delete AgrupadorOS : {}", id);
        agrupadorOSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
