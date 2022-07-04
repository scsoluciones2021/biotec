package gestWeb.service.impl;

import gestWeb.service.Adjuntos_fichaService;
import gestWeb.config.ApplicationProperties;
import gestWeb.domain.Adjuntos_ficha;
import gestWeb.repository.Adjuntos_fichaRepository;
import gestWeb.service.dto.Adjuntos_fichaDTO;
import gestWeb.service.mapper.Adjuntos_fichaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Service Implementation for managing Adjuntos_ficha.
 */
@Service
@Transactional
public class Adjuntos_fichaServiceImpl implements Adjuntos_fichaService {

    private final Logger log = LoggerFactory.getLogger(Adjuntos_fichaServiceImpl.class);

    private final Adjuntos_fichaRepository adjuntos_fichaRepository;

    private final Adjuntos_fichaMapper adjuntos_fichaMapper;

    private final Path fileStorageLocation;

    // private String filename;

    public Adjuntos_fichaServiceImpl(Adjuntos_fichaRepository adjuntos_fichaRepository,
            Adjuntos_fichaMapper adjuntos_fichaMapper, ApplicationProperties archivosProperties) {
        this.adjuntos_fichaRepository = adjuntos_fichaRepository;
        this.adjuntos_fichaMapper = adjuntos_fichaMapper;
        this.fileStorageLocation = Paths.get(archivosProperties.getArchivoProperties().getBaseUrl()).toAbsolutePath()
                .normalize();
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

    // Verificar si funciona con fileStorageLocation (que es la propiedad en
    // ApplicationProperties.java, que viene de appilication.yml)
    public void deleteAll() {
        //FileSystemUtils.deleteRecursively(rootLocation.toFile());
        FileSystemUtils.deleteRecursively(this.fileStorageLocation.toFile());
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
    public String[] obtenerArchivosFichaConsulta(Long idFicha) {
        String[] fileNames = this.adjuntos_fichaRepository.obtenerArchivosFichaConsulta(idFicha, "\\");
        log.debug("Nombres de archivos : {}", fileNames);
        return fileNames;
    }

    @Override
    public Resource loadFile(String filename) {
        return null;
    }

    @Override
    public Resource loadFileAsResource(String filename, HttpServletResponse request) {
        return getResource(filename, request);
    }

    /**
     * Este método se encarga de recueperar el archivo solicitado si existe
     * @filename es el nombre del archivo
     */
    public Resource getResource(String filename, HttpServletResponse request) {
        try {
            request.setContentType(MediaType.parseMediaType("application/octet-stream").toString());
            request.setHeader("Content-Disposition", "attachment; filename=" + filename);
            request.setHeader("filename", filename);

            Path filePath = this.fileStorageLocation.resolve(filename).normalize();

            Resource resource = new FileSystemResource(filePath.toFile());
            if (resource.exists()) {
                return resource;
            } else {
                log.error("no se pudo retornar el archivo");
            }
        } catch (Exception ex) {
            log.error("Error cargando archivo servicio", ex);
        }
        return null;
    }

    // Fin métodos para archivos
}
