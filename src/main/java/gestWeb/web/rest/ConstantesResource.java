package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.ConstantesService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.ConstantesDTO;
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
 * REST controller for managing Constantes.
 */
@RestController
@RequestMapping("/api")
public class ConstantesResource {

    private final Logger log = LoggerFactory.getLogger(ConstantesResource.class);

    private static final String ENTITY_NAME = "constantes";

    private final ConstantesService constantesService;

    public ConstantesResource(ConstantesService constantesService) {
        this.constantesService = constantesService;
    }

    /**
     * POST  /constantes : Create a new constantes.
     *
     * @param constantesDTO the constantesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new constantesDTO, or with status 400 (Bad Request) if the constantes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/constantes")
    @Timed
    public ResponseEntity<ConstantesDTO> createConstantes(@RequestBody ConstantesDTO constantesDTO) throws URISyntaxException {
        log.debug("REST request to save Constantes : {}", constantesDTO);
        if (constantesDTO.getId() != null) {
            throw new BadRequestAlertException("A new constantes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConstantesDTO result = constantesService.save(constantesDTO);
        return ResponseEntity.created(new URI("/api/constantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /constantes : Updates an existing constantes.
     *
     * @param constantesDTO the constantesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated constantesDTO,
     * or with status 400 (Bad Request) if the constantesDTO is not valid,
     * or with status 500 (Internal Server Error) if the constantesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/constantes")
    @Timed
    public ResponseEntity<ConstantesDTO> updateConstantes(@RequestBody ConstantesDTO constantesDTO) throws URISyntaxException {
        log.debug("REST request to update Constantes : {}", constantesDTO);
        if (constantesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConstantesDTO result = constantesService.save(constantesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, constantesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /constantes : get all the constantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of constantes in body
     */
    @GetMapping("/constantes")
    @Timed
    public List<ConstantesDTO> getAllConstantes() {
        log.debug("REST request to get all Constantes");
        return constantesService.findAll();
    }

    /**
     * GET  /constantes/:id : get the "id" constantes.
     *
     * @param id the id of the constantesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the constantesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/constantes/{id}")
    @Timed
    public ResponseEntity<ConstantesDTO> getConstantes(@PathVariable Long id) {
        log.debug("REST request to get Constantes : {}", id);
        Optional<ConstantesDTO> constantesDTO = constantesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(constantesDTO);
    }

    /**
     * DELETE  /constantes/:id : delete the "id" constantes.
     *
     * @param id the id of the constantesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/constantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteConstantes(@PathVariable Long id) {
        log.debug("REST request to delete Constantes : {}", id);
        constantesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
