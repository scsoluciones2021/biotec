package rpt.service.impl;

import rpt.service.TituloShortService;
import rpt.domain.TituloShort;
import rpt.repository.TituloShortRepository;
import rpt.service.dto.TituloShortDTO;
import rpt.service.mapper.TituloShortMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing TituloShort.
 */
@Service
@Transactional
public class TituloShortServiceImpl implements TituloShortService {

    private final Logger log = LoggerFactory.getLogger(TituloShortServiceImpl.class);

    private final TituloShortRepository tituloShortRepository;

    private final TituloShortMapper tituloShortMapper;

    public TituloShortServiceImpl(TituloShortRepository tituloShortRepository, TituloShortMapper tituloShortMapper) {
        this.tituloShortRepository = tituloShortRepository;
        this.tituloShortMapper = tituloShortMapper;
    }

    /**
     * Save a tituloShort.
     *
     * @param tituloShortDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TituloShortDTO save(TituloShortDTO tituloShortDTO) {
        log.debug("Request to save TituloShort : {}", tituloShortDTO);
        TituloShort tituloShort = tituloShortMapper.toEntity(tituloShortDTO);
        tituloShort = tituloShortRepository.save(tituloShort);
        return tituloShortMapper.toDto(tituloShort);
    }

    /**
     * Get all the tituloShorts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TituloShortDTO> findAll() {
        log.debug("Request to get all TituloShorts");
        return tituloShortRepository.findAll().stream()
            .map(tituloShortMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tituloShort by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TituloShortDTO> findOne(Long id) {
        log.debug("Request to get TituloShort : {}", id);
        return tituloShortRepository.findById(id)
            .map(tituloShortMapper::toDto);
    }

    /**
     * Delete the tituloShort by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TituloShort : {}", id);
        tituloShortRepository.deleteById(id);
    }
}
