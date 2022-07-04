package gestWeb.service.impl;

import gestWeb.service.HorariosProfesionalService;
import gestWeb.domain.HorariosProfesional;
import gestWeb.repository.HorariosProfesionalRepository;
import gestWeb.service.dto.HorariosProfesionalDTO;
import gestWeb.service.mapper.HorariosProfesionalMapper;
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
 * Service Implementation for managing HorariosProfesional.
 */
@Service
@Transactional
public class HorariosProfesionalServiceImpl implements HorariosProfesionalService {

    private final Logger log = LoggerFactory.getLogger(HorariosProfesionalServiceImpl.class);

    private final HorariosProfesionalRepository horariosProfesionalRepository;

    private final HorariosProfesionalMapper horariosProfesionalMapper;

    public HorariosProfesionalServiceImpl(HorariosProfesionalRepository horariosProfesionalRepository,
            HorariosProfesionalMapper horariosProfesionalMapper) {
        this.horariosProfesionalRepository = horariosProfesionalRepository;
        this.horariosProfesionalMapper = horariosProfesionalMapper;
    }

    /**
     * Save a horariosProfesional.
     *
     * @param horariosProfesionalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HorariosProfesionalDTO save(HorariosProfesionalDTO horariosProfesionalDTO) {
        log.debug("Request to save HorariosProfesional : {}", horariosProfesionalDTO);
        HorariosProfesional horariosProfesional = horariosProfesionalMapper.toEntity(horariosProfesionalDTO);
        horariosProfesional = horariosProfesionalRepository.save(horariosProfesional);
        return horariosProfesionalMapper.toDto(horariosProfesional);
    }

    /**
     * Get all the horariosProfesionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HorariosProfesionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HorariosProfesionals");
        return horariosProfesionalRepository.findAll(pageable).map(horariosProfesionalMapper::toDto);
    }

    /**
     * Get all the HorariosProfesional with eager load of many-to-many
     * relationships.
     *
     * @return the list of entities
     */
    public Page<HorariosProfesionalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return horariosProfesionalRepository.findAllWithEagerRelationships(pageable)
                .map(horariosProfesionalMapper::toDto);
    }

    /**
     * Get one horariosProfesional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HorariosProfesionalDTO> findOne(Long id) {
        log.debug("Request to get HorariosProfesional : {}", id);
        return horariosProfesionalRepository.findOneWithEagerRelationships(id).map(horariosProfesionalMapper::toDto);
    }

    /**
     * Delete the horariosProfesional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HorariosProfesional : {}", id);
        horariosProfesionalRepository.deleteById(id);
    }

    @Override
    public List<HorariosProfesionalDTO> findXProf(Long idProfesional, Long idEspecialidad) {
        return horariosProfesionalRepository.findXProf(idProfesional, idEspecialidad).stream()
            .map(horariosProfesionalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<HorariosProfesionalDTO> findHorariosProfesional(String nombre, String especialidad, Pageable pageable) {
        Page<HorariosProfesionalDTO> result = this.horariosProfesionalRepository.findHorariosProfesional(nombre, especialidad, pageable).map(horariosProfesionalMapper::toDto);
      return result;
    }
}
