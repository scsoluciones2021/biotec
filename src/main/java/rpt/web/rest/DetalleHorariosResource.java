package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.DetalleHorariosService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import rpt.service.dto.DetalleHorariosDTO;
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
 * REST controller for managing DetalleHorarios.
 */
@RestController
@RequestMapping("/api")
public class DetalleHorariosResource {

    private final Logger log = LoggerFactory.getLogger(DetalleHorariosResource.class);

    private static final String ENTITY_NAME = "detalleHorarios";

    private final DetalleHorariosService detalleHorariosService;

    public DetalleHorariosResource(DetalleHorariosService detalleHorariosService) {
        this.detalleHorariosService = detalleHorariosService;
    }

    /**
     * POST  /detalle-horarios : Create a new detalleHorarios.
     *
     * @param detalleHorariosDTO the detalleHorariosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleHorariosDTO, or with status 400 (Bad Request) if the detalleHorarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-horarios")
    @Timed
    public ResponseEntity<DetalleHorariosDTO> createDetalleHorarios(@Valid @RequestBody DetalleHorariosDTO detalleHorariosDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleHorarios : {}", detalleHorariosDTO);
        if (detalleHorariosDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleHorarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleHorariosDTO result = detalleHorariosService.save(detalleHorariosDTO);
        return ResponseEntity.created(new URI("/api/detalle-horarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-horarios : Updates an existing detalleHorarios.
     *
     * @param detalleHorariosDTO the detalleHorariosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleHorariosDTO,
     * or with status 400 (Bad Request) if the detalleHorariosDTO is not valid,
     * or with status 500 (Internal Server Error) if the detalleHorariosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-horarios")
    @Timed
    public ResponseEntity<DetalleHorariosDTO> updateDetalleHorarios(@Valid @RequestBody DetalleHorariosDTO detalleHorariosDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleHorarios : {}", detalleHorariosDTO);
        if (detalleHorariosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalleHorariosDTO result = detalleHorariosService.save(detalleHorariosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleHorariosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-horarios : get all the detalleHorarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleHorarios in body
     */
    @GetMapping("/detalle-horarios")
    @Timed
    public ResponseEntity<List<DetalleHorariosDTO>> getAllDetalleHorarios(Pageable pageable) {
        log.debug("REST request to get a page of DetalleHorarios");
        Page<DetalleHorariosDTO> page = detalleHorariosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-horarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detalle-horarios/:id : get the "id" detalleHorarios.
     *
     * @param id the id of the detalleHorariosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleHorariosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-horarios/{id}")
    @Timed
    public ResponseEntity<DetalleHorariosDTO> getDetalleHorarios(@PathVariable Long id) {
        log.debug("REST request to get DetalleHorarios : {}", id);
        Optional<DetalleHorariosDTO> detalleHorariosDTO = detalleHorariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleHorariosDTO);
    }

    /**
     * DELETE  /detalle-horarios/:id : delete the "id" detalleHorarios.
     *
     * @param id the id of the detalleHorariosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-horarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetalleHorarios(@PathVariable Long id) {
        log.debug("REST request to delete DetalleHorarios : {}", id);
        detalleHorariosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/detalle-horarios/buscarXHorario")
    @Timed
    public ResponseEntity<List<DetalleHorariosDTO>> buscarXHorario(@RequestParam Long idHorario, Pageable pageable) {
        log.debug("REST request to get a page of DetalleHorarios x horario");
        Page<DetalleHorariosDTO> page = detalleHorariosService.buscarXHorario(idHorario, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-horarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
