package rpt.service.impl;

import rpt.service.ObraSocialService;
import rpt.domain.ObraSocial;
import rpt.repository.ObraSocialRepository;
import rpt.service.dto.ObraSocialDTO;
import rpt.service.mapper.ObraSocialMapper;
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
 * Service Implementation for managing ObraSocial.
 */
@Service
@Transactional
public class ObraSocialServiceImpl implements ObraSocialService {

    private final Logger log = LoggerFactory.getLogger(ObraSocialServiceImpl.class);

    private final ObraSocialRepository obraSocialRepository;

    private final ObraSocialMapper obraSocialMapper;

    public ObraSocialServiceImpl(ObraSocialRepository obraSocialRepository, ObraSocialMapper obraSocialMapper) {
        this.obraSocialRepository = obraSocialRepository;
        this.obraSocialMapper = obraSocialMapper;
    }

    /**
     * Save a obraSocial.
     *
     * @param obraSocialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObraSocialDTO save(ObraSocialDTO obraSocialDTO) {
        log.debug("Request to save ObraSocial : {}", obraSocialDTO);
        ObraSocial obraSocial = obraSocialMapper.toEntity(obraSocialDTO);
        obraSocial = obraSocialRepository.save(obraSocial);
        return obraSocialMapper.toDto(obraSocial);
    }

    /**
     * Get all the obraSocials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObraSocialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObraSocials");
        return obraSocialRepository.findAll(pageable)
            .map(obraSocialMapper::toDto);
    }


    /**
     * Get one obraSocial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObraSocialDTO> findOne(Long id) {
        log.debug("Request to get ObraSocial : {}", id);
        return obraSocialRepository.findById(id)
            .map(obraSocialMapper::toDto);
    }

    /**
     * Delete the obraSocial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObraSocial : {}", id);
        obraSocialRepository.deleteById(id);
    }

    @Override
    public List<ObraSocialDTO> findObraSocial(String nombreStr) {
        List<ObraSocialDTO> result = this.obraSocialRepository.findByNombreCodigo(nombreStr).stream()
         .map(obraSocialMapper::toDto).collect(Collectors.toList());
      return result;

    }

    /**
     * Get all the obras sociales.
     * Sin paginar
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ObraSocialDTO> todas() {
        return this.obraSocialRepository.todas().stream()
            .map(obraSocialMapper::toDto).collect(Collectors.toList());
    }
}
