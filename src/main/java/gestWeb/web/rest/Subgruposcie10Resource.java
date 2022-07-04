package gestWeb.web.rest;

import gestWeb.service.dto.Subgruposcie10DTO;
import gestWeb.domain.Subgruposcie10;
import gestWeb.service.Subgruposcie10Service;
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
 * REST controller for managing {@link gestWeb.domain.Subgruposcie10}.
 */
@RestController
@RequestMapping("/api")
public class Subgruposcie10Resource {

    private final Logger log = LoggerFactory.getLogger(Subgruposcie10Resource.class);

    private static final String ENTITY_NAME = "subgruposcie10";

    // @Value("${jhipster.clientApp.name}")
    // private String applicationName;

    private final Subgruposcie10Service subgruposcie10Service;

    public Subgruposcie10Resource(Subgruposcie10Service subgruposcie10Service) {
        this.subgruposcie10Service = subgruposcie10Service;
    }

    /**
     * {@code POST  /subgruposcie10} : Create a new subgruposcie10.
     *
     * @param subgruposcie10 the subgruposcie10 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subgruposcie10, or with status {@code 400 (Bad Request)} if the subgruposcie10 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subgruposcie10")
    public ResponseEntity<Subgruposcie10DTO> createSubgruposcie10(@Valid @RequestBody Subgruposcie10DTO subgruposcie10) throws URISyntaxException {
        log.debug("REST request to save Subgruposcie10 : {}", subgruposcie10);
        if (subgruposcie10.getId() != null) {
            throw new BadRequestAlertException("A new subgruposcie10 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subgruposcie10DTO result = subgruposcie10Service.save(subgruposcie10);
        return ResponseEntity.created(new URI("/api/subgruposcie10/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subgruposcie10} : Updates an existing subgruposcie10.
     *
     * @param subgruposcie10 the subgruposcie10 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subgruposcie10,
     * or with status {@code 400 (Bad Request)} if the subgruposcie10 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subgruposcie10 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subgruposcie10")
    public ResponseEntity<Subgruposcie10DTO> updateSubgruposcie10(@Valid @RequestBody Subgruposcie10DTO subgruposcie10) throws URISyntaxException {
        log.debug("REST request to update Subgruposcie10 : {}", subgruposcie10);
        if (subgruposcie10.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subgruposcie10DTO result = subgruposcie10Service.save(subgruposcie10);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subgruposcie10.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subgruposcie10} : get all the subgruposcie10s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subgruposcie10s in body.
     */
    @GetMapping("/subgruposcie10")
    public ResponseEntity<List<Subgruposcie10DTO>> getAllSubgruposcie10s(Pageable pageable) {
        log.debug("REST request to get a page of Subgruposcie10s");
        Page<Subgruposcie10DTO> page = subgruposcie10Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,"/api/subgruposcie10");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subgruposcie10/:id} : get the "id" subgruposcie10.
     *
     * @param id the id of the subgruposcie10 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subgruposcie10, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subgruposcie10/{id}")
    public ResponseEntity<Subgruposcie10DTO> getSubgruposcie10(@PathVariable Long id) {
        log.debug("REST request to get Subgruposcie10 : {}", id);
        Optional<Subgruposcie10DTO> subgruposcie10 = subgruposcie10Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(subgruposcie10);
    }

    /**
     * {@code DELETE  /subgruposcie10/:id} : delete the "id" subgruposcie10.
     *
     * @param id the id of the subgruposcie10 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subgruposcie10/{id}")
    public ResponseEntity<Void> deleteSubgruposcie10(@PathVariable Long id) {
        log.debug("REST request to delete Subgruposcie10 : {}", id);
        subgruposcie10Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
