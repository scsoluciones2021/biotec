package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.ProfesionalService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.ProfesionalDTO;
import gestWeb.service.dto.ProfesionalTurnoDTO;
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
 * REST controller for managing Profesional.
 */
@RestController
@RequestMapping("/api")
public class ProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfesionalResource.class);

    private static final String ENTITY_NAME = "profesional";

    private final ProfesionalService profesionalService;

    public ProfesionalResource(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    /**
     * POST  /profesionals : Create a new profesional.
     *
     * @param profesionalDTO the profesionalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesionalDTO, or with status 400 (Bad Request) if the profesional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesionals")
    @Timed
    public ResponseEntity<ProfesionalDTO> createProfesional(@Valid @RequestBody ProfesionalDTO profesionalDTO) throws URISyntaxException {
        log.debug("REST request to save Profesional : {}", profesionalDTO);
        if (profesionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new profesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfesionalDTO result = profesionalService.save(profesionalDTO);
        return ResponseEntity.created(new URI("/api/profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profesionals : Updates an existing profesional.
     *
     * @param profesionalDTO the profesionalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesionalDTO,
     * or with status 400 (Bad Request) if the profesionalDTO is not valid,
     * or with status 500 (Internal Server Error) if the profesionalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profesionals")
    @Timed
    public ResponseEntity<ProfesionalDTO> updateProfesional(@Valid @RequestBody ProfesionalDTO profesionalDTO) throws URISyntaxException {
        log.debug("REST request to update Profesional : {}", profesionalDTO);
        if (profesionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfesionalDTO result = profesionalService.save(profesionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profesionals : get all the profesionals.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of profesionals in body
     */
    @GetMapping("/profesionals")
    @Timed
    public ResponseEntity<List<ProfesionalDTO>> getAllProfesionals(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Profesionals");
        Page<ProfesionalDTO> page;
        if (eagerload) {
            page = profesionalService.findAllWithEagerRelationships(pageable);
        } else {
            page = profesionalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/profesionals?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profesionals/:id : get the "id" profesional.
     *
     * @param id the id of the profesionalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesionalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profesionals/{id}")
    @Timed
    public ResponseEntity<ProfesionalDTO> getProfesional(@PathVariable Long id) {
        log.debug("REST request to get Profesional : {}", id);
        Optional<ProfesionalDTO> profesionalDTO = profesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profesionalDTO);
    }

    /**
     * DELETE  /profesionals/:id : delete the "id" profesional.
     *
     * @param id the id of the profesionalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesional(@PathVariable Long id) {
        log.debug("REST request to delete Profesional : {}", id);
        profesionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/profesionals-search")
    @Timed
    public ResponseEntity<List<ProfesionalDTO>> searchProfesionales(@RequestParam String query, Pageable pageable) {
        query = query.trim();        
        query = query.toUpperCase();
        log.debug("REST web profesional. Busqueda para {}: ", query);
        Page<ProfesionalDTO> page = profesionalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/profesional-search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/_search/profesionals")
    @Timed
    public ResponseEntity<List<ProfesionalDTO>> profesionalesByApeYNom(@RequestParam String apellido,
    @RequestParam String nombre, Pageable pageable) {
        String ape = null;
        String nom = null;

        if (!apellido.isEmpty()) {
            ape = apellido.toUpperCase();
        }

        if (!nombre.isEmpty()) {
            nom = nombre.toUpperCase();
        }        

        Page<ProfesionalDTO> page = profesionalService.findProfesionalByApeYNom(ape, nom, pageable);
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/profesional");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/_search/profesionals/busquedaIndividual")
    @Timed
    public ResponseEntity<List<ProfesionalDTO>> getProfesionalSearch(@RequestParam String nombreStr) throws InterruptedException {
      List<ProfesionalDTO> profesionales = this.profesionalService.findProfesional(nombreStr);

      return new ResponseEntity<List<ProfesionalDTO>>(profesionales,  HttpStatus.OK);
   }
    
    @GetMapping("/_search/profesionals/todosTurno")
    @Timed
    public ResponseEntity<List<ProfesionalTurnoDTO>> findAllCustom() throws InterruptedException {
      List<ProfesionalTurnoDTO> profesionales = this.profesionalService.findAllCustom();

      return new ResponseEntity<List<ProfesionalTurnoDTO>>(profesionales,  HttpStatus.OK);
   }

   @GetMapping("/profesionals/todos")
    @Timed
    public ResponseEntity<List<ProfesionalDTO>> todos() throws InterruptedException {
      List<ProfesionalDTO> profesionales = this.profesionalService.todos();

      return new ResponseEntity<List<ProfesionalDTO>>(profesionales,  HttpStatus.OK);
   }

   @GetMapping("/profesionals/usuario/{id}")
    @Timed
    public ResponseEntity<ProfesionalDTO> getProfesionalUsuario(@PathVariable Long id) {
        log.debug("REST request to get Profesional usuario : {}", id);
        Optional<ProfesionalDTO> profesionalDTO = profesionalService.findOneByUserId(id);
        return ResponseUtil.wrapOrNotFound(profesionalDTO);
    }

    @GetMapping("/profesionals/updateUserId")
    @Timed
    public ResponseEntity<Void> updateUserId(@RequestParam String idProfesional, @RequestParam String idUsuario) {
        log.debug("REST request to updateUserId : {}", idProfesional);
        log.debug("REST request to updateUserId : {}", idUsuario);
        Long profesionalId = Long.parseLong(idProfesional);
        Long usuarioId = Long.parseLong(idUsuario);
        profesionalService.updateUserId(profesionalId, usuarioId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, idProfesional.toString())).build();
    }


    @GetMapping("/profesionals/searchProfesionalWithoutUser")
    @Timed
    public Boolean searchProfesionalWithoutUser(@RequestParam String idProfesional) {
        log.debug("REST request to searchProfesionalWithoutUser : {}", idProfesional);
        Long profesionalId = Long.parseLong(idProfesional);
        return profesionalService.searchProfesionalWithoutUser(profesionalId);
    }

    @GetMapping("/profesionals/updateUserIdEliminado")
    @Timed
    public ResponseEntity<Void> updateUserIdEliminado(@RequestParam String idUsuario) {
        log.debug("REST request to updateUserIdEliminado : {}", idUsuario);
        Long usuarioId = Long.parseLong(idUsuario);
        profesionalService.updateUserIdEliminado(usuarioId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, idUsuario.toString())).build();
    }

}
