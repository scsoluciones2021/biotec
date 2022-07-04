package gestWeb.service.impl;

import gestWeb.service.AgrupadorOSService;
import gestWeb.domain.AgrupadorOS;
import gestWeb.repository.AgrupadorOSRepository;
import gestWeb.service.dto.AgrupadorOSDTO;
import gestWeb.service.mapper.AgrupadorOSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing AgrupadorOS.
 */
@Service
@Transactional
public class AgrupadorOSServiceImpl implements AgrupadorOSService {

    private final Logger log = LoggerFactory.getLogger(AgrupadorOSServiceImpl.class);

    private final AgrupadorOSRepository agrupadorOSRepository;

    private final AgrupadorOSMapper agrupadorOSMapper;

    public AgrupadorOSServiceImpl(AgrupadorOSRepository agrupadorOSRepository, AgrupadorOSMapper agrupadorOSMapper) {
        this.agrupadorOSRepository = agrupadorOSRepository;
        this.agrupadorOSMapper = agrupadorOSMapper;
    }

    /**
     * Save a agrupadorOS.
     *
     * @param agrupadorOSDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgrupadorOSDTO save(AgrupadorOSDTO agrupadorOSDTO) {
        log.debug("Request to save AgrupadorOS : {}", agrupadorOSDTO);
        AgrupadorOS agrupadorOS = agrupadorOSMapper.toEntity(agrupadorOSDTO);
        agrupadorOS = agrupadorOSRepository.save(agrupadorOS);
        return agrupadorOSMapper.toDto(agrupadorOS);
    }

    /**
     * Get all the agrupadorOS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgrupadorOSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgrupadorOS");
        return agrupadorOSRepository.findAll(pageable)
            .map(agrupadorOSMapper::toDto);
    }


    /**
     * Get one agrupadorOS by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AgrupadorOSDTO> findOne(Long id) {
        log.debug("Request to get AgrupadorOS : {}", id);
        return agrupadorOSRepository.findById(id)
            .map(agrupadorOSMapper::toDto);
    }

    /**
     * Delete the agrupadorOS by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgrupadorOS : {}", id);
        agrupadorOSRepository.deleteById(id);
    }
}
