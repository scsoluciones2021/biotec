package rpt.service;

import rpt.service.dto.DetalleHorariosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DetalleHorarios.
 */
public interface DetalleHorariosService {

    /**
     * Save a detalleHorarios.
     *
     * @param detalleHorariosDTO the entity to save
     * @return the persisted entity
     */
    DetalleHorariosDTO save(DetalleHorariosDTO detalleHorariosDTO);

    /**
     * Get all the detalleHorarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleHorariosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detalleHorarios.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DetalleHorariosDTO> findOne(Long id);

    /**
     * Delete the "id" detalleHorarios.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the detalleHorarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleHorariosDTO> buscarXHorario(Long idHorario, Pageable pageable);
}
