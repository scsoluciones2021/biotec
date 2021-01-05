package rpt.service.impl;

import rpt.service.TurnoService;
import rpt.domain.Turno;
import rpt.repository.TurnoRepository;
import rpt.service.dto.EspecialidadDTO;
import rpt.service.dto.TurnoDTO;
import rpt.service.dto.TurnoProfDTO;
import rpt.service.mapper.TurnoMapper;
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
 * Service Implementation for managing Turno.
 */
@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final Logger log = LoggerFactory.getLogger(TurnoServiceImpl.class);

    private final TurnoRepository turnoRepository;

    private final TurnoMapper turnoMapper;

    public TurnoServiceImpl(TurnoRepository turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    /**
     * Save a turno.
     *
     * @param turnoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TurnoDTO save(TurnoDTO turnoDTO) {
        log.debug("Request to save Turno : {}", turnoDTO);
        Turno turno = turnoMapper.toEntity(turnoDTO);
        turno = turnoRepository.save(turno);
        return turnoMapper.toDto(turno);
    }

    /**
     * Get all the turnos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TurnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Turnos");
        return turnoRepository.findAll(pageable).map(turnoMapper::toDto);
    }

    /**
     * Get one turno by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TurnoDTO> findOne(Long id) {
        log.debug("Request to get Turno : {}", id);
        return turnoRepository.findById(id).map(turnoMapper::toDto);
    }

    @Override
    public void cambiarEstado(Long id, Integer estado) {
        log.debug("Request to cambiarEstado Turno : {}", id);
        log.debug("Request to cambiarEstado Turno estado : {}", estado);
        // turnoRepository.deleteById(id);
        turnoRepository.cambiarEstado(id, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> horariosOcupadosProf(Long idProf, Long idEsp, String dia) {
        List<String> result = this.turnoRepository.horariosOcupadosProf(idProf, idEsp, dia).stream()
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Page<TurnoDTO> busqueda_filtros(String[] query, List<String> espSel, List<String> profSel,
            Pageable pageable) {
        String ape = null;
        String nom = null;
        String dni = null;
        String fech = null;
        List<String> esp = null;
        List<String> prof = null;
        Long usuarioCarga = (long) -1;

        if (Long.parseLong(query[6]) != -1)
            usuarioCarga = Long.parseLong(query[6]);

        if (!query[0].isEmpty()) {
            ape = query[0];
        }

        if (!query[1].isEmpty()) {
            nom = query[1];
        }

        if (!query[2].isEmpty()) {
            dni = query[2];
        }
        // log.debug("En el profesional llega: : {}", query[3]);
        if (!query[3].isEmpty()) {
            fech = query[3];
        }

        if (!espSel.isEmpty()) {
            esp = espSel;
        }
        // log.debug("En el profesional llega: : {}", profSel);
        if (!profSel.isEmpty()) {
            prof = profSel;
        }
        // log.debug("usuarioCarga: " + usuarioCarga);

        return turnoRepository.busqueda_filtros(ape, nom, dni, fech, esp, prof, usuarioCarga, pageable)
                .map(turnoMapper::toDto);
    }

    @Override
    public List<TurnoDTO> busqueda_filtros_impresion(String[] query, List<String> espSel, List<String> profSel) {
        String ape = null;
        String nom = null;
        String dni = null;
        String fech = null;
        List<String> esp = null;
        List<String> prof = null;
        Long usuarioCarga = Long.parseLong(query[6]);

        if (!query[0].isEmpty()) {
            ape = query[0];
        }

        if (!query[1].isEmpty()) {
            nom = query[1];
        }

        if (!query[2].isEmpty()) {
            dni = query[2];
        }

        if (!query[3].isEmpty()) {
            fech = query[3];
        }

        if (!espSel.isEmpty()) {
            esp = espSel;
        }

        if (!profSel.isEmpty()) {
            prof = profSel;
        }

        return turnoRepository.busqueda_filtros_impresion(ape, nom, dni, fech, esp, prof, usuarioCarga).stream()
                .map(turnoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<TurnoDTO> busqueda_medico(TurnoProfDTO tpFinal, Pageable pageable) {
        System.out.println("TPFINAL: " + tpFinal.getFecha());
        List<String> esp = new ArrayList<String>();
        for (EspecialidadDTO e: tpFinal.getEspecialidades()) {
            esp.add(e.getId().toString());
        }
        System.out.println("TPFINAL: " + esp);
        return turnoRepository.busqueda_medico(tpFinal.getFecha(), tpFinal.getProfesional(), esp, tpFinal.getEstado(), pageable).map(turnoMapper::toDto);
    }
}
