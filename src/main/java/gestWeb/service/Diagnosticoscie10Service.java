package gestWeb.service;

import gestWeb.service.dto.Diagnosticoscie10DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Diagnosticoscie10}.
 */
public interface Diagnosticoscie10Service {

    /**
     * Save a diagnosticoscie10.
     *
     * @param diagnosticoscie10 the entity to save.
     * @return the persisted entity.
     */
    Diagnosticoscie10DTO save(Diagnosticoscie10DTO diagnosticoscie10);

    /**
     * Get all the diagnosticoscie10s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Diagnosticoscie10DTO> findAll(Pageable pageable);


    /**
     * Get the "id" diagnosticoscie10.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Diagnosticoscie10DTO> findOne(Long id);

    /**
     * Delete the "id" diagnosticoscie10.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
