package gestWeb.service;

import gestWeb.service.dto.IntoleranciaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Intolerancia.
 */
public interface IntoleranciaService {

    /**
     * Save a intolerancia.
     *
     * @param intoleranciaDTO the entity to save
     * @return the persisted entity
     */
    IntoleranciaDTO save(IntoleranciaDTO intoleranciaDTO);

    /**
     * Get all the intolerancias.
     *
     * @return the list of entities
     */
    List<IntoleranciaDTO> findAll();


    /**
     * Get the "id" intolerancia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IntoleranciaDTO> findOne(Long id);

    /**
     * Delete the "id" intolerancia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
