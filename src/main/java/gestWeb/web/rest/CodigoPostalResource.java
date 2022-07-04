package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.CodigoPostalService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.CodigoPostalDTO;
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
 * REST controller for managing CodigoPostal.
 */
@RestController
@RequestMapping("/api")
public class CodigoPostalResource {

    private final Logger log = LoggerFactory.getLogger(CodigoPostalResource.class);

    private static final String ENTITY_NAME = "codigoPostal";

    private final CodigoPostalService codigoPostalService;

    public CodigoPostalResource(CodigoPostalService codigoPostalService) {
        this.codigoPostalService = codigoPostalService;
    }

    /**
     * POST  /codigo-postals : Create a new codigoPostal.
     *
     * @param codigoPostalDTO the codigoPostalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new codigoPostalDTO, or with status 400 (Bad Request) if the codigoPostal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/codigo-postals")
    @Timed
    public ResponseEntity<CodigoPostalDTO> createCodigoPostal(@RequestBody CodigoPostalDTO codigoPostalDTO) throws URISyntaxException {
        log.debug("REST request to save CodigoPostal : {}", codigoPostalDTO);
        if (codigoPostalDTO.getId() != null) {
            throw new BadRequestAlertException("A new codigoPostal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodigoPostalDTO result = codigoPostalService.save(codigoPostalDTO);
        return ResponseEntity.created(new URI("/api/codigo-postals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /codigo-postals : Updates an existing codigoPostal.
     *
     * @param codigoPostalDTO the codigoPostalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated codigoPostalDTO,
     * or with status 400 (Bad Request) if the codigoPostalDTO is not valid,
     * or with status 500 (Internal Server Error) if the codigoPostalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/codigo-postals")
    @Timed
    public ResponseEntity<CodigoPostalDTO> updateCodigoPostal(@RequestBody CodigoPostalDTO codigoPostalDTO) throws URISyntaxException {
        log.debug("REST request to update CodigoPostal : {}", codigoPostalDTO);
        if (codigoPostalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodigoPostalDTO result = codigoPostalService.save(codigoPostalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, codigoPostalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /codigo-postals : get all the codigoPostals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of codigoPostals in body
     */
    @GetMapping("/codigo-postals")
    @Timed
    public ResponseEntity<List<CodigoPostalDTO>> getAllCodigoPostals(Pageable pageable) {
        log.debug("REST request to get a page of CodigoPostals");
        Page<CodigoPostalDTO> page = codigoPostalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/codigo-postals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /codigo-postals/:id : get the "id" codigoPostal.
     *
     * @param id the id of the codigoPostalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the codigoPostalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/codigo-postals/{id}")
    @Timed
    public ResponseEntity<CodigoPostalDTO> getCodigoPostal(@PathVariable Long id) {
        log.debug("REST request to get CodigoPostal : {}", id);
        Optional<CodigoPostalDTO> codigoPostalDTO = codigoPostalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codigoPostalDTO);
    }

    /**
     * DELETE  /codigo-postals/:id : delete the "id" codigoPostal.
     *
     * @param id the id of the codigoPostalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/codigo-postals/{id}")
    @Timed
    public ResponseEntity<Void> deleteCodigoPostal(@PathVariable Long id) {
        log.debug("REST request to delete CodigoPostal : {}", id);
        codigoPostalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/codigo-postals?query=:query : search for the codigoPostal corresponding
     * to the query.
     *
     * @param query the query of the codigoPostal search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/codigo-postals")
    @Timed
    public ResponseEntity<List<CodigoPostalDTO>> searchCodigoPostals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CodigoPostals for query {}", query);
        Page<CodigoPostalDTO> page = codigoPostalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/codigo-postals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/_search/codigo-postals/buscarIndividual")
    @Timed
    public ResponseEntity<List<CodigoPostalDTO>> getCPSearch(@RequestParam String cpStr) throws InterruptedException {
      List<CodigoPostalDTO> cp = this.codigoPostalService.findCP(cpStr);

      return new ResponseEntity<List<CodigoPostalDTO>>(cp,  HttpStatus.OK);
   }

    @GetMapping("/codigo-postals/Provincia")
    @Timed
    public ResponseEntity<List<CodigoPostalDTO>> findCPProvincia(@RequestParam String idProvincia) throws InterruptedException {
      List<CodigoPostalDTO> cp = this.codigoPostalService.findCPProvincia(idProvincia);

      return new ResponseEntity<List<CodigoPostalDTO>>(cp,  HttpStatus.OK);
   }
}
