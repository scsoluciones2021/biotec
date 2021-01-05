package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.BebidaService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.service.dto.BebidaDTO;
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
 * REST controller for managing Bebida.
 */
@RestController
@RequestMapping("/api")
public class BebidaResource {

    private final Logger log = LoggerFactory.getLogger(BebidaResource.class);

    private static final String ENTITY_NAME = "bebida";

    private final BebidaService bebidaService;

    public BebidaResource(BebidaService bebidaService) {
        this.bebidaService = bebidaService;
    }

    /**
     * POST  /bebidas : Create a new bebida.
     *
     * @param bebidaDTO the bebidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bebidaDTO, or with status 400 (Bad Request) if the bebida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bebidas")
    @Timed
    public ResponseEntity<BebidaDTO> createBebida(@Valid @RequestBody BebidaDTO bebidaDTO) throws URISyntaxException {
        log.debug("REST request to save Bebida : {}", bebidaDTO);
        if (bebidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new bebida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BebidaDTO result = bebidaService.save(bebidaDTO);
        return ResponseEntity.created(new URI("/api/bebidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bebidas : Updates an existing bebida.
     *
     * @param bebidaDTO the bebidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bebidaDTO,
     * or with status 400 (Bad Request) if the bebidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the bebidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bebidas")
    @Timed
    public ResponseEntity<BebidaDTO> updateBebida(@Valid @RequestBody BebidaDTO bebidaDTO) throws URISyntaxException {
        log.debug("REST request to update Bebida : {}", bebidaDTO);
        if (bebidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BebidaDTO result = bebidaService.save(bebidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bebidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bebidas : get all the bebidas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bebidas in body
     */
    @GetMapping("/bebidas")
    @Timed
    public List<BebidaDTO> getAllBebidas() {
        log.debug("REST request to get all Bebidas");
        return bebidaService.findAll();
    }

    /**
     * GET  /bebidas/:id : get the "id" bebida.
     *
     * @param id the id of the bebidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bebidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bebidas/{id}")
    @Timed
    public ResponseEntity<BebidaDTO> getBebida(@PathVariable Long id) {
        log.debug("REST request to get Bebida : {}", id);
        Optional<BebidaDTO> bebidaDTO = bebidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bebidaDTO);
    }

    /**
     * DELETE  /bebidas/:id : delete the "id" bebida.
     *
     * @param id the id of the bebidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bebidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBebida(@PathVariable Long id) {
        log.debug("REST request to delete Bebida : {}", id);
        bebidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
