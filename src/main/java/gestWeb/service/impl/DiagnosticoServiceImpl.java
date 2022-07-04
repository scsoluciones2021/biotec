package gestWeb.service.impl;

import gestWeb.service.DiagnosticoService;
import gestWeb.domain.Diagnostico;
import gestWeb.repository.DiagnosticoRepository;
import gestWeb.service.dto.DiagnosticoDTO;
import gestWeb.service.mapper.DiagnosticoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Diagnostico.
 */
@Service
@Transactional
public class DiagnosticoServiceImpl implements DiagnosticoService {

    private final Logger log = LoggerFactory.getLogger(DiagnosticoServiceImpl.class);

    private final DiagnosticoRepository diagnosticoRepository;

    private final DiagnosticoMapper diagnosticoMapper;

    public DiagnosticoServiceImpl(DiagnosticoRepository diagnosticoRepository, DiagnosticoMapper diagnosticoMapper) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.diagnosticoMapper = diagnosticoMapper;
    }

    /**
     * Save a diagnostico.
     *
     * @param diagnosticoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiagnosticoDTO save(DiagnosticoDTO diagnosticoDTO) {
        log.debug("Request to save Diagnostico : {}", diagnosticoDTO);
        Diagnostico diagnostico = diagnosticoMapper.toEntity(diagnosticoDTO);
        diagnostico = diagnosticoRepository.save(diagnostico);
        return diagnosticoMapper.toDto(diagnostico);
    }

    /**
     * Get all the diagnosticos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DiagnosticoDTO> findAll() {
        log.debug("Request to get all Diagnosticos");
        return diagnosticoRepository.findAll().stream()
            .map(diagnosticoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one diagnostico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnosticoDTO> findOne(Long id) {
        log.debug("Request to get Diagnostico : {}", id);
        return diagnosticoRepository.findById(id)
            .map(diagnosticoMapper::toDto);
    }

    /**
     * Delete the diagnostico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diagnostico : {}", id);
        diagnosticoRepository.deleteById(id);
    }
}
