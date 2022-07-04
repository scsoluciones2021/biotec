package gestWeb.service;

import gestWeb.service.dto.FamiliarDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Familiar.
 */
public interface FamiliarService {

    /**
     * Save a familiar.
     *
     * @param familiarDTO the entity to save
     * @return the persisted entity
     */
    FamiliarDTO save(FamiliarDTO familiarDTO);

    /**
     * Get all the familiars.
     *
     * @return the list of entities
     */
    List<FamiliarDTO> findAll();


    /**
     * Get the "id" familiar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FamiliarDTO> findOne(Long id);

    /**
     * Delete the "id" familiar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
