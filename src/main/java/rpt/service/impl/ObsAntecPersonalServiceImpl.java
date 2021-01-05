package rpt.service.impl;

import rpt.service.ObsAntecPersonalService;
import rpt.domain.ObsAntecPersonal;
import rpt.repository.ObsAntecPersonalRepository;
import rpt.service.dto.ObsAntecPersonalDTO;
import rpt.service.mapper.ObsAntecPersonalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ObsAntecPersonal.
 */
@Service
@Transactional
public class ObsAntecPersonalServiceImpl implements ObsAntecPersonalService {

    private final Logger log = LoggerFactory.getLogger(ObsAntecPersonalServiceImpl.class);

    private final ObsAntecPersonalRepository obsAntecPersonalRepository;

    private final ObsAntecPersonalMapper obsAntecPersonalMapper;

    public ObsAntecPersonalServiceImpl(ObsAntecPersonalRepository obsAntecPersonalRepository, ObsAntecPersonalMapper obsAntecPersonalMapper) {
        this.obsAntecPersonalRepository = obsAntecPersonalRepository;
        this.obsAntecPersonalMapper = obsAntecPersonalMapper;
    }

    /**
     * Save a obsAntecPersonal.
     *
     * @param obsAntecPersonalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObsAntecPersonalDTO save(ObsAntecPersonalDTO obsAntecPersonalDTO) {
        log.debug("Request to save ObsAntecPersonal : {}", obsAntecPersonalDTO);
        ObsAntecPersonal obsAntecPersonal = obsAntecPersonalMapper.toEntity(obsAntecPersonalDTO);
        obsAntecPersonal = obsAntecPersonalRepository.save(obsAntecPersonal);
        return obsAntecPersonalMapper.toDto(obsAntecPersonal);
    }

    /**
     * Get all the obsAntecPersonals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObsAntecPersonalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObsAntecPersonals");
        return obsAntecPersonalRepository.findAll(pageable)
            .map(obsAntecPersonalMapper::toDto);
    }


    /**
     * Get one obsAntecPersonal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObsAntecPersonalDTO> findOne(Long id) {
        log.debug("Request to get ObsAntecPersonal : {}", id);
        return obsAntecPersonalRepository.findById(id)
            .map(obsAntecPersonalMapper::toDto);
    }

    /**
     * Delete the obsAntecPersonal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObsAntecPersonal : {}", id);
        obsAntecPersonalRepository.deleteById(id);
    }
}
