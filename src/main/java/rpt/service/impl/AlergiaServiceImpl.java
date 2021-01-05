package rpt.service.impl;

import rpt.service.AlergiaService;
import rpt.domain.Alergia;
import rpt.repository.AlergiaRepository;
import rpt.service.dto.AlergiaDTO;
import rpt.service.mapper.AlergiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Alergia.
 */
@Service
@Transactional
public class AlergiaServiceImpl implements AlergiaService {

    private final Logger log = LoggerFactory.getLogger(AlergiaServiceImpl.class);

    private final AlergiaRepository alergiaRepository;

    private final AlergiaMapper alergiaMapper;

    public AlergiaServiceImpl(AlergiaRepository alergiaRepository, AlergiaMapper alergiaMapper) {
        this.alergiaRepository = alergiaRepository;
        this.alergiaMapper = alergiaMapper;
    }

    /**
     * Save a alergia.
     *
     * @param alergiaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlergiaDTO save(AlergiaDTO alergiaDTO) {
        log.debug("Request to save Alergia : {}", alergiaDTO);
        Alergia alergia = alergiaMapper.toEntity(alergiaDTO);
        alergia = alergiaRepository.save(alergia);
        return alergiaMapper.toDto(alergia);
    }

    /**
     * Get all the alergias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlergiaDTO> findAll() {
        log.debug("Request to get all Alergias");
        return alergiaRepository.findAll().stream()
            .map(alergiaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one alergia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlergiaDTO> findOne(Long id) {
        log.debug("Request to get Alergia : {}", id);
        return alergiaRepository.findById(id)
            .map(alergiaMapper::toDto);
    }

    /**
     * Delete the alergia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alergia : {}", id);
        alergiaRepository.deleteById(id);
    }
}
