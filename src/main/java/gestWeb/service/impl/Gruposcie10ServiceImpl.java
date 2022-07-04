package gestWeb.service.impl;

import gestWeb.service.Gruposcie10Service;
import gestWeb.service.dto.Gruposcie10DTO;
import gestWeb.domain.Gruposcie10;
import gestWeb.repository.Gruposcie10Repository;
import gestWeb.service.mapper.Gruposcie10Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Gruposcie10}.
 */
@Service
@Transactional
public class Gruposcie10ServiceImpl implements Gruposcie10Service {

    private final Logger log = LoggerFactory.getLogger(Gruposcie10ServiceImpl.class);

    private final Gruposcie10Repository gruposcie10Repository;

    private final Gruposcie10Mapper gruposcie10Mapper;

    public Gruposcie10ServiceImpl(Gruposcie10Repository gruposcie10Repository, Gruposcie10Mapper gruposcie10Mapper) {
        this.gruposcie10Repository = gruposcie10Repository;
        this.gruposcie10Mapper = gruposcie10Mapper;
    }

    @Override
    public Gruposcie10DTO save(Gruposcie10DTO gruposcie10DTO) {
        log.debug("Request to save Gruposcie10 : {}", gruposcie10DTO);
        Gruposcie10 gruposcie10 = gruposcie10Mapper.toEntity(gruposcie10DTO);
        gruposcie10 = gruposcie10Repository.save(gruposcie10);
        return gruposcie10Mapper.toDto(gruposcie10);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Gruposcie10DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gruposcie10s");
        return gruposcie10Repository.findAll(pageable)
        .map(gruposcie10Mapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Gruposcie10DTO> findOne(Long id) {
        log.debug("Request to get Gruposcie10 : {}", id);
        return gruposcie10Repository.findById(id)
        .map(gruposcie10Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gruposcie10 : {}", id);
        gruposcie10Repository.deleteById(id);
    }
}
