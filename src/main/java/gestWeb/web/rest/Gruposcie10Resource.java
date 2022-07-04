package gestWeb.web.rest;

import gestWeb.service.dto.Gruposcie10DTO;
import gestWeb.domain.Gruposcie10;
import gestWeb.service.Gruposcie10Service;
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
 * REST controller for managing {@link gestWeb.domain.Gruposcie10}.
 */
@RestController
@RequestMapping("/api")
public class Gruposcie10Resource {

    private final Logger log = LoggerFactory.getLogger(Gruposcie10Resource.class);

    private static final String ENTITY_NAME = "gruposcie10";

    // @Value("${jhipster.clientApp.name}")
    // private String applicationName;

    private final Gruposcie10Service gruposcie10Service;

    public Gruposcie10Resource(Gruposcie10Service gruposcie10Service) {
        this.gruposcie10Service = gruposcie10Service;
    }

    /**
     * {@code POST  /gruposcie10} : Create a new gruposcie10.
     *
     * @param gruposcie10 the gruposcie10 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gruposcie10, or with status {@code 400 (Bad Request)} if the gruposcie10 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gruposcie10")
    public ResponseEntity<Gruposcie10DTO> createGruposcie10(@Valid @RequestBody Gruposcie10DTO gruposcie10) throws URISyntaxException {
        log.debug("REST request to save Gruposcie10 : {}", gruposcie10);
        if (gruposcie10.getId() != null) {
            throw new BadRequestAlertException("A new gruposcie10 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gruposcie10DTO result = gruposcie10Service.save(gruposcie10);
        return ResponseEntity.created(new URI("/api/gruposcie10/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gruposcie10} : Updates an existing gruposcie10.
     *
     * @param gruposcie10 the gruposcie10 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruposcie10,
     * or with status {@code 400 (Bad Request)} if the gruposcie10 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gruposcie10 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gruposcie10")
    public ResponseEntity<Gruposcie10DTO> updateGruposcie10(@Valid @RequestBody Gruposcie10DTO gruposcie10) throws URISyntaxException {
        log.debug("REST request to update Gruposcie10 : {}", gruposcie10);
        if (gruposcie10.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gruposcie10DTO result = gruposcie10Service.save(gruposcie10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gruposcie10.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gruposcie10} : get all the gruposcie10s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gruposcie10s in body.
     */
    @GetMapping("/gruposcie10")
    public ResponseEntity<List<Gruposcie10DTO>> getAllGruposcie10s(Pageable pageable) {
        log.debug("REST request to get a page of Gruposcie10s");
        Page<Gruposcie10DTO> page = gruposcie10Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gruposcie10");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gruposcie10/:id} : get the "id" gruposcie10.
     *
     * @param id the id of the gruposcie10 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gruposcie10, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gruposcie10/{id}")
    public ResponseEntity<Gruposcie10DTO> getGruposcie10(@PathVariable Long id) {
        log.debug("REST request to get Gruposcie10 : {}", id);
        Optional<Gruposcie10DTO> gruposcie10 = gruposcie10Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(gruposcie10);
    }

    /**
     * {@code DELETE  /gruposcie10/:id} : delete the "id" gruposcie10.
     *
     * @param id the id of the gruposcie10 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gruposcie10/{id}")
    public ResponseEntity<Void> deleteGruposcie10(@PathVariable Long id) {
        log.debug("REST request to delete Gruposcie10 : {}", id);
        gruposcie10Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
