package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.ConsultaService;
import gestWeb.service.FichaService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.ConsultaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 * REST controller for managing Consulta.
 */
@RestController
@RequestMapping("/api")
public class ConsultaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultaResource.class);

    private static final String ENTITY_NAME = "consulta";

    private final ConsultaService consultaService;
    private FichaService fichaService;

	List<String> files = new ArrayList<String>();

    public ConsultaResource(ConsultaService consultaService, FichaService fichaService) {
        this.consultaService = consultaService;
        this.fichaService = fichaService;
    }

    /**
     * POST  /consultas : Create a new consulta.
     *
     * @param consultaDTO the consultaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultaDTO, or with status 400 (Bad Request) if the consulta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultas")
    @Timed
    public ResponseEntity<ConsultaDTO> createConsulta(@RequestBody ConsultaDTO consultaDTO) throws URISyntaxException {
        log.debug("REST request to save Consulta : {}", consultaDTO);
        if (consultaDTO.getId() != null) {
            throw new BadRequestAlertException("A new consulta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultaDTO result = consultaService.save(consultaDTO);
        return ResponseEntity.created(new URI("/api/consultas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultas : Updates an existing consulta.
     *
     * @param consultaDTO the consultaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultaDTO,
     * or with status 400 (Bad Request) if the consultaDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultas")
    @Timed
    public ResponseEntity<ConsultaDTO> updateConsulta(@RequestBody ConsultaDTO consultaDTO) throws URISyntaxException {
        log.debug("REST request to update Consulta : {}", consultaDTO);
        if (consultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsultaDTO result = consultaService.save(consultaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultas : get all the consultas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consultas in body
     */
    @GetMapping("/consultas")
    @Timed
    public List<ConsultaDTO> getAllConsultas() {
        log.debug("REST request to get all Consultas");
        return consultaService.findAll();
    }

    /**
     * GET  /consultas/:id : get the "id" consulta.
     *
     * @param id the id of the consultaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultas/{id}")
    @Timed
    public ResponseEntity<ConsultaDTO> getConsulta(@PathVariable Long id) {
        log.debug("REST request to get Consulta : {}", id);
        Optional<ConsultaDTO> consultaDTO = consultaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultaDTO);
    }

    /**
     * DELETE  /consultas/:id : delete the "id" consulta.
     *
     * @param id the id of the consultaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultas/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        log.debug("REST request to delete Consulta : {}", id);
        consultaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    // Métodos para carga / obtención de archivos

     /**
     * POST  /consultas : Create a new consulta.
     *
     * @param file el archivo a subir
     * @return the ResponseEntity with status 200 (ok) 
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultas/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        String message = "";
		try {
			consultaService.store(file);
			files.add(file.getOriginalFilename());
 
            message = "Se adjuntó exitosamente " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "No se pudo adjuntar " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
    }

    @GetMapping("/consultas/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
        .stream().map(fileName -> MvcUriComponentsBuilder
			.fromMethodName(ConsultaResource.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());
 
		return ResponseEntity.ok().body(fileNames);
	}
 
	@GetMapping("/consultas/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = consultaService.loadFile(filename);
        log.debug("filename: " + filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

    // FIN Métodos para carga / obtención de archivos

    @GetMapping("/consultas/fichas")
	@ResponseBody
	public List<ConsultaDTO> getConsultasXFicha(@RequestParam Long idFicha) throws URISyntaxException{
        log.debug("REST request to consultasxfichas");
        log.debug("idFicha:");
        log.debug(Long.toString(idFicha));
        return consultaService.findConsultasXFicha(idFicha);
    }

    @GetMapping("/consultas/ficha-consulta")
	@Timed
    public ResponseEntity<Void> setFichaConsulta(@RequestParam Long idConsulta, @RequestParam Long idPaciente, @RequestParam Long idProfesional, @RequestParam Long idTurno, @RequestParam Long idEspecialidad) throws URISyntaxException {
        log.debug("Llegue al response:");
        consultaService.relacionTurnoProfPacCons(idTurno, idPaciente, idProfesional, idConsulta, idEspecialidad);
        Long idFicha = fichaService.fichaRelPacProfEsp(idPaciente, idProfesional, idEspecialidad);
        log.debug("Llegue al response y el idFicha es: ");
        log.debug(idFicha.toString());
        consultaService.setFichaConsulta(idFicha, idConsulta);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, idConsulta.toString())).build();
    }
}
