package gestWeb.service.impl;

import gestWeb.service.IntoleranciaService;
import gestWeb.domain.Intolerancia;
import gestWeb.repository.IntoleranciaRepository;
import gestWeb.service.dto.IntoleranciaDTO;
import gestWeb.service.mapper.IntoleranciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Intolerancia.
 */
@Service
@Transactional
public class IntoleranciaServiceImpl implements IntoleranciaService {

    private final Logger log = LoggerFactory.getLogger(IntoleranciaServiceImpl.class);

    private final IntoleranciaRepository intoleranciaRepository;

    private final IntoleranciaMapper intoleranciaMapper;

    public IntoleranciaServiceImpl(IntoleranciaRepository intoleranciaRepository, IntoleranciaMapper intoleranciaMapper) {
        this.intoleranciaRepository = intoleranciaRepository;
        this.intoleranciaMapper = intoleranciaMapper;
    }

    /**
     * Save a intolerancia.
     *
     * @param intoleranciaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IntoleranciaDTO save(IntoleranciaDTO intoleranciaDTO) {
        log.debug("Request to save Intolerancia : {}", intoleranciaDTO);
        Intolerancia intolerancia = intoleranciaMapper.toEntity(intoleranciaDTO);
        intolerancia = intoleranciaRepository.save(intolerancia);
        return intoleranciaMapper.toDto(intolerancia);
    }

    /**
     * Get all the intolerancias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IntoleranciaDTO> findAll() {
        log.debug("Request to get all Intolerancias");
        return intoleranciaRepository.findAll().stream()
            .map(intoleranciaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one intolerancia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IntoleranciaDTO> findOne(Long id) {
        log.debug("Request to get Intolerancia : {}", id);
        return intoleranciaRepository.findById(id)
            .map(intoleranciaMapper::toDto);
    }

    /**
     * Delete the intolerancia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intolerancia : {}", id);
        intoleranciaRepository.deleteById(id);
    }
}
