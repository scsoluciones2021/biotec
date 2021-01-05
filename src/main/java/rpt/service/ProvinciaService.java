package rpt.service;

import rpt.service.dto.ProvinciaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Provincia.
 */
public interface ProvinciaService {

    /**
     * Save a provincia.
     *
     * @param provinciaDTO the entity to save
     * @return the persisted entity
     */
    ProvinciaDTO save(ProvinciaDTO provinciaDTO);

    /**
     * Get all the provincias.
     *
     * @return the list of entities
     */
    List<ProvinciaDTO> findAll();


    /**
     * Get the "id" provincia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProvinciaDTO> findOne(Long id);

    /**
     * Delete the "id" provincia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
