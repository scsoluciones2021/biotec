package gestWeb.service;

import gestWeb.service.dto.FichaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing Ficha.
 */
public interface FichaService {

    /**
     * Save a ficha.
     *
     * @param fichaDTO the entity to save
     * @return the persisted entity
     */
    FichaDTO save(FichaDTO fichaDTO);

    /**
     * Get all the fichas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FichaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ficha.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FichaDTO> findOne(Long id);

    /**
     * Delete the "id" ficha.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    // Métodos para archivo
    String store(MultipartFile file, Long idFicha, Integer nroImagen);

    Path loadFile(String filename);

    void deleteAll();

	Page<FichaDTO> findAllWithFilters(String apellido, String nombre, List<String> profesionales, List<String> especialidades, Pageable pageable);

	Integer existeFichaIdPac(Long idPac);

    Long fichaRelPacProfEsp(Long idPaciente, Long idProfesional, Long idEspecialidad);

    // Fin métodos para archivo
}
