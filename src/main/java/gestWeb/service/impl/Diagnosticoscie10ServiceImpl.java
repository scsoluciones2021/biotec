package gestWeb.service.impl;

import gestWeb.service.Diagnosticoscie10Service;
import gestWeb.domain.Diagnosticoscie10;
import gestWeb.service.dto.Diagnosticoscie10DTO;
import gestWeb.repository.Diagnosticoscie10Repository;
import gestWeb.service.mapper.Diagnosticoscie10Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Diagnosticoscie10}.
 */
@Service
@Transactional
public class Diagnosticoscie10ServiceImpl implements Diagnosticoscie10Service {

    private final Logger log = LoggerFactory.getLogger(Diagnosticoscie10ServiceImpl.class);

    private final Diagnosticoscie10Repository diagnosticoscie10Repository;

    private final Diagnosticoscie10Mapper diagnosticoscie10Mapper;

    public Diagnosticoscie10ServiceImpl(Diagnosticoscie10Repository diagnosticoscie10Repository, Diagnosticoscie10Mapper diagnosticoscie10Mapper) {
        this.diagnosticoscie10Repository = diagnosticoscie10Repository;
        this.diagnosticoscie10Mapper = diagnosticoscie10Mapper;
    }

    @Override
    public Diagnosticoscie10DTO save(Diagnosticoscie10DTO diagnosticoscie10DTO) {
        Diagnosticoscie10 diagnosticoscie10 = diagnosticoscie10Mapper.toEntity(diagnosticoscie10DTO);
        diagnosticoscie10 = diagnosticoscie10Repository.save(diagnosticoscie10);
        return diagnosticoscie10Mapper.toDto(diagnosticoscie10);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Diagnosticoscie10DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnosticoscie10s");
        return diagnosticoscie10Repository.findAll(pageable)
            .map(diagnosticoscie10Mapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Diagnosticoscie10DTO> findOne(Long id) {
        log.debug("Request to get Diagnosticoscie10 : {}", id);
        return diagnosticoscie10Repository.findById(id)
        .map(diagnosticoscie10Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diagnosticoscie10 : {}", id);
        diagnosticoscie10Repository.deleteById(id);
    }
}
