package rpt.service.impl;

import rpt.service.PersonalService;
import rpt.domain.Personal;
import rpt.repository.PersonalRepository;
import rpt.service.dto.PersonalDTO;
import rpt.service.mapper.PersonalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Personal.
 */
@Service
@Transactional
public class PersonalServiceImpl implements PersonalService {

    private final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);

    private final PersonalRepository personalRepository;

    private final PersonalMapper personalMapper;

    public PersonalServiceImpl(PersonalRepository personalRepository, PersonalMapper personalMapper) {
        this.personalRepository = personalRepository;
        this.personalMapper = personalMapper;
    }

    /**
     * Save a personal.
     *
     * @param personalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonalDTO save(PersonalDTO personalDTO) {
        log.debug("Request to save Personal : {}", personalDTO);
        Personal personal = personalMapper.toEntity(personalDTO);
        personal = personalRepository.save(personal);
        return personalMapper.toDto(personal);
    }

    /**
     * Get all the personals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonalDTO> findAll() {
        log.debug("Request to get all Personals");
        return personalRepository.findAll().stream()
            .map(personalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalDTO> findOne(Long id) {
        log.debug("Request to get Personal : {}", id);
        return personalRepository.findById(id)
            .map(personalMapper::toDto);
    }

    /**
     * Delete the personal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personal : {}", id);
        personalRepository.deleteById(id);
    }
}
