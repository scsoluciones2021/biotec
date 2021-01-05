package rpt.service;

import rpt.service.dto.TurnoDTO;
import rpt.service.dto.TurnoProfDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Turno.
 */
public interface TurnoService {

    /**
     * Save a turno.
     *
     * @param turnoDTO the entity to save
     * @return the persisted entity
     */
    TurnoDTO save(TurnoDTO turnoDTO);

    /**
     * Get all the turnos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TurnoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" turno.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TurnoDTO> findOne(Long id);

    void cambiarEstado(Long id, Integer estado);

    List<String> horariosOcupadosProf(Long idProf, Long idEsp, String dia);
    
	Page<TurnoDTO> busqueda_filtros(String[] query, List<String> espSel, List<String> profSel, Pageable pageable);
    
    List<TurnoDTO> busqueda_filtros_impresion(String[] query, List<String> espSel, List<String> profSel);

    Page<TurnoDTO> busqueda_medico(TurnoProfDTO tp, Pageable pageable);
}
