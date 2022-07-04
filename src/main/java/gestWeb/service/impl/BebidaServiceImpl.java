package gestWeb.service.impl;

import gestWeb.service.BebidaService;
import gestWeb.domain.Bebida;
import gestWeb.repository.BebidaRepository;
import gestWeb.service.dto.BebidaDTO;
import gestWeb.service.mapper.BebidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Bebida.
 */
@Service
@Transactional
public class BebidaServiceImpl implements BebidaService {

    private final Logger log = LoggerFactory.getLogger(BebidaServiceImpl.class);

    private final BebidaRepository bebidaRepository;

    private final BebidaMapper bebidaMapper;

    public BebidaServiceImpl(BebidaRepository bebidaRepository, BebidaMapper bebidaMapper) {
        this.bebidaRepository = bebidaRepository;
        this.bebidaMapper = bebidaMapper;
    }

    /**
     * Save a bebida.
     *
     * @param bebidaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BebidaDTO save(BebidaDTO bebidaDTO) {
        log.debug("Request to save Bebida : {}", bebidaDTO);
        Bebida bebida = bebidaMapper.toEntity(bebidaDTO);
        bebida = bebidaRepository.save(bebida);
        return bebidaMapper.toDto(bebida);
    }

    /**
     * Get all the bebidas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BebidaDTO> findAll() {
        log.debug("Request to get all Bebidas");
        return bebidaRepository.findAll().stream()
            .map(bebidaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bebida by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BebidaDTO> findOne(Long id) {
        log.debug("Request to get Bebida : {}", id);
        return bebidaRepository.findById(id)
            .map(bebidaMapper::toDto);
    }

    /**
     * Delete the bebida by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bebida : {}", id);
        bebidaRepository.deleteById(id);
    }
}
