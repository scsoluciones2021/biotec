package rpt.web.rest;

import rpt.service.PagoConsultaService;
import rpt.web.rest.errors.BadRequestAlertException;
import rpt.service.dto.PagoConsultaDTO;

import rpt.web.rest.util.HeaderUtil;
import rpt.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.ResponseUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing {@link rpt.domain.PagoConsulta}.
 */
@RestController
@RequestMapping("/api")
public class PagoConsultaResource {

    private final Logger log = LoggerFactory.getLogger(PagoConsultaResource.class);

    private static final String ENTITY_NAME = "pagoConsulta";

   // @Value("${jhipster.clientApp.name}")
    // private String applicationName;

    private final PagoConsultaService pagoConsultaService;

    public PagoConsultaResource(PagoConsultaService pagoConsultaService) {
        this.pagoConsultaService = pagoConsultaService;
    }

    /**
     * {@code POST  /pago-consultas} : Create a new pagoConsulta.
     *
     * @param pagoConsultaDTO the pagoConsultaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pagoConsultaDTO, or with status {@code 400 (Bad Request)} if the pagoConsulta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pago-consultas")
    public ResponseEntity<PagoConsultaDTO> createPagoConsulta(@RequestBody PagoConsultaDTO pagoConsultaDTO) throws URISyntaxException {
        log.debug("REST request to save PagoConsulta : {}", pagoConsultaDTO);
        if (pagoConsultaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagoConsulta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagoConsultaDTO result = pagoConsultaService.save(pagoConsultaDTO);
        return ResponseEntity.created(new URI("/api/pago-consultas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pago-consultas} : Updates an existing pagoConsulta.
     *
     * @param pagoConsultaDTO the pagoConsultaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pagoConsultaDTO,
     * or with status {@code 400 (Bad Request)} if the pagoConsultaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pagoConsultaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pago-consultas")
    public ResponseEntity<PagoConsultaDTO> updatePagoConsulta(@RequestBody PagoConsultaDTO pagoConsultaDTO) throws URISyntaxException {
        log.debug("REST request to update PagoConsulta : {}", pagoConsultaDTO);
        if (pagoConsultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PagoConsultaDTO result = pagoConsultaService.save(pagoConsultaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pagoConsultaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pago-consultas} : get all the pagoConsultas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagoConsultas in body.
     */
    @GetMapping("/pago-consultas")
    public ResponseEntity<List<PagoConsultaDTO>> getAllPagoConsultas(Pageable pageable) {
        log.debug("REST request to get a page of PagoConsultas");
        Page<PagoConsultaDTO> page = pagoConsultaService.findAll(pageable);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, ServletUriComponentsBuilder.fromCurrentRequest());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/pagoconsulta"));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pago-consultas/:id} : get the "id" pagoConsulta.
     *
     * @param id the id of the pagoConsultaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagoConsultaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pago-consultas/{id}")
    public ResponseEntity<PagoConsultaDTO> getPagoConsulta(@PathVariable Long id) {
        log.debug("REST request to get PagoConsulta : {}", id);
        Optional<PagoConsultaDTO> pagoConsultaDTO = pagoConsultaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagoConsultaDTO);
    }

    /**
     * {@code DELETE  /pago-consultas/:id} : delete the "id" pagoConsulta.
     *
     * @param id the id of the pagoConsultaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pago-consultas/{id}")
    public ResponseEntity<Void> deletePagoConsulta(@PathVariable Long id) {
        log.debug("REST request to delete PagoConsulta : {}", id);
        pagoConsultaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/pago-consultas/filtros")
    @Timed
    public ResponseEntity<List<PagoConsultaDTO>> filtrosImpresion(@RequestParam String[] query, @RequestParam List<String> espSel, @RequestParam List<String> profSel, Pageable pageable) {
        List<PagoConsultaDTO> lista = pagoConsultaService.busqueda_filtros_impresion(query, espSel, profSel);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
