package gestWeb.service;

import gestWeb.service.dto.Gruposcie10DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Gruposcie10}.
 */
public interface Gruposcie10Service {

    /**
     * Save a gruposcie10.
     *
     * @param gruposcie10 the entity to save.
     * @return the persisted entity.
     */
    Gruposcie10DTO save(Gruposcie10DTO gruposcie10);

    /**
     * Get all the gruposcie10s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Gruposcie10DTO> findAll(Pageable pageable);


    /**
     * Get the "id" gruposcie10.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Gruposcie10DTO> findOne(Long id);

    /**
     * Delete the "id" gruposcie10.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
