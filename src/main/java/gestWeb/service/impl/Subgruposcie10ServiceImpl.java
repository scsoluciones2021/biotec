package gestWeb.service.impl;

import gestWeb.service.Subgruposcie10Service;
import gestWeb.service.dto.Subgruposcie10DTO;
import gestWeb.domain.Subgruposcie10;
import gestWeb.repository.Subgruposcie10Repository;
import gestWeb.service.mapper.Subgruposcie10Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Subgruposcie10}.
 */
@Service
@Transactional
public class Subgruposcie10ServiceImpl implements Subgruposcie10Service {

    private final Logger log = LoggerFactory.getLogger(Subgruposcie10ServiceImpl.class);

    private final Subgruposcie10Repository subgruposcie10Repository;

    private final Subgruposcie10Mapper subgruposcie10Mapper;

    public Subgruposcie10ServiceImpl(Subgruposcie10Repository subgruposcie10Repository, Subgruposcie10Mapper subgruposcie10Mapper) {
        this.subgruposcie10Repository = subgruposcie10Repository;
        this.subgruposcie10Mapper = subgruposcie10Mapper;
    }

    @Override
    public Subgruposcie10DTO save(Subgruposcie10DTO subgruposcie10DTO) {
        Subgruposcie10 subgruposcie10 = subgruposcie10Mapper.toEntity(subgruposcie10DTO);
        subgruposcie10 = subgruposcie10Repository.save(subgruposcie10);
        return subgruposcie10Mapper.toDto(subgruposcie10);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Subgruposcie10DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subgruposcie10s");
        return subgruposcie10Repository.findAll(pageable)
        .map(subgruposcie10Mapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Subgruposcie10DTO> findOne(Long id) {
        log.debug("Request to get Subgruposcie10 : {}", id);
        return subgruposcie10Repository.findById(id)
         .map(subgruposcie10Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subgruposcie10 : {}", id);
        subgruposcie10Repository.deleteById(id);
    }
}
