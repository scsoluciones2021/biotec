package rpt.service;

import rpt.service.dto.ConsultaDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing Consulta.
 */
public interface ConsultaService {

    /**
     * Save a consulta.
     *
     * @param consultaDTO the entity to save
     * @return the persisted entity
     */
    ConsultaDTO save(ConsultaDTO consultaDTO);

    /**
     * Get all the consultas.
     *
     * @return the list of entities
     */
    List<ConsultaDTO> findAll();


    /**
     * Get the "id" consulta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConsultaDTO> findOne(Long id);

    /**
     * Delete the "id" consulta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void store(MultipartFile file);

    Resource loadFile(String filename);

    void deleteAll();
}
