package gestWeb.service;

import gestWeb.service.dto.ObsAntecPersonalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ObsAntecPersonal.
 */
public interface ObsAntecPersonalService {

    /**
     * Save a obsAntecPersonal.
     *
     * @param obsAntecPersonalDTO the entity to save
     * @return the persisted entity
     */
    ObsAntecPersonalDTO save(ObsAntecPersonalDTO obsAntecPersonalDTO);

    /**
     * Get all the obsAntecPersonals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObsAntecPersonalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" obsAntecPersonal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObsAntecPersonalDTO> findOne(Long id);

    /**
     * Delete the "id" obsAntecPersonal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
