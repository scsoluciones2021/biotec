package rpt.service;

import rpt.service.dto.ObraSocialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ObraSocial.
 */
public interface ObraSocialService {

    /**
     * Save a obraSocial.
     *
     * @param obraSocialDTO the entity to save
     * @return the persisted entity
     */
    ObraSocialDTO save(ObraSocialDTO obraSocialDTO);

    /**
     * Get all the obraSocials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObraSocialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" obraSocial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObraSocialDTO> findOne(Long id);

    /**
     * Delete the "id" obraSocial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<ObraSocialDTO> findObraSocial(String nombreStr);

        /**
     *  Lista completa de obras sociales, sin paginar
     */
    List<ObraSocialDTO> todas();
}
