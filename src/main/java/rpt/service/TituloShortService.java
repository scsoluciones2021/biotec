package rpt.service;

import rpt.service.dto.TituloShortDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TituloShort.
 */
public interface TituloShortService {

    /**
     * Save a tituloShort.
     *
     * @param tituloShortDTO the entity to save
     * @return the persisted entity
     */
    TituloShortDTO save(TituloShortDTO tituloShortDTO);

    /**
     * Get all the tituloShorts.
     *
     * @return the list of entities
     */
    List<TituloShortDTO> findAll();


    /**
     * Get the "id" tituloShort.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TituloShortDTO> findOne(Long id);

    /**
     * Delete the "id" tituloShort.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
