package rpt.service;

import rpt.service.dto.EnfermedadDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Enfermedad.
 */
public interface EnfermedadService {

    /**
     * Save a enfermedad.
     *
     * @param enfermedadDTO the entity to save
     * @return the persisted entity
     */
    EnfermedadDTO save(EnfermedadDTO enfermedadDTO);

    /**
     * Get all the enfermedads.
     *
     * @return the list of entities
     */
    List<EnfermedadDTO> findAll();


    /**
     * Get the "id" enfermedad.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EnfermedadDTO> findOne(Long id);

    /**
     * Delete the "id" enfermedad.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
