package gestWeb.service.impl;

import gestWeb.service.FamiliarService;
import gestWeb.domain.Familiar;
import gestWeb.repository.FamiliarRepository;
import gestWeb.service.dto.FamiliarDTO;
import gestWeb.service.mapper.FamiliarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Familiar.
 */
@Service
@Transactional
public class FamiliarServiceImpl implements FamiliarService {

    private final Logger log = LoggerFactory.getLogger(FamiliarServiceImpl.class);

    private final FamiliarRepository familiarRepository;

    private final FamiliarMapper familiarMapper;

    public FamiliarServiceImpl(FamiliarRepository familiarRepository, FamiliarMapper familiarMapper) {
        this.familiarRepository = familiarRepository;
        this.familiarMapper = familiarMapper;
    }

    /**
     * Save a familiar.
     *
     * @param familiarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FamiliarDTO save(FamiliarDTO familiarDTO) {
        log.debug("Request to save Familiar : {}", familiarDTO);
        Familiar familiar = familiarMapper.toEntity(familiarDTO);
        familiar = familiarRepository.save(familiar);
        return familiarMapper.toDto(familiar);
    }

    /**
     * Get all the familiars.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamiliarDTO> findAll() {
        log.debug("Request to get all Familiars");
        return familiarRepository.findAll().stream()
            .map(familiarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one familiar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FamiliarDTO> findOne(Long id) {
        log.debug("Request to get Familiar : {}", id);
        return familiarRepository.findById(id)
            .map(familiarMapper::toDto);
    }

    /**
     * Delete the familiar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Familiar : {}", id);
        familiarRepository.deleteById(id);
    }
}
