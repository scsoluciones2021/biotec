package rpt.service;

import rpt.service.dto.EspecialidadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Especialidad.
 */
public interface EspecialidadService {

    /**
     * Save a especialidad.
     *
     * @param especialidadDTO the entity to save
     * @return the persisted entity
     */
    EspecialidadDTO save(EspecialidadDTO especialidadDTO);

    /**
     * Get all the especialidads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EspecialidadDTO> findAll(Pageable pageable);


    /**
     * Get the "id" especialidad.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EspecialidadDTO> findOne(Long id);

    /**
     * Delete the "id" especialidad.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the codigoPostal corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EspecialidadDTO> search(String query, Pageable pageable);

    List<EspecialidadDTO> findEspecialidad(String query);

	List<EspecialidadDTO> espeProfesional(String idProf);
    
}
