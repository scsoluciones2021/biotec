package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.EstadosService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.EstadosDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Estados.
 */
@RestController
@RequestMapping("/api")
public class EstadosResource {

    private final Logger log = LoggerFactory.getLogger(EstadosResource.class);

    private static final String ENTITY_NAME = "estados";

    private final EstadosService estadosService;

    public EstadosResource(EstadosService estadosService) {
        this.estadosService = estadosService;
    }

    /**
     * POST  /estados : Create a new estados.
     *
     * @param estadosDTO the estadosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estadosDTO, or with status 400 (Bad Request) if the estados has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estados")
    @Timed
    public ResponseEntity<EstadosDTO> createEstados(@RequestBody EstadosDTO estadosDTO) throws URISyntaxException {
        log.debug("REST request to save Estados : {}", estadosDTO);
        if (estadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new estados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadosDTO result = estadosService.save(estadosDTO);
        return ResponseEntity.created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estados : Updates an existing estados.
     *
     * @param estadosDTO the estadosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estadosDTO,
     * or with status 400 (Bad Request) if the estadosDTO is not valid,
     * or with status 500 (Internal Server Error) if the estadosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estados")
    @Timed
    public ResponseEntity<EstadosDTO> updateEstados(@RequestBody EstadosDTO estadosDTO) throws URISyntaxException {
        log.debug("REST request to update Estados : {}", estadosDTO);
        if (estadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadosDTO result = estadosService.save(estadosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estadosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estados : get all the estados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estados in body
     */
    @GetMapping("/estados")
    @Timed
    public ResponseEntity<List<EstadosDTO>> getAllEstados(Pageable pageable) {
        log.debug("REST request to get a page of Estados");
        Page<EstadosDTO> page = estadosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estados/:id : get the "id" estados.
     *
     * @param id the id of the estadosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estadosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estados/{id}")
    @Timed
    public ResponseEntity<EstadosDTO> getEstados(@PathVariable Long id) {
        log.debug("REST request to get Estados : {}", id);
        Optional<EstadosDTO> estadosDTO = estadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadosDTO);
    }

    /**
     * DELETE  /estados/:id : delete the "id" estados.
     *
     * @param id the id of the estadosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstados(@PathVariable Long id) {
        log.debug("REST request to delete Estados : {}", id);
        estadosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
