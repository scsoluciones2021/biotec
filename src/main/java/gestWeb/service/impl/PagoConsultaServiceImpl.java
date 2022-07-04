package gestWeb.service.impl;

import gestWeb.service.PagoConsultaService;
import gestWeb.domain.PagoConsulta;
import gestWeb.repository.PagoConsultaRepository;
import gestWeb.service.dto.PagoConsultaDTO;
import gestWeb.service.mapper.PagoConsultaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PagoConsulta}.
 */
@Service
@Transactional
public class PagoConsultaServiceImpl implements PagoConsultaService {

    private final Logger log = LoggerFactory.getLogger(PagoConsultaServiceImpl.class);

    private final PagoConsultaRepository pagoConsultaRepository;

    private final PagoConsultaMapper pagoConsultaMapper;

    public PagoConsultaServiceImpl(PagoConsultaRepository pagoConsultaRepository,
            PagoConsultaMapper pagoConsultaMapper) {
        this.pagoConsultaRepository = pagoConsultaRepository;
        this.pagoConsultaMapper = pagoConsultaMapper;
    }

    /**
     * Save a pagoConsulta.
     *
     * @param pagoConsultaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PagoConsultaDTO save(PagoConsultaDTO pagoConsultaDTO) {
        log.debug("Request to save PagoConsulta : {}", pagoConsultaDTO);
        PagoConsulta pagoConsulta = pagoConsultaMapper.toEntity(pagoConsultaDTO);
        pagoConsulta = pagoConsultaRepository.save(pagoConsulta);
        return pagoConsultaMapper.toDto(pagoConsulta);
    }

    /**
     * Get all the pagoConsultas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagoConsultaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PagoConsultas");
        return pagoConsultaRepository.findAll(pageable).map(pagoConsultaMapper::toDto);
    }

    /**
     * Get one pagoConsulta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PagoConsultaDTO> findOne(Long id) {
        log.debug("Request to get PagoConsulta : {}", id);
        return pagoConsultaRepository.findById(id).map(pagoConsultaMapper::toDto);
    }

    /**
     * Delete the pagoConsulta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PagoConsulta : {}", id);
        pagoConsultaRepository.deleteById(id);
    }

    @Override
    public List<PagoConsultaDTO> busqueda_filtros_impresion(String[] query, List<String> espSel, List<String> profSel) {
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

        return pagoConsultaRepository.busqueda_filtros_impresion(ape, nom, dni, fech, esp, prof, usuarioCarga).stream()
                .map(pagoConsultaMapper::toDto).collect(Collectors.toList());
    
    }
}
