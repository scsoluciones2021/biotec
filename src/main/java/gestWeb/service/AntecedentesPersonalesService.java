package gestWeb.service;

import gestWeb.service.dto.AntecedentesPersonalesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AntecedentesPersonales.
 */
public interface AntecedentesPersonalesService {

    /**
     * Save a antecedentesPersonales.
     *
     * @param antecedentesPersonalesDTO the entity to save
     * @return the persisted entity
     */
    AntecedentesPersonalesDTO save(AntecedentesPersonalesDTO antecedentesPersonalesDTO);

    /**
     * Get all the antecedentesPersonales.
     *
     * @return the list of entities
     */
    List<AntecedentesPersonalesDTO> findAll();

    /**
     * Get all the AntecedentesPersonales with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<AntecedentesPersonalesDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" antecedentesPersonales.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AntecedentesPersonalesDTO> findOne(Long id);

    /**
     * Delete the "id" antecedentesPersonales.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
