package gestWeb.service;

import gestWeb.service.dto.AlergiaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Alergia.
 */
public interface AlergiaService {

    /**
     * Save a alergia.
     *
     * @param alergiaDTO the entity to save
     * @return the persisted entity
     */
    AlergiaDTO save(AlergiaDTO alergiaDTO);

    /**
     * Get all the alergias.
     *
     * @return the list of entities
     */
    List<AlergiaDTO> findAll();


    /**
     * Get the "id" alergia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AlergiaDTO> findOne(Long id);

    /**
     * Delete the "id" alergia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
