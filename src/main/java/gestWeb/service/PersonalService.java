package gestWeb.service;

import gestWeb.service.dto.PersonalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Personal.
 */
public interface PersonalService {

    /**
     * Save a personal.
     *
     * @param personalDTO the entity to save
     * @return the persisted entity
     */
    PersonalDTO save(PersonalDTO personalDTO);

    /**
     * Get all the personals.
     *
     * @return the list of entities
     */
    List<PersonalDTO> findAll();


    /**
     * Get the "id" personal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonalDTO> findOne(Long id);

    /**
     * Delete the "id" personal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
