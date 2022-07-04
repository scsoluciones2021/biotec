package gestWeb.service.impl;

import gestWeb.service.AntecedentesPersonalesService;
import gestWeb.domain.AntecedentesPersonales;
import gestWeb.repository.AntecedentesPersonalesRepository;
import gestWeb.service.dto.AntecedentesPersonalesDTO;
import gestWeb.service.mapper.AntecedentesPersonalesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing AntecedentesPersonales.
 */
@Service
@Transactional
public class AntecedentesPersonalesServiceImpl implements AntecedentesPersonalesService {

    private final Logger log = LoggerFactory.getLogger(AntecedentesPersonalesServiceImpl.class);

    private final AntecedentesPersonalesRepository antecedentesPersonalesRepository;

    private final AntecedentesPersonalesMapper antecedentesPersonalesMapper;

    public AntecedentesPersonalesServiceImpl(AntecedentesPersonalesRepository antecedentesPersonalesRepository, AntecedentesPersonalesMapper antecedentesPersonalesMapper) {
        this.antecedentesPersonalesRepository = antecedentesPersonalesRepository;
        this.antecedentesPersonalesMapper = antecedentesPersonalesMapper;
    }

    /**
     * Save a antecedentesPersonales.
     *
     * @param antecedentesPersonalesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AntecedentesPersonalesDTO save(AntecedentesPersonalesDTO antecedentesPersonalesDTO) {
        log.debug("Request to save AntecedentesPersonales : {}", antecedentesPersonalesDTO);
        AntecedentesPersonales antecedentesPersonales = antecedentesPersonalesMapper.toEntity(antecedentesPersonalesDTO);
        antecedentesPersonales = antecedentesPersonalesRepository.save(antecedentesPersonales);
        return antecedentesPersonalesMapper.toDto(antecedentesPersonales);
    }

    /**
     * Get all the antecedentesPersonales.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AntecedentesPersonalesDTO> findAll() {
        log.debug("Request to get all AntecedentesPersonales");
        return antecedentesPersonalesRepository.findAllWithEagerRelationships().stream()
            .map(antecedentesPersonalesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the AntecedentesPersonales with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AntecedentesPersonalesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return antecedentesPersonalesRepository.findAllWithEagerRelationships(pageable).map(antecedentesPersonalesMapper::toDto);
    }
    

    /**
     * Get one antecedentesPersonales by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AntecedentesPersonalesDTO> findOne(Long id) {
        log.debug("Request to get AntecedentesPersonales : {}", id);
        return antecedentesPersonalesRepository.findOneWithEagerRelationships(id)
            .map(antecedentesPersonalesMapper::toDto);
    }

    /**
     * Delete the antecedentesPersonales by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AntecedentesPersonales : {}", id);
        antecedentesPersonalesRepository.deleteById(id);
    }
}
