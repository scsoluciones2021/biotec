package rpt.service.impl;

import rpt.service.Adjuntos_fichaService;
import rpt.domain.Adjuntos_ficha;
import rpt.repository.Adjuntos_fichaRepository;
import rpt.service.dto.Adjuntos_fichaDTO;
import rpt.service.mapper.Adjuntos_fichaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * Service Implementation for managing Adjuntos_ficha.
 */
@Service
@Transactional
public class Adjuntos_fichaServiceImpl implements Adjuntos_fichaService {

    private final Logger log = LoggerFactory.getLogger(Adjuntos_fichaServiceImpl.class);

    private final Adjuntos_fichaRepository adjuntos_fichaRepository;

    private final Adjuntos_fichaMapper adjuntos_fichaMapper;

    private final Path rootLocation = Paths.get("upload/Fichas");

    public Adjuntos_fichaServiceImpl(Adjuntos_fichaRepository adjuntos_fichaRepository,
            Adjuntos_fichaMapper adjuntos_fichaMapper) {
        this.adjuntos_fichaRepository = adjuntos_fichaRepository;
        this.adjuntos_fichaMapper = adjuntos_fichaMapper;
    }

    /**
     * Save a adjuntos_ficha.
     *
     * @param adjuntos_fichaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Adjuntos_fichaDTO save(Adjuntos_fichaDTO adjuntos_fichaDTO) {
        log.debug("Request to save Adjuntos_ficha : {}", adjuntos_fichaDTO);
        Adjuntos_ficha adjuntos_ficha = adjuntos_fichaMapper.toEntity(adjuntos_fichaDTO);
        adjuntos_ficha = adjuntos_fichaRepository.save(adjuntos_ficha);
        return adjuntos_fichaMapper.toDto(adjuntos_ficha);
    }

    /**
     * Get all the adjuntos_fichas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Adjuntos_fichaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adjuntos_fichas");
        return adjuntos_fichaRepository.findAll(pageable).map(adjuntos_fichaMapper::toDto);
    }

    /**
     * Get one adjuntos_ficha by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Adjuntos_fichaDTO> findOne(Long id) {
        log.debug("Request to get Adjuntos_ficha : {}", id);
        return adjuntos_fichaRepository.findById(id).map(adjuntos_fichaMapper::toDto);
    }

    /**
     * Delete the adjuntos_ficha by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adjuntos_ficha : {}", id);
        adjuntos_fichaRepository.deleteById(id);
    }

    // Métodos para subir / ver archivos cargados
    @Override
    public String store(MultipartFile file) {
        return null;

    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Integer getNro(Long ficha) {
        return adjuntos_fichaRepository.ultimoNumero(ficha);
    }

    @Override
    public Integer incrementNroImagen(Long ficha) {
        Integer nro = 0;
        nro = this.getNro(ficha);
        nro++;
        return nro;
    }

    @Override
    public List<String> obtenerArchivosFichaConsulta(Long idFicha) {
        List<String> fileNames = this.adjuntos_fichaRepository.obtenerArchivosFichaConsulta(idFicha, "\\");
        return fileNames;
    }

    @Override
    public Resource loadFile(String filename) {
        return null;
    }

    // Fin métodos para archivos
}
