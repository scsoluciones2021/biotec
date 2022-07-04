package gestWeb.service.impl;

import gestWeb.service.Categoriascie10Service;
import gestWeb.service.dto.Categoriascie10DTO;
import gestWeb.domain.Categoriascie10;
import gestWeb.repository.Categoriascie10Repository;
import gestWeb.service.mapper.Categoriascie10Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Categoriascie10}.
 */
@Service
@Transactional
public class Categoriascie10ServiceImpl implements Categoriascie10Service {

    private final Logger log = LoggerFactory.getLogger(Categoriascie10ServiceImpl.class);

    private final Categoriascie10Repository categoriascie10Repository;

    private final Categoriascie10Mapper categoriascie10Mapper;

    public Categoriascie10ServiceImpl(Categoriascie10Repository categoriascie10Repository, Categoriascie10Mapper categoriascie10Mapper) {
        this.categoriascie10Repository = categoriascie10Repository;
        this.categoriascie10Mapper = categoriascie10Mapper;
    }

    @Override
    public Categoriascie10DTO save(Categoriascie10DTO categoriascie10DTO) {
        Categoriascie10 categoriascie10 = categoriascie10Mapper.toEntity(categoriascie10DTO);
        categoriascie10 = categoriascie10Repository.save(categoriascie10);
        return categoriascie10Mapper.toDto(categoriascie10);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Categoriascie10DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categoriascie10s");
        return categoriascie10Repository.findAll(pageable)
        .map(categoriascie10Mapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Categoriascie10DTO> findOne(Long id) {
        log.debug("Request to get Categoriascie10 : {}", id);
        return categoriascie10Repository.findById(id)
        .map(categoriascie10Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categoriascie10 : {}", id);
        categoriascie10Repository.deleteById(id);
    }
}
