package gestWeb.service.impl;

import gestWeb.service.ConstantesService;
import gestWeb.domain.Constantes;
import gestWeb.repository.ConstantesRepository;
import gestWeb.service.dto.ConstantesDTO;
import gestWeb.service.mapper.ConstantesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Constantes.
 */
@Service
@Transactional
public class ConstantesServiceImpl implements ConstantesService {

    private final Logger log = LoggerFactory.getLogger(ConstantesServiceImpl.class);

    private final ConstantesRepository constantesRepository;

    private final ConstantesMapper constantesMapper;

    public ConstantesServiceImpl(ConstantesRepository constantesRepository, ConstantesMapper constantesMapper) {
        this.constantesRepository = constantesRepository;
        this.constantesMapper = constantesMapper;
    }

    /**
     * Save a constantes.
     *
     * @param constantesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConstantesDTO save(ConstantesDTO constantesDTO) {
        log.debug("Request to save Constantes : {}", constantesDTO);
        Constantes constantes = constantesMapper.toEntity(constantesDTO);
        constantes = constantesRepository.save(constantes);
        return constantesMapper.toDto(constantes);
    }

    /**
     * Get all the constantes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConstantesDTO> findAll() {
        log.debug("Request to get all Constantes");
        return constantesRepository.findAll().stream()
            .map(constantesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one constantes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConstantesDTO> findOne(Long id) {
        log.debug("Request to get Constantes : {}", id);
        return constantesRepository.findById(id)
            .map(constantesMapper::toDto);
    }

    /**
     * Delete the constantes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Constantes : {}", id);
        constantesRepository.deleteById(id);
    }
}
