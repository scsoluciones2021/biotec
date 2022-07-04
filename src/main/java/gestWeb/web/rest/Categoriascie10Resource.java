package gestWeb.web.rest;

import gestWeb.service.dto.Categoriascie10DTO;
import gestWeb.domain.Categoriascie10;
import gestWeb.service.Categoriascie10Service;
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
 * REST controller for managing {@link gestWeb.domain.Categoriascie10}.
 */
@RestController
@RequestMapping("/api")
public class Categoriascie10Resource {

    private final Logger log = LoggerFactory.getLogger(Categoriascie10Resource.class);

    private static final String ENTITY_NAME = "categoriascie10";

    // @Value("${jhipster.clientApp.name}")
    // private String applicationName;

    private final Categoriascie10Service categoriascie10Service;

    public Categoriascie10Resource(Categoriascie10Service categoriascie10Service) {
        this.categoriascie10Service = categoriascie10Service;
    }

    /**
     * {@code POST  /categoriascie10-s} : Create a new categoriascie10.
     *
     * @param categoriascie10 the categoriascie10 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriascie10, or with status {@code 400 (Bad Request)} if the categoriascie10 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoriascie10")
    public ResponseEntity<Categoriascie10DTO> createCategoriascie10(@Valid @RequestBody Categoriascie10DTO categoriascie10) throws URISyntaxException {
        log.debug("REST request to save Categoriascie10 : {}", categoriascie10);
        if (categoriascie10.getId() != null) {
            throw new BadRequestAlertException("A new categoriascie10 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Categoriascie10DTO result = categoriascie10Service.save(categoriascie10);
        return ResponseEntity.created(new URI("/api/categoriascie10/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert( ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoriascie10-s} : Updates an existing categoriascie10.
     *
     * @param categoriascie10 the categoriascie10 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriascie10,
     * or with status {@code 400 (Bad Request)} if the categoriascie10 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriascie10 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoriascie10")
    public ResponseEntity<Categoriascie10DTO> updateCategoriascie10(@Valid @RequestBody Categoriascie10DTO categoriascie10) throws URISyntaxException {
        log.debug("REST request to update Categoriascie10 : {}", categoriascie10);
        if (categoriascie10.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Categoriascie10DTO result = categoriascie10Service.save(categoriascie10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert( ENTITY_NAME, categoriascie10.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoriascie10-s} : get all the categoriascie10s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriascie10s in body.
     */
    @GetMapping("/categoriascie10")
    public ResponseEntity<List<Categoriascie10DTO>> getAllCategoriascie10s(Pageable pageable) {
        log.debug("REST request to get a page of Categoriascie10s");
        Page<Categoriascie10DTO> page = categoriascie10Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categoriascie10");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoriascie10-s/:id} : get the "id" categoriascie10.
     *
     * @param id the id of the categoriascie10 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriascie10, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoriascie10-s/{id}")
    public ResponseEntity<Categoriascie10DTO> getCategoriascie10(@PathVariable Long id) {
        log.debug("REST request to get Categoriascie10 : {}", id);
        Optional<Categoriascie10DTO> categoriascie10 = categoriascie10Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriascie10);
    }

    /**
     * {@code DELETE  /categoriascie10-s/:id} : delete the "id" categoriascie10.
     *
     * @param id the id of the categoriascie10 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoriascie10-s/{id}")
    public ResponseEntity<Void> deleteCategoriascie10(@PathVariable Long id) {
        log.debug("REST request to delete Categoriascie10 : {}", id);
        categoriascie10Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
