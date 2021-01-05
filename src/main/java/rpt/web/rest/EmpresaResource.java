package rpt.web.rest;

import com.codahale.metrics.annotation.Timed;
import rpt.service.EmpresaService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.web.rest.util.HeaderUtil;
import rpt.service.dto.EmpresaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Empresa.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";

    private final EmpresaService empresaService;

    public EmpresaResource(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * POST  /empresas : Create a new empresa.
     *
     * @param empresaDTO the empresaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empresaDTO, or with status 400 (Bad Request) if the empresa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empresas")
    @Timed
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresaDTO);
        if (empresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empresas : Updates an existing empresa.
     *
     * @param empresaDTO the empresaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empresaDTO,
     * or with status 400 (Bad Request) if the empresaDTO is not valid,
     * or with status 500 (Internal Server Error) if the empresaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empresas")
    @Timed
    public ResponseEntity<EmpresaDTO> updateEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresaDTO);
        if (empresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empresas : get all the empresas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empresas in body
     */
    @GetMapping("/empresas")
    @Timed
    public List<EmpresaDTO> getAllEmpresas() {
        log.debug("REST request to get all Empresas");
        return empresaService.findAll();
    }

    /**
     * GET  /empresas/:id : get the "id" empresa.
     *
     * @param id the id of the empresaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empresaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/empresas/{id}")
    @Timed
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<EmpresaDTO> empresaDTO = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     * DELETE  /empresas/:id : delete the "id" empresa.
     *
     * @param id the id of the empresaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empresas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
