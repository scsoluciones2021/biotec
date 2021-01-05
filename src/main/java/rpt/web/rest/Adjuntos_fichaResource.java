package rpt.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.github.jhipster.web.util.ResponseUtil;
import rpt.service.Adjuntos_fichaService;
import rpt.service.dto.Adjuntos_fichaCriteria;
import rpt.service.dto.Adjuntos_fichaDTO;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;

import org.springframework.core.io.Resource;
import java.nio.file.Paths;


/**
 * REST controller for managing Adjuntos_ficha.
 */
@RestController
@RequestMapping("/api")
public class Adjuntos_fichaResource {

    private final Logger log = LoggerFactory.getLogger(Adjuntos_fichaResource.class);

    private static final String ENTITY_NAME = "adjuntos_ficha";

    private final Adjuntos_fichaService adjuntos_fichaService;

    List<String> files = new ArrayList<String>();


    public Adjuntos_fichaResource(Adjuntos_fichaService adjuntos_fichaService) {
        this.adjuntos_fichaService = adjuntos_fichaService;
    }

    /**
     * POST  /adjuntos-fichas : Create a new adjuntos_ficha.
     *
     * @param adjuntos_fichaDTO the adjuntos_fichaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adjuntos_fichaDTO, or with status 400 (Bad Request) if the adjuntos_ficha has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adjuntos-fichas")
    @Timed
    public ResponseEntity<Adjuntos_fichaDTO> createAdjuntos_ficha(@RequestBody Adjuntos_fichaDTO adjuntos_fichaDTO) throws URISyntaxException {
        log.debug("REST request to save Adjuntos_ficha : {}", adjuntos_fichaDTO);
        if (adjuntos_fichaDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjuntos_ficha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Adjuntos_fichaDTO result = adjuntos_fichaService.save(adjuntos_fichaDTO);
        return ResponseEntity.created(new URI("/api/adjuntos-fichas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adjuntos-fichas : Updates an existing adjuntos_ficha.
     *
     * @param adjuntos_fichaDTO the adjuntos_fichaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adjuntos_fichaDTO,
     * or with status 400 (Bad Request) if the adjuntos_fichaDTO is not valid,
     * or with status 500 (Internal Server Error) if the adjuntos_fichaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adjuntos-fichas")
    @Timed
    public ResponseEntity<Adjuntos_fichaDTO> updateAdjuntos_ficha(@RequestBody Adjuntos_fichaDTO adjuntos_fichaDTO) throws URISyntaxException {
        log.debug("REST request to update Adjuntos_ficha : {}", adjuntos_fichaDTO);
        if (adjuntos_fichaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Adjuntos_fichaDTO result = adjuntos_fichaService.save(adjuntos_fichaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adjuntos_fichaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adjuntos-fichas : get all the adjuntos_fichas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of adjuntos_fichas in body
     */
    @GetMapping("/adjuntos-fichas")
    @Timed
    public ResponseEntity<List<Adjuntos_fichaDTO>> getAllAdjuntos_fichas(Adjuntos_fichaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Adjuntos_fichas by criteria: {}", criteria);
        Page<Adjuntos_fichaDTO> page = adjuntos_fichaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adjuntos-fichas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adjuntos-fichas/:id : get the "id" adjuntos_ficha.
     *
     * @param id the id of the adjuntos_fichaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adjuntos_fichaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adjuntos-fichas/{id}")
    @Timed
    public ResponseEntity<Adjuntos_fichaDTO> getAdjuntos_ficha(@PathVariable Long id) {
        log.debug("REST request to get Adjuntos_ficha : {}", id);
        Optional<Adjuntos_fichaDTO> adjuntos_fichaDTO = adjuntos_fichaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjuntos_fichaDTO);
    }

    /**
     * DELETE  /adjuntos-fichas/:id : delete the "id" adjuntos_ficha.
     *
     * @param id the id of the adjuntos_fichaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adjuntos-fichas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdjuntos_ficha(@PathVariable Long id) {
        log.debug("REST request to delete Adjuntos_ficha : {}", id);
        adjuntos_fichaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // Métodos para carga / obtención de archivos

     /**
     * POST  /fichas : Create a new consulta.
     *
     * @param file el archivo a subir
     * @return the ResponseEntity with status 200 (ok) 
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adjuntos-fichas/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("fileUpload") MultipartFile archivos) throws URISyntaxException {
        String message = "";
        
		try {
            //for(MultipartFile uploadedFile : archivos){
                System.out.println("paso y ok" + archivos.getOriginalFilename());
                String nuevoNombre = adjuntos_fichaService.store(archivos);
			    files.add(nuevoNombre);
                
                message = "Se adjuntó exitosamente " + nuevoNombre + "!";
           // }
			
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "No se pudo adjuntar " + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/adjuntos-fichas/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
        
        List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(FichaResource.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
 
		return ResponseEntity.ok().body(fileNames);

        
    }
 
	@GetMapping("/adjuntos-fichas/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
       // Resource file = fichaService.loadFile(filename);
        String ruta = Paths.get("upload/Fichas"+filename).toAbsolutePath().toString();
        Resource file = adjuntos_fichaService.loadFile(ruta);
        
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getFilename())
                .body(file);
              
	}

    // FIN Métodos para carga / obtención de archivos
}
