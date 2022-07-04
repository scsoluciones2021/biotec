package gestWeb.service;

import gestWeb.service.dto.EstadosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Estados.
 */
public interface EstadosService {

    /**
     * Save a estados.
     *
     * @param estadosDTO the entity to save
     * @return the persisted entity
     */
    EstadosDTO save(EstadosDTO estadosDTO);

    /**
     * Get all the estados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstadosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estados.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstadosDTO> findOne(Long id);

    /**
     * Delete the "id" estados.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
