package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.BloqueosService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import rpt.service.dto.BloqueosDTO;
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
 * REST controller for managing Bloqueos.
 */
@RestController
@RequestMapping("/api")
public class BloqueosResource {

    private final Logger log = LoggerFactory.getLogger(BloqueosResource.class);

    private static final String ENTITY_NAME = "bloqueos";

    private final BloqueosService bloqueosService;

    public BloqueosResource(BloqueosService bloqueosService) {
        this.bloqueosService = bloqueosService;
    }

    /**
     * POST  /bloqueos : Create a new bloqueos.
     *
     * @param bloqueosDTO the bloqueosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bloqueosDTO, or with status 400 (Bad Request) if the bloqueos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bloqueos")
    @Timed
    public ResponseEntity<BloqueosDTO> createBloqueos(@Valid @RequestBody BloqueosDTO bloqueosDTO) throws URISyntaxException {
        log.debug("REST request to save Bloqueos : {}", bloqueosDTO);
        if (bloqueosDTO.getId() != null) {
            throw new BadRequestAlertException("A new bloqueos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloqueosDTO result = bloqueosService.save(bloqueosDTO);
        return ResponseEntity.created(new URI("/api/bloqueos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bloqueos : Updates an existing bloqueos.
     *
     * @param bloqueosDTO the bloqueosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bloqueosDTO,
     * or with status 400 (Bad Request) if the bloqueosDTO is not valid,
     * or with status 500 (Internal Server Error) if the bloqueosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bloqueos")
    @Timed
    public ResponseEntity<BloqueosDTO> updateBloqueos(@Valid @RequestBody BloqueosDTO bloqueosDTO) throws URISyntaxException {
        log.debug("REST request to update Bloqueos : {}", bloqueosDTO);
        if (bloqueosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BloqueosDTO result = bloqueosService.save(bloqueosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bloqueosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bloqueos : get all the bloqueos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bloqueos in body
     */
    @GetMapping("/bloqueos")
    @Timed
    public ResponseEntity<List<BloqueosDTO>> getAllBloqueos(Pageable pageable) {
        log.debug("REST request to get a page of Bloqueos");
        Page<BloqueosDTO> page = bloqueosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bloqueos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bloqueos/:id : get the "id" bloqueos.
     *
     * @param id the id of the bloqueosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bloqueosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bloqueos/{id}")
    @Timed
    public ResponseEntity<BloqueosDTO> getBloqueos(@PathVariable Long id) {
        log.debug("REST request to get Bloqueos : {}", id);
        Optional<BloqueosDTO> bloqueosDTO = bloqueosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloqueosDTO);
    }

    /**
     * DELETE  /bloqueos/:id : delete the "id" bloqueos.
     *
     * @param id the id of the bloqueosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bloqueos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBloqueos(@PathVariable Long id) {
        log.debug("REST request to delete Bloqueos : {}", id);
        bloqueosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
