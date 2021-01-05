package rpt.service.impl;

import rpt.service.ProvinciaService;
import rpt.domain.Provincia;
import rpt.repository.ProvinciaRepository;
import rpt.service.dto.ProvinciaDTO;
import rpt.service.mapper.ProvinciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Provincia.
 */
@Service
@Transactional
public class ProvinciaServiceImpl implements ProvinciaService {

    private final Logger log = LoggerFactory.getLogger(ProvinciaServiceImpl.class);

    private final ProvinciaRepository provinciaRepository;

    private final ProvinciaMapper provinciaMapper;

    public ProvinciaServiceImpl(ProvinciaRepository provinciaRepository, ProvinciaMapper provinciaMapper) {
        this.provinciaRepository = provinciaRepository;
        this.provinciaMapper = provinciaMapper;
    }

    /**
     * Save a provincia.
     *
     * @param provinciaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProvinciaDTO save(ProvinciaDTO provinciaDTO) {
        log.debug("Request to save Provincia : {}", provinciaDTO);
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        provincia = provinciaRepository.save(provincia);
        return provinciaMapper.toDto(provincia);
    }

    /**
     * Get all the provincias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProvinciaDTO> findAll() {
        log.debug("Request to get all Provincias");
        return provinciaRepository.findAll().stream()
            .map(provinciaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one provincia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProvinciaDTO> findOne(Long id) {
        log.debug("Request to get Provincia : {}", id);
        return provinciaRepository.findById(id)
            .map(provinciaMapper::toDto);
    }

    /**
     * Delete the provincia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provincia : {}", id);
        provinciaRepository.deleteById(id);
    }
}
