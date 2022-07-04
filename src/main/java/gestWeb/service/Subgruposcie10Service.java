package gestWeb.service;

import gestWeb.service.dto.Subgruposcie10DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Subgruposcie10}.
 */
public interface Subgruposcie10Service {

    /**
     * Save a subgruposcie10.
     *
     * @param subgruposcie10 the entity to save.
     * @return the persisted entity.
     */
    Subgruposcie10DTO save(Subgruposcie10DTO subgruposcie10);

    /**
     * Get all the subgruposcie10s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Subgruposcie10DTO> findAll(Pageable pageable);


    /**
     * Get the "id" subgruposcie10.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Subgruposcie10DTO> findOne(Long id);

    /**
     * Delete the "id" subgruposcie10.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
