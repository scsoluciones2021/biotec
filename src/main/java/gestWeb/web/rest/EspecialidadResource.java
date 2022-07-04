package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.EspecialidadService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.EspecialidadDTO;
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
 * REST controller for managing Especialidad.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadResource.class);

    private static final String ENTITY_NAME = "especialidad";

    private final EspecialidadService especialidadService;

    public EspecialidadResource(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    /**
     * POST  /especialidads : Create a new especialidad.
     *
     * @param especialidadDTO the especialidadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidadDTO, or with status 400 (Bad Request) if the especialidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especialidads")
    @Timed
    public ResponseEntity<EspecialidadDTO> createEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) throws URISyntaxException {
        log.debug("REST request to save Especialidad : {}", especialidadDTO);
        if (especialidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new especialidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspecialidadDTO result = especialidadService.save(especialidadDTO);
        return ResponseEntity.created(new URI("/api/especialidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidads : Updates an existing especialidad.
     *
     * @param especialidadDTO the especialidadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidadDTO,
     * or with status 400 (Bad Request) if the especialidadDTO is not valid,
     * or with status 500 (Internal Server Error) if the especialidadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especialidads")
    @Timed
    public ResponseEntity<EspecialidadDTO> updateEspecialidad(@Valid @RequestBody EspecialidadDTO especialidadDTO) throws URISyntaxException {
        log.debug("REST request to update Especialidad : {}", especialidadDTO);
        if (especialidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EspecialidadDTO result = especialidadService.save(especialidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, especialidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidads : get all the especialidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especialidads in body
     */
    @GetMapping("/especialidads")
    @Timed
    public ResponseEntity<List<EspecialidadDTO>> getAllEspecialidads(Pageable pageable) {
        log.debug("REST request to get a page of Especialidads");
        Page<EspecialidadDTO> page = especialidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especialidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /especialidads/:id : get the "id" especialidad.
     *
     * @param id the id of the especialidadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/especialidads/{id}")
    @Timed
    public ResponseEntity<EspecialidadDTO> getEspecialidad(@PathVariable Long id) {
        log.debug("REST request to get Especialidad : {}", id);
        Optional<EspecialidadDTO> especialidadDTO = especialidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialidadDTO);
    }

    /**
     * DELETE  /especialidads/:id : delete the "id" especialidad.
     *
     * @param id the id of the especialidadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especialidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long id) {
        log.debug("REST request to delete Especialidad : {}", id);
        especialidadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/especialidads?query=:query : search for the especialidad corresponding
     * to the query.
     *
     * @param query the query of the codigoPostal search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/especialidads")
    @Timed
    public ResponseEntity<List<EspecialidadDTO>> searchEspecialidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Especialidades for query {}", query);
        Page<EspecialidadDTO> page = especialidadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/especialidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

// Función utilizada desde la carga de profesionales
    @GetMapping("/_search/especialidad")
    @Timed
    public ResponseEntity<List<EspecialidadDTO>> getEspecialidadSearch(@RequestParam String nombreStr) throws InterruptedException {
      List<EspecialidadDTO> especialidad = this.especialidadService.findEspecialidad(nombreStr);

      return new ResponseEntity<List<EspecialidadDTO>>(especialidad,  HttpStatus.OK);
   }

   @GetMapping("/_search/especialidads/profesional")
    @Timed
    public ResponseEntity<List<EspecialidadDTO>> espeProfesional(@RequestParam String idProf) {
        List<EspecialidadDTO> especialidades = this.especialidadService.espeProfesional(idProf);

        return new ResponseEntity<List<EspecialidadDTO>>(especialidades,  HttpStatus.OK);
    }
}
