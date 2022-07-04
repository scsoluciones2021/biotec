package gestWeb.service.impl;

import gestWeb.service.AntecedentesFamiliaresService;
import gestWeb.domain.AntecedentesFamiliares;
import gestWeb.repository.AntecedentesFamiliaresRepository;
import gestWeb.service.dto.AntecedentesFamiliaresDTO;
import gestWeb.service.mapper.AntecedentesFamiliaresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing AntecedentesFamiliares.
 */
@Service
@Transactional
public class AntecedentesFamiliaresServiceImpl implements AntecedentesFamiliaresService {

    private final Logger log = LoggerFactory.getLogger(AntecedentesFamiliaresServiceImpl.class);

    private final AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

    private final AntecedentesFamiliaresMapper antecedentesFamiliaresMapper;

    public AntecedentesFamiliaresServiceImpl(AntecedentesFamiliaresRepository antecedentesFamiliaresRepository, AntecedentesFamiliaresMapper antecedentesFamiliaresMapper) {
        this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
        this.antecedentesFamiliaresMapper = antecedentesFamiliaresMapper;
    }

    /**
     * Save a antecedentesFamiliares.
     *
     * @param antecedentesFamiliaresDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AntecedentesFamiliaresDTO save(AntecedentesFamiliaresDTO antecedentesFamiliaresDTO) {
        log.debug("Request to save AntecedentesFamiliares : {}", antecedentesFamiliaresDTO);
        AntecedentesFamiliares antecedentesFamiliares = antecedentesFamiliaresMapper.toEntity(antecedentesFamiliaresDTO);
        antecedentesFamiliares = antecedentesFamiliaresRepository.save(antecedentesFamiliares);
        return antecedentesFamiliaresMapper.toDto(antecedentesFamiliares);
    }

    /**
     * Get all the antecedentesFamiliares.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AntecedentesFamiliaresDTO> findAll() {
        log.debug("Request to get all AntecedentesFamiliares");
        return antecedentesFamiliaresRepository.findAll().stream()
            .map(antecedentesFamiliaresMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one antecedentesFamiliares by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AntecedentesFamiliaresDTO> findOne(Long id) {
        log.debug("Request to get AntecedentesFamiliares : {}", id);
        return antecedentesFamiliaresRepository.findById(id)
            .map(antecedentesFamiliaresMapper::toDto);
    }

    /**
     * Delete the antecedentesFamiliares by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AntecedentesFamiliares : {}", id);
        antecedentesFamiliaresRepository.deleteById(id);
    }
}
