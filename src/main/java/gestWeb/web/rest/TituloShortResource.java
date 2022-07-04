package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.TituloShortService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.TituloShortDTO;
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
 * REST controller for managing TituloShort.
 */
@RestController
@RequestMapping("/api")
public class TituloShortResource {

    private final Logger log = LoggerFactory.getLogger(TituloShortResource.class);

    private static final String ENTITY_NAME = "tituloShort";

    private final TituloShortService tituloShortService;

    public TituloShortResource(TituloShortService tituloShortService) {
        this.tituloShortService = tituloShortService;
    }

    /**
     * POST  /titulo-shorts : Create a new tituloShort.
     *
     * @param tituloShortDTO the tituloShortDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tituloShortDTO, or with status 400 (Bad Request) if the tituloShort has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/titulo-shorts")
    @Timed
    public ResponseEntity<TituloShortDTO> createTituloShort(@Valid @RequestBody TituloShortDTO tituloShortDTO) throws URISyntaxException {
        log.debug("REST request to save TituloShort : {}", tituloShortDTO);
        if (tituloShortDTO.getId() != null) {
            throw new BadRequestAlertException("A new tituloShort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TituloShortDTO result = tituloShortService.save(tituloShortDTO);
        return ResponseEntity.created(new URI("/api/titulo-shorts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /titulo-shorts : Updates an existing tituloShort.
     *
     * @param tituloShortDTO the tituloShortDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tituloShortDTO,
     * or with status 400 (Bad Request) if the tituloShortDTO is not valid,
     * or with status 500 (Internal Server Error) if the tituloShortDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/titulo-shorts")
    @Timed
    public ResponseEntity<TituloShortDTO> updateTituloShort(@Valid @RequestBody TituloShortDTO tituloShortDTO) throws URISyntaxException {
        log.debug("REST request to update TituloShort : {}", tituloShortDTO);
        if (tituloShortDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TituloShortDTO result = tituloShortService.save(tituloShortDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tituloShortDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titulo-shorts : get all the tituloShorts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tituloShorts in body
     */
    @GetMapping("/titulo-shorts")
    @Timed
    public List<TituloShortDTO> getAllTituloShorts() {
        log.debug("REST request to get all TituloShorts");
        return tituloShortService.findAll();
    }

    /**
     * GET  /titulo-shorts/:id : get the "id" tituloShort.
     *
     * @param id the id of the tituloShortDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tituloShortDTO, or with status 404 (Not Found)
     */
    @GetMapping("/titulo-shorts/{id}")
    @Timed
    public ResponseEntity<TituloShortDTO> getTituloShort(@PathVariable Long id) {
        log.debug("REST request to get TituloShort : {}", id);
        Optional<TituloShortDTO> tituloShortDTO = tituloShortService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tituloShortDTO);
    }

    /**
     * DELETE  /titulo-shorts/:id : delete the "id" tituloShort.
     *
     * @param id the id of the tituloShortDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/titulo-shorts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTituloShort(@PathVariable Long id) {
        log.debug("REST request to delete TituloShort : {}", id);
        tituloShortService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
