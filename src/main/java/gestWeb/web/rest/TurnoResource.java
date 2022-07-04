package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.TurnoService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.TurnoDTO;
import gestWeb.service.dto.TurnoProfDTO;
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
 * REST controller for managing Turno.
 */
@RestController
@RequestMapping("/api")
public class TurnoResource {

    private final Logger log = LoggerFactory.getLogger(TurnoResource.class);

    private static final String ENTITY_NAME = "turno";

    private final TurnoService turnoService;

    public TurnoResource(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    /**
     * POST  /turnos : Create a new turno.
     *
     * @param turnoDTO the turnoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turnoDTO, or with status 400 (Bad Request) if the turno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turnos")
    @Timed
    public ResponseEntity<TurnoDTO> createTurno(@Valid @RequestBody TurnoDTO turnoDTO) throws URISyntaxException {
        log.debug("REST request to save Turno : {}", turnoDTO);
        if (turnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new turno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TurnoDTO result = turnoService.save(turnoDTO);
        return ResponseEntity.created(new URI("/api/turnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turnos : Updates an existing turno.
     *
     * @param turnoDTO the turnoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turnoDTO,
     * or with status 400 (Bad Request) if the turnoDTO is not valid,
     * or with status 500 (Internal Server Error) if the turnoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turnos")
    @Timed
    public ResponseEntity<TurnoDTO> updateTurno(@Valid @RequestBody TurnoDTO turnoDTO) throws URISyntaxException {
        log.debug("REST request to update Turno : {}", turnoDTO);
        if (turnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TurnoDTO result = turnoService.save(turnoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, turnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turnos : get all the turnos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of turnos in body
     */
    @GetMapping("/turnos")
    @Timed
    public ResponseEntity<List<TurnoDTO>> getAllTurnos(Pageable pageable) {
        log.debug("REST request to get a page of Turnos");
        Page<TurnoDTO> page = turnoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/turnos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /turnos/:id : get the "id" turno.
     *
     * @param id the id of the turnoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turnoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/turnos/{id}")
    @Timed
    public ResponseEntity<TurnoDTO> getTurno(@PathVariable Long id) {
        log.debug("REST request to get Turno : {}", id);
        Optional<TurnoDTO> turnoDTO = turnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turnoDTO);
    }

    /**
     * DELETE  /turnos/:id : delete the "id" turno.
     *
     * @param id the id of the turnoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turnos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        log.debug("REST request to delete Turno : {}", id);
        turnoService.cambiarEstado(id, new Integer(5));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/turnos/ocup-prof")
    @Timed
    public ResponseEntity<List<String>> horariosOcupadosProf(@RequestParam String idProf, String idEsp, String dia) throws InterruptedException {
      Long prof = Long.valueOf(idProf).longValue();
      Long esp = Long.valueOf(idEsp).longValue();
      List<String> horarios = this.turnoService.horariosOcupadosProf(prof, esp, dia);

      return new ResponseEntity<List<String>>(horarios,  HttpStatus.OK);
   }

    @GetMapping("/turnos/busqueda-filtros")
    @Timed
    public ResponseEntity<List<TurnoDTO>> busqueda_filtros(@RequestParam String[] query, @RequestParam List<String> espSel, @RequestParam List<String> profSel, Pageable pageable) {
        Page<TurnoDTO> page = turnoService.busqueda_filtros(query, espSel, profSel, pageable);
        String retorno = query[7];
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/"+retorno);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/turnos/impresion")
    @Timed
    public ResponseEntity<List<TurnoDTO>> impresion(@RequestParam String[] query, @RequestParam List<String> espSel, @RequestParam List<String> profSel, Pageable pageable) {
        List<TurnoDTO> lista = turnoService.busqueda_filtros_impresion(query, espSel, profSel);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/turnos/modificar-estado")
    @Timed
    public ResponseEntity<Void> CambiarEstadoTurno(@RequestParam Long id, @RequestParam Integer estado) {
        log.debug("REST request to Cambiar estado Turno : {}", id);
        turnoService.cambiarEstado(id, estado);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/turnos/busqueda-medico")
    @Timed
    public ResponseEntity<List<TurnoDTO>> busqueda_medico(@RequestBody TurnoProfDTO turnoPDTO, Pageable pageable) throws URISyntaxException{
        Page<TurnoDTO> page = turnoService.busqueda_medico(turnoPDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/"+turnoPDTO.getRetorno());
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * TieneConsulta  /turnos/tieneconsulta/:id : se fija en la tabla de relacion si ya existe
     *  una consulta para este turno.
     *
     * @param id the id of the turnoDTO
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/turnos/tieneconsulta/{id}")
    @Timed
    public Integer tieneConsulta(@PathVariable Long id) {
        log.debug("REST request to tieneconsulta Turno : {}", id);
        return turnoService.tieneConsulta(id);
    }
}
