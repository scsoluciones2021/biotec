package rpt.service.impl;

import rpt.service.ProfesionalService;
import rpt.domain.Profesional;
import rpt.repository.ProfesionalRepository;
import rpt.service.dto.EspecialidadDTO;
import rpt.service.dto.ProfesionalDTO;
import rpt.service.dto.ProfesionalTurnoDTO;
import rpt.service.mapper.ProfesionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Profesional.
 */
@Service
@Transactional
public class ProfesionalServiceImpl implements ProfesionalService {

    private final Logger log = LoggerFactory.getLogger(ProfesionalServiceImpl.class);

    private final ProfesionalRepository profesionalRepository;

    private final ProfesionalMapper profesionalMapper;

    public ProfesionalServiceImpl(ProfesionalRepository profesionalRepository, ProfesionalMapper profesionalMapper) {
        this.profesionalRepository = profesionalRepository;
        this.profesionalMapper = profesionalMapper;
    }

    /**
     * Save a profesional.
     *
     * @param profesionalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfesionalDTO save(ProfesionalDTO profesionalDTO) {
        log.debug("Request to save Profesional : {}", profesionalDTO);
        Profesional profesional = profesionalMapper.toEntity(profesionalDTO);
        profesional = profesionalRepository.save(profesional);
        return profesionalMapper.toDto(profesional);
    }

    /**
     * Get all the profesionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfesionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profesionals");
        return profesionalRepository.findAll(pageable)
            .map(profesionalMapper::toDto);
    }

    /**
     * Get all the Profesional with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProfesionalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profesionalRepository.findAllWithEagerRelationships(pageable).map(profesionalMapper::toDto);
    }


    /**
     * Get one profesional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfesionalDTO> findOne(Long id) {
        log.debug("Request to get Profesional : {}", id);
        return profesionalRepository.findOneWithEagerRelationships(id)
            .map(profesionalMapper::toDto);
    }

    /**
     * Delete the profesional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profesional : {}", id);
        profesionalRepository.deleteById(id);
    }

    // búsqueda de profesionales para listados de autocomplete
    @Override
    @Transactional(readOnly = true)
    public List<ProfesionalDTO> findProfesional(String query) {
        List<ProfesionalDTO> result = this.profesionalRepository.findByN(query).stream()
        .map(profesionalMapper::toDto).collect(Collectors.toList());
     return result;
    }

    // Búsqueda de profesionales en el listado general
    @Override
    @Transactional(readOnly = true)
    public Page<ProfesionalDTO> search(String query, Pageable pageable) {        
            log.debug("Al service impl llega: {}", query);
            return profesionalRepository.findByApeNomContaining(query, pageable)
                .map(profesionalMapper::toDto);
    }

    @Override
    public List<ProfesionalTurnoDTO> findAllCustom() {
        log.debug("Request to search all Profesionales custom {}");
        List<ProfesionalDTO> listaProf = profesionalRepository.findAllWithEagerRelationships().stream()
        .map(profesionalMapper::toDto).collect(Collectors.toList());

        List<ProfesionalTurnoDTO> listaFinal = new ArrayList<ProfesionalTurnoDTO>();

        //Recorrer la lista de profesionales e ir armando el dto de turno y agregarlo a la lista.
        for (ProfesionalDTO prof : listaProf) {
            ProfesionalTurnoDTO ptdto = new ProfesionalTurnoDTO();
            ptdto.setId(prof.getId());
            ptdto.setNombreProfesional(prof.getNombreProfesional());
            String idsEsp = "";
            String nomEsp = "";
            for (EspecialidadDTO esp : prof.getEspecialidads()) {
                idsEsp += esp.getId().toString() + "|";
                nomEsp += esp.getNombreEspecialidad() +"|";                
            }

            ptdto.setIdsEspecialidades(idsEsp);
            ptdto.setNombreEspecialidades(nomEsp);

            listaFinal.add(ptdto);
        }
        return listaFinal;

    }

    /**
     * Get all the profesionals.
     * Sin paginar
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfesionalDTO> todos() {
        return profesionalRepository.todos().stream()
            .map(profesionalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProfesionalDTO> findOneByUserId(Long id) {
        log.debug("Request to get Profesional by user id : {}", id);
        return profesionalRepository.findOneByUserId(id).map(profesionalMapper::toDto);
    }
}
