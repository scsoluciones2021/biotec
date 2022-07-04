package gestWeb.service.impl;

import gestWeb.service.EstadosService;
import gestWeb.domain.Estados;
import gestWeb.repository.EstadosRepository;
import gestWeb.service.dto.EstadosDTO;
import gestWeb.service.mapper.EstadosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Estados.
 */
@Service
@Transactional
public class EstadosServiceImpl implements EstadosService {

    private final Logger log = LoggerFactory.getLogger(EstadosServiceImpl.class);

    private final EstadosRepository estadosRepository;

    private final EstadosMapper estadosMapper;

    public EstadosServiceImpl(EstadosRepository estadosRepository, EstadosMapper estadosMapper) {
        this.estadosRepository = estadosRepository;
        this.estadosMapper = estadosMapper;
    }

    /**
     * Save a estados.
     *
     * @param estadosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstadosDTO save(EstadosDTO estadosDTO) {
        log.debug("Request to save Estados : {}", estadosDTO);
        Estados estados = estadosMapper.toEntity(estadosDTO);
        estados = estadosRepository.save(estados);
        return estadosMapper.toDto(estados);
    }

    /**
     * Get all the estados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstadosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estados");
        return estadosRepository.findAll(pageable)
            .map(estadosMapper::toDto);
    }


    /**
     * Get one estados by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstadosDTO> findOne(Long id) {
        log.debug("Request to get Estados : {}", id);
        return estadosRepository.findById(id)
            .map(estadosMapper::toDto);
    }

    /**
     * Delete the estados by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estados : {}", id);
        estadosRepository.deleteById(id);
    }
}
