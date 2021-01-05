package rpt.service.impl;

import rpt.service.ObsAntecFamiliarService;
import rpt.domain.ObsAntecFamiliar;
import rpt.repository.ObsAntecFamiliarRepository;
import rpt.service.dto.ObsAntecFamiliarDTO;
import rpt.service.mapper.ObsAntecFamiliarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ObsAntecFamiliar.
 */
@Service
@Transactional
public class ObsAntecFamiliarServiceImpl implements ObsAntecFamiliarService {

    private final Logger log = LoggerFactory.getLogger(ObsAntecFamiliarServiceImpl.class);

    private final ObsAntecFamiliarRepository obsAntecFamiliarRepository;

    private final ObsAntecFamiliarMapper obsAntecFamiliarMapper;

    public ObsAntecFamiliarServiceImpl(ObsAntecFamiliarRepository obsAntecFamiliarRepository, ObsAntecFamiliarMapper obsAntecFamiliarMapper) {
        this.obsAntecFamiliarRepository = obsAntecFamiliarRepository;
        this.obsAntecFamiliarMapper = obsAntecFamiliarMapper;
    }

    /**
     * Save a obsAntecFamiliar.
     *
     * @param obsAntecFamiliarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObsAntecFamiliarDTO save(ObsAntecFamiliarDTO obsAntecFamiliarDTO) {
        log.debug("Request to save ObsAntecFamiliar : {}", obsAntecFamiliarDTO);
        ObsAntecFamiliar obsAntecFamiliar = obsAntecFamiliarMapper.toEntity(obsAntecFamiliarDTO);
        obsAntecFamiliar = obsAntecFamiliarRepository.save(obsAntecFamiliar);
        return obsAntecFamiliarMapper.toDto(obsAntecFamiliar);
    }

    /**
     * Get all the obsAntecFamiliars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObsAntecFamiliarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObsAntecFamiliars");
        return obsAntecFamiliarRepository.findAll(pageable)
            .map(obsAntecFamiliarMapper::toDto);
    }


    /**
     * Get one obsAntecFamiliar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObsAntecFamiliarDTO> findOne(Long id) {
        log.debug("Request to get ObsAntecFamiliar : {}", id);
        return obsAntecFamiliarRepository.findById(id)
            .map(obsAntecFamiliarMapper::toDto);
    }

    /**
     * Delete the obsAntecFamiliar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObsAntecFamiliar : {}", id);
        obsAntecFamiliarRepository.deleteById(id);
    }
}
