package gestWeb.service.impl;

import gestWeb.service.EspecialidadService;
import gestWeb.domain.Especialidad;
import gestWeb.repository.EspecialidadRepository;
import gestWeb.service.dto.EspecialidadDTO;
import gestWeb.service.mapper.EspecialidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Especialidad.
 */
@Service
@Transactional
public class EspecialidadServiceImpl implements EspecialidadService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadServiceImpl.class);

    private final EspecialidadRepository especialidadRepository;

    private final EspecialidadMapper especialidadMapper;

    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository, EspecialidadMapper especialidadMapper) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadMapper = especialidadMapper;
    }

    /**
     * Save a especialidad.
     *
     * @param especialidadDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EspecialidadDTO save(EspecialidadDTO especialidadDTO) {
        log.debug("Request to save Especialidad : {}", especialidadDTO);
        Especialidad especialidad = especialidadMapper.toEntity(especialidadDTO);
        especialidad = especialidadRepository.save(especialidad);
        return especialidadMapper.toDto(especialidad);
    }

    /**
     * Get all the especialidads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidads");
        return especialidadRepository.findAll(pageable)
            .map(especialidadMapper::toDto);
    }


    /**
     * Get one especialidad by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialidadDTO> findOne(Long id) {
        log.debug("Request to get Especialidad : {}", id);
        return especialidadRepository.findById(id)
            .map(especialidadMapper::toDto);
    }

    /**
     * Delete the especialidad by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especialidad : {}", id);
        especialidadRepository.deleteById(id);
    }

    /**
     * Search for the especialidad corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CodigoPostals for query {}", query);
        return especialidadRepository.findByNombreContaining(query, pageable)
            .map(especialidadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadDTO> findEspecialidad(String nombreStr) {

      List<EspecialidadDTO> result = this.especialidadRepository.findByNombreCodigo(nombreStr).stream()
         .map(especialidadMapper::toDto).collect(Collectors.toList());
      return result;

    }

    @Override
    public List<EspecialidadDTO> espeProfesional(String idProf) {
        List<EspecialidadDTO> result = this.especialidadRepository.espeProfesional(Long.parseLong(idProf)).stream()
        .map(especialidadMapper::toDto).collect(Collectors.toList());
     return result;
    }
}
