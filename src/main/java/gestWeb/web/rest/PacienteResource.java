package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.PacienteService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.PacienteDTO;
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
 * REST controller for managing Paciente.
 */
@RestController
@RequestMapping("/api")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";

    private final PacienteService pacienteService;

    public PacienteResource(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * POST /pacientes : Create a new paciente.
     *
     * @param pacienteDTO the pacienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         pacienteDTO, or with status 400 (Bad Request) if the paciente has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO)
            throws URISyntaxException {
        log.debug("REST request to save Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacienteDTO result = pacienteService.save(pacienteDTO);
        return ResponseEntity.created(new URI("/api/pacientes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /pacientes : Updates an existing paciente.
     *
     * @param pacienteDTO the pacienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         pacienteDTO, or with status 400 (Bad Request) if the pacienteDTO is
     *         not valid, or with status 500 (Internal Server Error) if the
     *         pacienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDTO)
            throws URISyntaxException {
        log.debug("REST request to update Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PacienteDTO result = pacienteService.save(pacienteDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacienteDTO.getId().toString())).body(result);
    }

    /**
     * GET /pacientes : get all the pacientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pacientes in
     *         body
     */
    @GetMapping("/pacientes")
    @Timed
    public ResponseEntity<List<PacienteDTO>> getAllPacientes(Pageable pageable) {
        log.debug("REST request to get a page of Pacientes");
        Page<PacienteDTO> page = pacienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pacientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /pacientes/:id : get the "id" paciente.
     *
     * @param id the id of the pacienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         pacienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable Long id) {
        log.debug("REST request to get Paciente : {}", id);
        Optional<PacienteDTO> pacienteDTO = pacienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacienteDTO);
    }

    /**
     * DELETE /pacientes/:id : delete the "id" paciente.
     *
     * @param id the id of the pacienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        log.debug("REST request to delete Paciente : {}", id);
        pacienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/paciente?query=:query : search for the paciente corresponding
     * to the query.
     *
     * @param query    the query of the paciente search
     * @param pageable the pagination information
     * @return the result of the search
     */
    /*
     * @GetMapping("/_search/pacientes")
     * 
     * @Timed public ResponseEntity<List<PacienteDTO>> searchPaciente(@RequestParam
     * String query, Pageable pageable) {
     * log.debug("REST request to search Paciente for query {}", query);
     * /*Page<PacienteDTO> page = codigoPostalService.search(query, pageable);
     * HttpHeaders headers =
     * PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
     * "/api/_search/pacientes"); return new ResponseEntity<>(page.getContent(),
     * headers, HttpStatus.OK);
     * 
     * Page<PacienteDTO> page = this.pacienteService.searchPaciente(query,
     * pageable); HttpHeaders headers =
     * PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
     * "/api/_search/pacientes");
     * 
     * return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK); }
     */

    @GetMapping("/pacientes/busqueda-ficha")
    @Timed
    public ResponseEntity<List<PacienteDTO>> getPacientesSearch(@RequestParam String nombreStr)
            throws InterruptedException {
        List<PacienteDTO> pacientes = this.pacienteService.findPaciente(nombreStr);

        return new ResponseEntity<List<PacienteDTO>>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/pacientes/busqueda-general")
    @Timed
    public ResponseEntity<List<PacienteDTO>> getPacientesSearchGral(@RequestParam String apellido,
            @RequestParam String nombre, @RequestParam String dni, Pageable pageable) throws InterruptedException {

        String ape = null;
        String nom = null;
        String dni2 = null;

        if (!apellido.isEmpty()) {
            ape = apellido;
        }

        if (!nombre.isEmpty()) {
            nom = nombre;
        }

        if (!dni.isEmpty()) {
            dni2 = dni;
        }

        // List<PacienteDTO> pacientes = this.pacienteService.findPacientes(ape, nom,
        // dni2);

        Page<PacienteDTO> page = this.pacienteService.findPacientes(ape, nom, dni2, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pacientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        // return new ResponseEntity<List<PacienteDTO>>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/pacientes/busqueda-turno")
    @Timed
    public ResponseEntity<PacienteDTO> getPacientesSearchDni(@RequestParam String dni)
            throws InterruptedException {
                Optional<PacienteDTO> pacienteDTO = this.pacienteService.findPacienteXDni(dni);

        return ResponseUtil.wrapOrNotFound(pacienteDTO);
    }
}
