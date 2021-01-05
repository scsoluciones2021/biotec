package rpt.service;

import rpt.service.dto.DiagnosticoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Diagnostico.
 */
public interface DiagnosticoService {

    /**
     * Save a diagnostico.
     *
     * @param diagnosticoDTO the entity to save
     * @return the persisted entity
     */
    DiagnosticoDTO save(DiagnosticoDTO diagnosticoDTO);

    /**
     * Get all the diagnosticos.
     *
     * @return the list of entities
     */
    List<DiagnosticoDTO> findAll();


    /**
     * Get the "id" diagnostico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiagnosticoDTO> findOne(Long id);

    /**
     * Delete the "id" diagnostico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
