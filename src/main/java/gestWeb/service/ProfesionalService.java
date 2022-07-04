package gestWeb.service;

import gestWeb.service.dto.ProfesionalDTO;
import gestWeb.service.dto.ProfesionalTurnoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;
/**
 * Service Interface for managing Profesional.
 */
public interface ProfesionalService {

    /**
     * Save a profesional.
     *
     * @param profesionalDTO the entity to save
     * @return the persisted entity
     */
    ProfesionalDTO save(ProfesionalDTO profesionalDTO);

    /**
     * Get all the profesionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProfesionalDTO> findAll(Pageable pageable);

    /**
     * Get all the Profesional with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ProfesionalDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" profesional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProfesionalDTO> findOne(Long id);

    /**
     * Delete the "id" profesional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<ProfesionalDTO> findProfesional(String query);

    /**
     * búsqueda de profesionales en el listado
     */
    Page<ProfesionalDTO> search(String query, Pageable pageable);
    
    /**
     * búsqueda de profesionales en el listado
     */
    List<ProfesionalTurnoDTO> findAllCustom();

    /**
     *  Lista completa de profesionales, sin paginar
     */
    List<ProfesionalDTO> todos();

	Optional<ProfesionalDTO> findOneByUserId(Long id);

    Page<ProfesionalDTO> findProfesionalByApeYNom(String apellido, String nombre, Pageable pageable);

    void updateUserId(Long idProfesional, Long idUsuario);

    Boolean searchProfesionalWithoutUser(Long idProfesional);

    void updateUserIdEliminado(Long idUsuario);
}
