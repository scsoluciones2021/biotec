package rpt.service;

import rpt.service.dto.PacienteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing Paciente.
 */
public interface PacienteService {

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save
     * @return the persisted entity
     */
    PacienteDTO save(PacienteDTO pacienteDTO);

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PacienteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paciente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PacienteDTO> findOne(Long id);

    /**
     * Delete the "id" paciente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PacienteDTO> searchPaciente(String query, Pageable pageable);
    
    List<PacienteDTO> findPaciente(String query);

	Page<PacienteDTO> findPacientes(String apellido, String nombre, String dni, Pageable pageable);

	Optional<PacienteDTO> findPacienteXDni(String dni);
}
