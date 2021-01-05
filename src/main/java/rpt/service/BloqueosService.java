package rpt.service;

import rpt.service.dto.BloqueosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Bloqueos.
 */
public interface BloqueosService {

    /**
     * Save a bloqueos.
     *
     * @param bloqueosDTO the entity to save
     * @return the persisted entity
     */
    BloqueosDTO save(BloqueosDTO bloqueosDTO);

    /**
     * Get all the bloqueos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BloqueosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bloqueos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BloqueosDTO> findOne(Long id);

    /**
     * Delete the "id" bloqueos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
