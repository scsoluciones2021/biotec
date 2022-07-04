package gestWeb.service;

import gestWeb.service.dto.AntecedentesFamiliaresDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AntecedentesFamiliares.
 */
public interface AntecedentesFamiliaresService {

    /**
     * Save a antecedentesFamiliares.
     *
     * @param antecedentesFamiliaresDTO the entity to save
     * @return the persisted entity
     */
    AntecedentesFamiliaresDTO save(AntecedentesFamiliaresDTO antecedentesFamiliaresDTO);

    /**
     * Get all the antecedentesFamiliares.
     *
     * @return the list of entities
     */
    List<AntecedentesFamiliaresDTO> findAll();


    /**
     * Get the "id" antecedentesFamiliares.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AntecedentesFamiliaresDTO> findOne(Long id);

    /**
     * Delete the "id" antecedentesFamiliares.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
