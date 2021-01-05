package rpt.service;

import rpt.service.dto.ConstantesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Constantes.
 */
public interface ConstantesService {

    /**
     * Save a constantes.
     *
     * @param constantesDTO the entity to save
     * @return the persisted entity
     */
    ConstantesDTO save(ConstantesDTO constantesDTO);

    /**
     * Get all the constantes.
     *
     * @return the list of entities
     */
    List<ConstantesDTO> findAll();


    /**
     * Get the "id" constantes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConstantesDTO> findOne(Long id);

    /**
     * Delete the "id" constantes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
