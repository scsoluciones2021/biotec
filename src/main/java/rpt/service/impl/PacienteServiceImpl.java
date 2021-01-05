package rpt.service.impl;

import rpt.service.PacienteService;
import rpt.domain.Paciente;
import rpt.repository.PacienteRepository;
import rpt.service.dto.PacienteDTO;
import rpt.service.mapper.PacienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Paciente.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        log.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(paciente);
    }

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable)
            .map(pacienteMapper::toDto);
    }


    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id)
            .map(pacienteMapper::toDto);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
    }

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query the query of the search     
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PacienteDTO> searchPaciente(String query, Pageable pageable) {
        log.debug("Request to search for a page of pacientes for query {}", query);
        return pacienteRepository.findByNombreApellidoContaining(query, pageable)
            .map(pacienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> findPaciente(String nombreStr) {
      List<PacienteDTO> result = this.pacienteRepository.findByNyA(nombreStr).stream()
         .map(pacienteMapper::toDto).collect(Collectors.toList());
      return result;
   }

    @Override
    public Page<PacienteDTO> findPacientes(String apellido, String nombre, String dni, Pageable pageable) {
        Page<PacienteDTO> result = this.pacienteRepository.findPacientes(apellido, nombre, dni, pageable).map(pacienteMapper::toDto);
      return result;
    }

    @Override
    public Optional<PacienteDTO> findPacienteXDni(String dni) {
        return this.pacienteRepository.findByDni(dni).map(pacienteMapper::toDto);
    }

}
