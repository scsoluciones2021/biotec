package gestWeb.service;

import gestWeb.service.dto.Categoriascie10DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Categoriascie10}.
 */
public interface Categoriascie10Service {

    /**
     * Save a categoriascie10.
     *
     * @param categoriascie10 the entity to save.
     * @return the persisted entity.
     */
    Categoriascie10DTO save(Categoriascie10DTO categoriascie10);

    /**
     * Get all the categoriascie10s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Categoriascie10DTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoriascie10.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Categoriascie10DTO> findOne(Long id);

    /**
     * Delete the "id" categoriascie10.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
