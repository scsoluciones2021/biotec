package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;
import gestWeb.service.PersonalService;
import gestWeb.web.rest.errors.BadRequestAlertException;
import gestWeb.web.rest.util.HeaderUtil;
import gestWeb.service.dto.PersonalDTO;
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
 * REST controller for managing Personal.
 */
@RestController
@RequestMapping("/api")
public class PersonalResource {

    private final Logger log = LoggerFactory.getLogger(PersonalResource.class);

    private static final String ENTITY_NAME = "personal";

    private final PersonalService personalService;

    public PersonalResource(PersonalService personalService) {
        this.personalService = personalService;
    }

    /**
     * POST  /personals : Create a new personal.
     *
     * @param personalDTO the personalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalDTO, or with status 400 (Bad Request) if the personal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personals")
    @Timed
    public ResponseEntity<PersonalDTO> createPersonal(@Valid @RequestBody PersonalDTO personalDTO) throws URISyntaxException {
        log.debug("REST request to save Personal : {}", personalDTO);
        if (personalDTO.getId() != null) {
            throw new BadRequestAlertException("A new personal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalDTO result = personalService.save(personalDTO);
        return ResponseEntity.created(new URI("/api/personals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personals : Updates an existing personal.
     *
     * @param personalDTO the personalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalDTO,
     * or with status 400 (Bad Request) if the personalDTO is not valid,
     * or with status 500 (Internal Server Error) if the personalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personals")
    @Timed
    public ResponseEntity<PersonalDTO> updatePersonal(@Valid @RequestBody PersonalDTO personalDTO) throws URISyntaxException {
        log.debug("REST request to update Personal : {}", personalDTO);
        if (personalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalDTO result = personalService.save(personalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personals : get all the personals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personals in body
     */
    @GetMapping("/personals")
    @Timed
    public List<PersonalDTO> getAllPersonals() {
        log.debug("REST request to get all Personals");
        return personalService.findAll();
    }

    /**
     * GET  /personals/:id : get the "id" personal.
     *
     * @param id the id of the personalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personals/{id}")
    @Timed
    public ResponseEntity<PersonalDTO> getPersonal(@PathVariable Long id) {
        log.debug("REST request to get Personal : {}", id);
        Optional<PersonalDTO> personalDTO = personalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalDTO);
    }

    /**
     * DELETE  /personals/:id : delete the "id" personal.
     *
     * @param id the id of the personalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personals/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonal(@PathVariable Long id) {
        log.debug("REST request to delete Personal : {}", id);
        personalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
