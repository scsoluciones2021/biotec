package gestWeb.service;

import gestWeb.service.dto.RegimenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Regimen.
 */
public interface RegimenService {

    /**
     * Save a regimen.
     *
     * @param regimenDTO the entity to save
     * @return the persisted entity
     */
    RegimenDTO save(RegimenDTO regimenDTO);

    /**
     * Get all the regimen.
     *
     * @return the list of entities
     */
    List<RegimenDTO> findAll();


    /**
     * Get the "id" regimen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RegimenDTO> findOne(Long id);

    /**
     * Delete the "id" regimen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
