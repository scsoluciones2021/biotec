package rpt.service;

import rpt.service.dto.AgrupadorOSDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AgrupadorOS.
 */
public interface AgrupadorOSService {

    /**
     * Save a agrupadorOS.
     *
     * @param agrupadorOSDTO the entity to save
     * @return the persisted entity
     */
    AgrupadorOSDTO save(AgrupadorOSDTO agrupadorOSDTO);

    /**
     * Get all the agrupadorOS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgrupadorOSDTO> findAll(Pageable pageable);


    /**
     * Get the "id" agrupadorOS.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AgrupadorOSDTO> findOne(Long id);

    /**
     * Delete the "id" agrupadorOS.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
