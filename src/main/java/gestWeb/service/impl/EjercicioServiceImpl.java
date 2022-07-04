package gestWeb.service.impl;

import gestWeb.service.EjercicioService;
import gestWeb.domain.Ejercicio;
import gestWeb.repository.EjercicioRepository;
import gestWeb.service.dto.EjercicioDTO;
import gestWeb.service.mapper.EjercicioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Ejercicio.
 */
@Service
@Transactional
public class EjercicioServiceImpl implements EjercicioService {

    private final Logger log = LoggerFactory.getLogger(EjercicioServiceImpl.class);

    private final EjercicioRepository ejercicioRepository;

    private final EjercicioMapper ejercicioMapper;

    public EjercicioServiceImpl(EjercicioRepository ejercicioRepository, EjercicioMapper ejercicioMapper) {
        this.ejercicioRepository = ejercicioRepository;
        this.ejercicioMapper = ejercicioMapper;
    }

    /**
     * Save a ejercicio.
     *
     * @param ejercicioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EjercicioDTO save(EjercicioDTO ejercicioDTO) {
        log.debug("Request to save Ejercicio : {}", ejercicioDTO);
        Ejercicio ejercicio = ejercicioMapper.toEntity(ejercicioDTO);
        ejercicio = ejercicioRepository.save(ejercicio);
        return ejercicioMapper.toDto(ejercicio);
    }

    /**
     * Get all the ejercicios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EjercicioDTO> findAll() {
        log.debug("Request to get all Ejercicios");
        return ejercicioRepository.findAll().stream()
            .map(ejercicioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ejercicio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EjercicioDTO> findOne(Long id) {
        log.debug("Request to get Ejercicio : {}", id);
        return ejercicioRepository.findById(id)
            .map(ejercicioMapper::toDto);
    }

    /**
     * Delete the ejercicio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ejercicio : {}", id);
        ejercicioRepository.deleteById(id);
    }
}
