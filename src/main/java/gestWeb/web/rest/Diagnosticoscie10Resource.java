package gestWeb.web.rest;

import gestWeb.service.dto.Diagnosticoscie10DTO;
import gestWeb.domain.Diagnosticoscie10;
import gestWeb.service.Diagnosticoscie10Service;
import gestWeb.web.rest.errors.BadRequestAlertException;

import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link gestWeb.domain.Diagnosticoscie10}.
 */
@RestController
@RequestMapping("/api")
public class Diagnosticoscie10Resource {

    private final Logger log = LoggerFactory.getLogger(Diagnosticoscie10Resource.class);

    private static final String ENTITY_NAME = "diagnosticoscie10";

    // @Value("${jhipster.clientApp.name}")
    // private String applicationName;

    private final Diagnosticoscie10Service diagnosticoscie10Service;

    public Diagnosticoscie10Resource(Diagnosticoscie10Service diagnosticoscie10Service) {
        this.diagnosticoscie10Service = diagnosticoscie10Service;
    }

    /**
     * {@code POST  /diagnosticoscie10-s} : Create a new diagnosticoscie10.
     *
     * @param diagnosticoscie10 the diagnosticoscie10 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosticoscie10, or with status {@code 400 (Bad Request)} if the diagnosticoscie10 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diagnosticoscie10s")
    public ResponseEntity<Diagnosticoscie10DTO> createDiagnosticoscie10(@Valid @RequestBody Diagnosticoscie10DTO diagnosticoscie10) throws URISyntaxException {
        log.debug("REST request to save Diagnosticoscie10 : {}", diagnosticoscie10);
        if (diagnosticoscie10.getId() != null) {
            throw new BadRequestAlertException("A new diagnosticoscie10 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diagnosticoscie10DTO result = diagnosticoscie10Service.save(diagnosticoscie10);
        return ResponseEntity.created(new URI("/api/diagnosticoscie10-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diagnosticoscie10-s} : Updates an existing diagnosticoscie10.
     *
     * @param diagnosticoscie10 the diagnosticoscie10 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticoscie10,
     * or with status {@code 400 (Bad Request)} if the diagnosticoscie10 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticoscie10 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diagnosticoscie10")
    public ResponseEntity<Diagnosticoscie10DTO> updateDiagnosticoscie10(@Valid @RequestBody Diagnosticoscie10DTO diagnosticoscie10) throws URISyntaxException {
        log.debug("REST request to update Diagnosticoscie10 : {}", diagnosticoscie10);
        if (diagnosticoscie10.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Diagnosticoscie10DTO result = diagnosticoscie10Service.save(diagnosticoscie10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnosticoscie10.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diagnosticoscie10-s} : get all the diagnosticoscie10s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnosticoscie10s in body.
     */
    @GetMapping("/diagnosticoscie10")
    public ResponseEntity<List<Diagnosticoscie10DTO>> getAllDiagnosticoscie10s(Pageable pageable) {
        log.debug("REST request to get a page of Diagnosticoscie10s");
        Page<Diagnosticoscie10DTO> page = diagnosticoscie10Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diagnosticoscie10");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diagnosticoscie10-s/:id} : get the "id" diagnosticoscie10.
     *
     * @param id the id of the diagnosticoscie10 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosticoscie10, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diagnosticoscie10-s/{id}")
    public ResponseEntity<Diagnosticoscie10DTO> getDiagnosticoscie10(@PathVariable Long id) {
        log.debug("REST request to get Diagnosticoscie10 : {}", id);
        Optional<Diagnosticoscie10DTO> diagnosticoscie10 = diagnosticoscie10Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosticoscie10);
    }

    /**
     * {@code DELETE  /diagnosticoscie10-s/:id} : delete the "id" diagnosticoscie10.
     *
     * @param id the id of the diagnosticoscie10 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diagnosticoscie10-s/{id}")
    public ResponseEntity<Void> deleteDiagnosticoscie10(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosticoscie10 : {}", id);
        diagnosticoscie10Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
