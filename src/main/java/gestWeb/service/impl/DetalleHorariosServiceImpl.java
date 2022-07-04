package gestWeb.service.impl;

import gestWeb.service.DetalleHorariosService;
import gestWeb.domain.DetalleHorarios;
import gestWeb.repository.DetalleHorariosRepository;
import gestWeb.service.dto.DetalleHorariosDTO;
import gestWeb.service.mapper.DetalleHorariosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing DetalleHorarios.
 */
@Service
@Transactional
public class DetalleHorariosServiceImpl implements DetalleHorariosService {

    private final Logger log = LoggerFactory.getLogger(DetalleHorariosServiceImpl.class);

    private final DetalleHorariosRepository detalleHorariosRepository;

    private final DetalleHorariosMapper detalleHorariosMapper;

    public DetalleHorariosServiceImpl(DetalleHorariosRepository detalleHorariosRepository, DetalleHorariosMapper detalleHorariosMapper) {
        this.detalleHorariosRepository = detalleHorariosRepository;
        this.detalleHorariosMapper = detalleHorariosMapper;
    }

    /**
     * Save a detalleHorarios.
     *
     * @param detalleHorariosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetalleHorariosDTO save(DetalleHorariosDTO detalleHorariosDTO) {
        log.debug("Request to save DetalleHorarios : {}", detalleHorariosDTO);
        DetalleHorarios detalleHorarios = detalleHorariosMapper.toEntity(detalleHorariosDTO);
        detalleHorarios = detalleHorariosRepository.save(detalleHorarios);
        return detalleHorariosMapper.toDto(detalleHorarios);
    }

    /**
     * Get all the detalleHorarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleHorariosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleHorarios");
        return detalleHorariosRepository.findAll(pageable)
            .map(detalleHorariosMapper::toDto);
    }


    /**
     * Get one detalleHorarios by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleHorariosDTO> findOne(Long id) {
        log.debug("Request to get DetalleHorarios : {}", id);
        return detalleHorariosRepository.findById(id)
            .map(detalleHorariosMapper::toDto);
    }

    /**
     * Delete the detalleHorarios by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetalleHorarios : {}", id);
        detalleHorariosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetalleHorariosDTO> buscarXHorario(Long idHorario, Pageable pageable) {
        log.debug("Request to get buscarXHorario DetalleHorarios");
        return detalleHorariosRepository.buscarXHorario(idHorario, pageable)
            .map(detalleHorariosMapper::toDto);
    }
}
