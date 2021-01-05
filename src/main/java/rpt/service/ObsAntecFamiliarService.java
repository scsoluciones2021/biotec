package rpt.service;

import rpt.service.dto.ObsAntecFamiliarDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ObsAntecFamiliar.
 */
public interface ObsAntecFamiliarService {

    /**
     * Save a obsAntecFamiliar.
     *
     * @param obsAntecFamiliarDTO the entity to save
     * @return the persisted entity
     */
    ObsAntecFamiliarDTO save(ObsAntecFamiliarDTO obsAntecFamiliarDTO);

    /**
     * Get all the obsAntecFamiliars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObsAntecFamiliarDTO> findAll(Pageable pageable);


    /**
     * Get the "id" obsAntecFamiliar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObsAntecFamiliarDTO> findOne(Long id);

    /**
     * Delete the "id" obsAntecFamiliar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
