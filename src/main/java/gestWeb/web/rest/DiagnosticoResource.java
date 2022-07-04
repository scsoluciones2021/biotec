package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.DiagnosticoService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.DiagnosticoDTO;
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
 * REST controller for managing Diagnostico.
 */
@RestController
@RequestMapping("/api")
public class DiagnosticoResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosticoResource.class);

    private static final String ENTITY_NAME = "diagnostico";

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoResource(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    /**
     * POST  /diagnosticos : Create a new diagnostico.
     *
     * @param diagnosticoDTO the diagnosticoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diagnosticoDTO, or with status 400 (Bad Request) if the diagnostico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diagnosticos")
    @Timed
    public ResponseEntity<DiagnosticoDTO> createDiagnostico(@RequestBody DiagnosticoDTO diagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to save Diagnostico : {}", diagnosticoDTO);
        if (diagnosticoDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnostico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnosticoDTO result = diagnosticoService.save(diagnosticoDTO);
        return ResponseEntity.created(new URI("/api/diagnosticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnosticos : Updates an existing diagnostico.
     *
     * @param diagnosticoDTO the diagnosticoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diagnosticoDTO,
     * or with status 400 (Bad Request) if the diagnosticoDTO is not valid,
     * or with status 500 (Internal Server Error) if the diagnosticoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnosticos")
    @Timed
    public ResponseEntity<DiagnosticoDTO> updateDiagnostico(@RequestBody DiagnosticoDTO diagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to update Diagnostico : {}", diagnosticoDTO);
        if (diagnosticoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiagnosticoDTO result = diagnosticoService.save(diagnosticoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnosticoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diagnosticos : get all the diagnosticos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of diagnosticos in body
     */
    @GetMapping("/diagnosticos")
    @Timed
    public List<DiagnosticoDTO> getAllDiagnosticos() {
        log.debug("REST request to get all Diagnosticos");
        return diagnosticoService.findAll();
    }

    /**
     * GET  /diagnosticos/:id : get the "id" diagnostico.
     *
     * @param id the id of the diagnosticoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diagnosticoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diagnosticos/{id}")
    @Timed
    public ResponseEntity<DiagnosticoDTO> getDiagnostico(@PathVariable Long id) {
        log.debug("REST request to get Diagnostico : {}", id);
        Optional<DiagnosticoDTO> diagnosticoDTO = diagnosticoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosticoDTO);
    }

    /**
     * DELETE  /diagnosticos/:id : delete the "id" diagnostico.
     *
     * @param id the id of the diagnosticoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diagnosticos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiagnostico(@PathVariable Long id) {
        log.debug("REST request to delete Diagnostico : {}", id);
        diagnosticoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
