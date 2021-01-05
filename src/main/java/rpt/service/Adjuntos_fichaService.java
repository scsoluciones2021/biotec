package rpt.service;

import rpt.service.dto.Adjuntos_fichaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

/**
 * Service Interface for managing Adjuntos_ficha.
 */
public interface Adjuntos_fichaService {

    /**
     * Save a adjuntos_ficha.
     *
     * @param adjuntos_fichaDTO the entity to save
     * @return the persisted entity
     */
    Adjuntos_fichaDTO save(Adjuntos_fichaDTO adjuntos_fichaDTO);

    /**
     * Get all the adjuntos_fichas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Adjuntos_fichaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adjuntos_ficha.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Adjuntos_fichaDTO> findOne(Long id);

    /**
     * Delete the "id" adjuntos_ficha.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

        // Métodos para archivo
        String store(MultipartFile file);

        Resource loadFile(String filename);
    
        void deleteAll();

        Integer getNro(Long ficha);

        Integer incrementNroImagen(Long ficha);
    
        List<String> obtenerArchivosFichaConsulta(Long idFicha);
        // Fin métodos para archivo
}
