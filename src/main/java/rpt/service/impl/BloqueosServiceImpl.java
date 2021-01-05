package rpt.service.impl;

import rpt.service.BloqueosService;
import rpt.domain.Bloqueos;
import rpt.repository.BloqueosRepository;
import rpt.service.dto.BloqueosDTO;
import rpt.service.mapper.BloqueosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Bloqueos.
 */
@Service
@Transactional
public class BloqueosServiceImpl implements BloqueosService {

    private final Logger log = LoggerFactory.getLogger(BloqueosServiceImpl.class);

    private final BloqueosRepository bloqueosRepository;

    private final BloqueosMapper bloqueosMapper;

    public BloqueosServiceImpl(BloqueosRepository bloqueosRepository, BloqueosMapper bloqueosMapper) {
        this.bloqueosRepository = bloqueosRepository;
        this.bloqueosMapper = bloqueosMapper;
    }

    /**
     * Save a bloqueos.
     *
     * @param bloqueosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BloqueosDTO save(BloqueosDTO bloqueosDTO) {
        log.debug("Request to save Bloqueos : {}", bloqueosDTO);
        Bloqueos bloqueos = bloqueosMapper.toEntity(bloqueosDTO);
        bloqueos = bloqueosRepository.save(bloqueos);
        return bloqueosMapper.toDto(bloqueos);
    }

    /**
     * Get all the bloqueos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BloqueosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bloqueos");
        return bloqueosRepository.findAll(pageable)
            .map(bloqueosMapper::toDto);
    }


    /**
     * Get one bloqueos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BloqueosDTO> findOne(Long id) {
        log.debug("Request to get Bloqueos : {}", id);
        return bloqueosRepository.findById(id)
            .map(bloqueosMapper::toDto);
    }

    /**
     * Delete the bloqueos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bloqueos : {}", id);
        bloqueosRepository.deleteById(id);
    }
}
