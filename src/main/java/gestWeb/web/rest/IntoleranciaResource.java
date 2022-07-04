package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.IntoleranciaService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.IntoleranciaDTO;
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
 * REST controller for managing Intolerancia.
 */
@RestController
@RequestMapping("/api")
public class IntoleranciaResource {

    private final Logger log = LoggerFactory.getLogger(IntoleranciaResource.class);

    private static final String ENTITY_NAME = "intolerancia";

    private final IntoleranciaService intoleranciaService;

    public IntoleranciaResource(IntoleranciaService intoleranciaService) {
        this.intoleranciaService = intoleranciaService;
    }

    /**
     * POST  /intolerancias : Create a new intolerancia.
     *
     * @param intoleranciaDTO the intoleranciaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intoleranciaDTO, or with status 400 (Bad Request) if the intolerancia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intolerancias")
    @Timed
    public ResponseEntity<IntoleranciaDTO> createIntolerancia(@Valid @RequestBody IntoleranciaDTO intoleranciaDTO) throws URISyntaxException {
        log.debug("REST request to save Intolerancia : {}", intoleranciaDTO);
        if (intoleranciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new intolerancia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntoleranciaDTO result = intoleranciaService.save(intoleranciaDTO);
        return ResponseEntity.created(new URI("/api/intolerancias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intolerancias : Updates an existing intolerancia.
     *
     * @param intoleranciaDTO the intoleranciaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intoleranciaDTO,
     * or with status 400 (Bad Request) if the intoleranciaDTO is not valid,
     * or with status 500 (Internal Server Error) if the intoleranciaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intolerancias")
    @Timed
    public ResponseEntity<IntoleranciaDTO> updateIntolerancia(@Valid @RequestBody IntoleranciaDTO intoleranciaDTO) throws URISyntaxException {
        log.debug("REST request to update Intolerancia : {}", intoleranciaDTO);
        if (intoleranciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntoleranciaDTO result = intoleranciaService.save(intoleranciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intoleranciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intolerancias : get all the intolerancias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of intolerancias in body
     */
    @GetMapping("/intolerancias")
    @Timed
    public List<IntoleranciaDTO> getAllIntolerancias() {
        log.debug("REST request to get all Intolerancias");
        return intoleranciaService.findAll();
    }

    /**
     * GET  /intolerancias/:id : get the "id" intolerancia.
     *
     * @param id the id of the intoleranciaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intoleranciaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/intolerancias/{id}")
    @Timed
    public ResponseEntity<IntoleranciaDTO> getIntolerancia(@PathVariable Long id) {
        log.debug("REST request to get Intolerancia : {}", id);
        Optional<IntoleranciaDTO> intoleranciaDTO = intoleranciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intoleranciaDTO);
    }

    /**
     * DELETE  /intolerancias/:id : delete the "id" intolerancia.
     *
     * @param id the id of the intoleranciaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intolerancias/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntolerancia(@PathVariable Long id) {
        log.debug("REST request to delete Intolerancia : {}", id);
        intoleranciaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
