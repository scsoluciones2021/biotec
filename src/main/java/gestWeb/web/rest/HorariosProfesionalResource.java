package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.HorariosProfesionalService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.HorariosProfesionalDTO;
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
 * REST controller for managing HorariosProfesional.
 */
@RestController
@RequestMapping("/api")
public class HorariosProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(HorariosProfesionalResource.class);

    private static final String ENTITY_NAME = "horariosProfesional";

    private final HorariosProfesionalService horariosProfesionalService;

    public HorariosProfesionalResource(HorariosProfesionalService horariosProfesionalService) {
        this.horariosProfesionalService = horariosProfesionalService;
    }

    /**
     * POST  /horarios-profesionals : Create a new horariosProfesional.
     *
     * @param horariosProfesionalDTO the horariosProfesionalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horariosProfesionalDTO, or with status 400 (Bad Request) if the horariosProfesional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horarios-profesionals")
    @Timed
    public ResponseEntity<HorariosProfesionalDTO> createHorariosProfesional(@Valid @RequestBody HorariosProfesionalDTO horariosProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to save HorariosProfesional : {}", horariosProfesionalDTO);
        if (horariosProfesionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new horariosProfesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorariosProfesionalDTO result = horariosProfesionalService.save(horariosProfesionalDTO);
        return ResponseEntity.created(new URI("/api/horarios-profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horarios-profesionals : Updates an existing horariosProfesional.
     *
     * @param horariosProfesionalDTO the horariosProfesionalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horariosProfesionalDTO,
     * or with status 400 (Bad Request) if the horariosProfesionalDTO is not valid,
     * or with status 500 (Internal Server Error) if the horariosProfesionalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horarios-profesionals")
    @Timed
    public ResponseEntity<HorariosProfesionalDTO> updateHorariosProfesional(@Valid @RequestBody HorariosProfesionalDTO horariosProfesionalDTO) throws URISyntaxException {
        log.debug("REST request to update HorariosProfesional : {}", horariosProfesionalDTO);
        if (horariosProfesionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorariosProfesionalDTO result = horariosProfesionalService.save(horariosProfesionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horariosProfesionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horarios-profesionals : get all the horariosProfesionals.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of horariosProfesionals in body
     */
    @GetMapping("/horarios-profesionals")
    @Timed
    public ResponseEntity<List<HorariosProfesionalDTO>> getAllHorariosProfesionals(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of HorariosProfesionals");
        Page<HorariosProfesionalDTO> page;
        if (eagerload) {
            page = horariosProfesionalService.findAllWithEagerRelationships(pageable);
        } else {
            page = horariosProfesionalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/horarios-profesionals?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /horarios-profesionals/:id : get the "id" horariosProfesional.
     *
     * @param id the id of the horariosProfesionalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horariosProfesionalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/horarios-profesionals/{id}")
    @Timed
    public ResponseEntity<HorariosProfesionalDTO> getHorariosProfesional(@PathVariable Long id) {
        log.debug("REST request to get HorariosProfesional : {}", id);
        Optional<HorariosProfesionalDTO> horariosProfesionalDTO = horariosProfesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horariosProfesionalDTO);
    }

    /**
     * DELETE  /horarios-profesionals/:id : delete the "id" horariosProfesional.
     *
     * @param id the id of the horariosProfesionalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horarios-profesionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteHorariosProfesional(@PathVariable Long id) {
        log.debug("REST request to delete HorariosProfesional : {}", id);
        horariosProfesionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/horarios-profesionals/hor-profesional")
    @Timed
    public ResponseEntity<List<HorariosProfesionalDTO>> getHorariosXProfesional(@RequestParam Long idProfesional, @RequestParam Long idEspecialidad) {
        log.debug("ProfesionalNM : {}", idProfesional);
        log.debug("EspecialidadNM : {}", idEspecialidad);
        List<HorariosProfesionalDTO> horariosProfesionalDTO = horariosProfesionalService.findXProf(idProfesional, idEspecialidad);
        return new ResponseEntity<List<HorariosProfesionalDTO>>(horariosProfesionalDTO, HttpStatus.OK);
    }

    @GetMapping("/horarios-profesionals/busqueda-general")
    @Timed
    public ResponseEntity<List<HorariosProfesionalDTO>> getHorariosProfesionalSearchGral(@RequestParam String nombre,@RequestParam String especialidad, Pageable pageable) throws InterruptedException {

        String nom = null;
        String esp = null;
    
        if (!nombre.isEmpty()) {
            nom = nombre;
        }

        if (!especialidad.isEmpty()) {
            esp = especialidad;
        }

        Page<HorariosProfesionalDTO> page = this.horariosProfesionalService.findHorariosProfesional(nom, esp, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/horarios-profesionals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
