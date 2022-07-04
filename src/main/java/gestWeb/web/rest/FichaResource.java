package gestWeb.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.github.jhipster.web.util.ResponseUtil;
import gestWeb.service.FichaService;
import gestWeb.service.Adjuntos_fichaService;
import gestWeb.service.dto.Adjuntos_fichaDTO;
import gestWeb.service.dto.FichaDTO;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Ficha.
 */
//@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/api")
public class FichaResource {

    private final Logger log = LoggerFactory.getLogger(FichaResource.class);

    private static final String ENTITY_NAME = "ficha";

    private final FichaService fichaService;
    private final Adjuntos_fichaService adjFichService;

    List<String> files = new ArrayList<String>();

    public FichaResource(FichaService fichaService, Adjuntos_fichaService adjFS) {
        this.fichaService = fichaService;
        this.adjFichService = adjFS;
    }

    /**
     * POST /fichas : Create a new ficha.
     *
     * @param fichaDTO the fichaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         fichaDTO, or with status 400 (Bad Request) if the ficha has already
     *         an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichas")
    @Timed
    public ResponseEntity<FichaDTO> createFicha(@RequestBody FichaDTO fichaDTO) throws URISyntaxException {
        log.debug("REST request to save Ficha : {}", fichaDTO);
        if (fichaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ficha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaDTO result = fichaService.save(fichaDTO);
        return ResponseEntity.created(new URI("/api/fichas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /fichas : Updates an existing ficha.
     *
     * @param fichaDTO the fichaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         fichaDTO, or with status 400 (Bad Request) if the fichaDTO is not
     *         valid, or with status 500 (Internal Server Error) if the fichaDTO
     *         couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichas")
    @Timed
    public ResponseEntity<FichaDTO> updateFicha(@RequestBody FichaDTO fichaDTO) throws URISyntaxException {
        log.debug("REST request to update Ficha : {}", fichaDTO);
        if (fichaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaDTO result = fichaService.save(fichaDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichaDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /fichas : get all the fichas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fichas in
     *         body
     */
    @GetMapping("/fichas")
    @Timed
    public ResponseEntity<List<FichaDTO>> getAllFichas(Pageable pageable) {
        log.debug("REST request to get a page of Fichas");
        Page<FichaDTO> page = fichaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /fichas/:id : get the "id" ficha.
     *
     * @param id the id of the fichaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichaDTO,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/fichas/{id}")
    @Timed
    public ResponseEntity<FichaDTO> getFicha(@PathVariable Long id) {
        log.debug("REST request to get Ficha : {}", id);
        Optional<FichaDTO> fichaDTO = fichaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaDTO);
    }

    /**
     * DELETE /fichas/:id : delete the "id" ficha.
     *
     * @param id the id of the fichaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFicha(@PathVariable Long id) {
        log.debug("REST request to delete Ficha : {}", id);
        fichaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // Métodos para carga / obtención de archivos

    /**
     * POST /fichas : Create a new consulta.
     *
     * @param file el archivo a subir
     * @return the ResponseEntity with status 200 (ok)
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichas/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("fileUpload") MultipartFile[] archivos, String idFicha)
            throws URISyntaxException {
        String message = "Se adjuntó exitosamente:";
        try {

            for (MultipartFile file : archivos) {
                // Sincronizamos este bloque del método para que le de tiempo a leer el nº de
                // imagen guardado en la base antes que quiera guardar el siguiente.
                synchronized (this) {
                    String nuevoNombre = null;
                    Integer nroImagen = this.adjFichService.getNro(Long.parseLong(idFicha));
                    System.out.println("Número im: " + nroImagen);
                    FichaDTO ficha = this.fichaService.findOne(Long.parseLong(idFicha)).get();
                    nuevoNombre = fichaService.store(file, ficha.getId(), nroImagen);
                    files.add(nuevoNombre);
                    // Guardamos en la base los datos del archivo adjunto
                    Adjuntos_fichaDTO adjuntos_fichaDTO = new Adjuntos_fichaDTO();
                    // adjuntos_fichaDTO.setFecha(fecha);
                    adjuntos_fichaDTO.setIdFicha(ficha.getId());
                    // adjuntos_fichaDTO.setIdPaciente(ficha.getPaciente().getId());
                    // adjuntos_fichaDTO.setIdProfesional(ficha.getProfesional().getId());
                    adjuntos_fichaDTO.setNombreOriginal(file.getOriginalFilename());
                    adjuntos_fichaDTO.setNombreActual(nuevoNombre);
                    adjuntos_fichaDTO.setRuta(nuevoNombre);
                    adjuntos_fichaDTO.setUsuario(nroImagen);
                    this.adjFichService.save(adjuntos_fichaDTO);
                    message += " " + nuevoNombre;
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "No se pudo adjuntar " + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/fichas/getallfiles/{id}")
    public ResponseEntity<String[]> getListFiles(@PathVariable Long id) {

        String[] fileNames = this.adjFichService.obtenerArchivosFichaConsulta(id);
        return ResponseEntity.ok().body(fileNames);
    }
       
    // FIN Métodos para carga / obtención de archivos

    @GetMapping("/fichas/filtros")
    @Timed
    public ResponseEntity<List<FichaDTO>> getAllFichasWithFilters(@RequestParam String apellido,@RequestParam String nombre,@RequestParam List<String> profesionales, @RequestParam List<String> especialidades , Pageable pageable) {
        Page<FichaDTO> page = fichaService.findAllWithFilters(apellido, nombre, profesionales, especialidades, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichas/filtros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/fichas/busquedaXIdPac/{idPac}")
    @Timed
    public Integer existeFichaIdPac(@PathVariable Long idPac) {
        log.debug("REST existeFichaIdPac : {}", idPac);
        return fichaService.existeFichaIdPac(idPac);
    }
}
