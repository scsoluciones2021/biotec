package rpt.service;

import rpt.service.dto.HorariosProfesionalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing HorariosProfesional.
 */
public interface HorariosProfesionalService {

    /**
     * Save a horariosProfesional.
     *
     * @param horariosProfesionalDTO the entity to save
     * @return the persisted entity
     */
    HorariosProfesionalDTO save(HorariosProfesionalDTO horariosProfesionalDTO);

    /**
     * Get all the horariosProfesionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HorariosProfesionalDTO> findAll(Pageable pageable);

    /**
     * Get all the HorariosProfesional with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<HorariosProfesionalDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" horariosProfesional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HorariosProfesionalDTO> findOne(Long id);

    /**
     * Delete the "id" horariosProfesional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	List<HorariosProfesionalDTO> findXProf(Long idProfesional, Long idEspecialida);
}
