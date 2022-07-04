package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.ObraSocialService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.web.rest.util.PaginationUtil;
import gestWeb.service.dto.ObraSocialDTO;
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
 * REST controller for managing ObraSocial.
 */
@RestController
@RequestMapping("/api")
public class ObraSocialResource {

    private final Logger log = LoggerFactory.getLogger(ObraSocialResource.class);

    private static final String ENTITY_NAME = "obraSocial";

    private final ObraSocialService obraSocialService;

    public ObraSocialResource(ObraSocialService obraSocialService) {
        this.obraSocialService = obraSocialService;
    }

    /**
     * POST  /obra-socials : Create a new obraSocial.
     *
     * @param obraSocialDTO the obraSocialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obraSocialDTO, or with status 400 (Bad Request) if the obraSocial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obra-socials")
    @Timed
    public ResponseEntity<ObraSocialDTO> createObraSocial(@RequestBody ObraSocialDTO obraSocialDTO) throws URISyntaxException {
        log.debug("REST request to save ObraSocial : {}", obraSocialDTO);
        if (obraSocialDTO.getId() != null) {
            throw new BadRequestAlertException("A new obraSocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObraSocialDTO result = obraSocialService.save(obraSocialDTO);
        return ResponseEntity.created(new URI("/api/obra-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obra-socials : Updates an existing obraSocial.
     *
     * @param obraSocialDTO the obraSocialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obraSocialDTO,
     * or with status 400 (Bad Request) if the obraSocialDTO is not valid,
     * or with status 500 (Internal Server Error) if the obraSocialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obra-socials")
    @Timed
    public ResponseEntity<ObraSocialDTO> updateObraSocial(@RequestBody ObraSocialDTO obraSocialDTO) throws URISyntaxException {
        log.debug("REST request to update ObraSocial : {}", obraSocialDTO);
        if (obraSocialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObraSocialDTO result = obraSocialService.save(obraSocialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obraSocialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /obra-socials : get all the obraSocials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of obraSocials in body
     */
    @GetMapping("/obra-socials")
    @Timed
    public ResponseEntity<List<ObraSocialDTO>> getAllObraSocials(Pageable pageable) {
        log.debug("REST request to get a page of ObraSocials");
        Page<ObraSocialDTO> page = obraSocialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obra-socials");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /obra-socials/:id : get the "id" obraSocial.
     *
     * @param id the id of the obraSocialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obraSocialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/obra-socials/{id}")
    @Timed
    public ResponseEntity<ObraSocialDTO> getObraSocial(@PathVariable Long id) {
        log.debug("REST request to get ObraSocial : {}", id);
        Optional<ObraSocialDTO> obraSocialDTO = obraSocialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obraSocialDTO);
    }

    /**
     * DELETE  /obra-socials/:id : delete the "id" obraSocial.
     *
     * @param id the id of the obraSocialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/obra-socials/{id}")
    @Timed
    public ResponseEntity<Void> deleteObraSocial(@PathVariable Long id) {
        log.debug("REST request to delete ObraSocial : {}", id);
        obraSocialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

// Búsqueda de obras sociales desde paciente y profesionales
    @GetMapping("/_search/obrasocial")
    @Timed
    public ResponseEntity<List<ObraSocialDTO>> getObraSocialSearch(@RequestParam String nombreStr) throws InterruptedException {
      List<ObraSocialDTO> especialidad = this.obraSocialService.findObraSocial(nombreStr);

      return new ResponseEntity<List<ObraSocialDTO>>(especialidad,  HttpStatus.OK);
   }

   
   @GetMapping("/obra-socials/todas")
    @Timed
    public ResponseEntity<List<ObraSocialDTO>> todos() throws InterruptedException {
      List<ObraSocialDTO> obrasSociales = this.obraSocialService.todas();

      return new ResponseEntity<List<ObraSocialDTO>>(obrasSociales,  HttpStatus.OK);
   }

   @GetMapping("/obra-socials/busqueda-general")
    @Timed
    public ResponseEntity<List<ObraSocialDTO>> getObraSocialSearchGral(@RequestParam String nombre, Pageable pageable) throws InterruptedException {

        String nom = null;
    
        if (!nombre.isEmpty()) {
            nom = nombre;
        }

        Page<ObraSocialDTO> page = this.obraSocialService.findObraSocial(nom, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obra-social");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
