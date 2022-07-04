package gestWeb.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import gestWeb.domain.Ficha;
import gestWeb.repository.FichaRepository;
import gestWeb.service.Adjuntos_fichaService;
import gestWeb.service.FichaService;
import gestWeb.service.dto.FichaDTO;
import gestWeb.service.mapper.FichaMapper;

/**
 * Service Implementation for managing Ficha.
 */
@Service
@Transactional
public class FichaServiceImpl implements FichaService {

    private final Logger log = LoggerFactory.getLogger(FichaServiceImpl.class);

    private final FichaRepository fichaRepository;

    private final FichaMapper fichaMapper;

    private final Path rootLocation = Paths.get("upload/Fichas");

    public FichaServiceImpl(FichaRepository fichaRepository, FichaMapper fichaMapper,
            Adjuntos_fichaService adj_fichService) {
        this.fichaRepository = fichaRepository;
        this.fichaMapper = fichaMapper;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {

        }
    }

    /**
     * Save a ficha.
     *
     * @param fichaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FichaDTO save(FichaDTO fichaDTO) {
        Ficha ficha = fichaMapper.toEntity(fichaDTO);
        ficha = fichaRepository.save(ficha);
        return fichaMapper.toDto(ficha);
    }

    /**
     * Get all the fichas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FichaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fichas");
        return fichaRepository.findAll(pageable).map(fichaMapper::toDto);
    }

    /**
     * Get one ficha by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FichaDTO> findOne(Long id) {
        log.debug("Request to get Ficha : {}", id);
        return fichaRepository.findById(id).map(fichaMapper::toDto);
    }

    /**
     * Delete the ficha by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ficha : {}", id);
        fichaRepository.deleteById(id);
    }

    // Métodos para subir / ver archivos cargados
    @Override
    public String store(MultipartFile file, Long idFicha, Integer nroImagen) {
        try {

            String fileName = handleFileName(file.getOriginalFilename(), idFicha, nroImagen);
            Path path = Paths.get(this.rootLocation.toString(), fileName);
            // System.out.println("Nro Im.: "+nroImagen + " Nombre Archivo: " +
            // file.getOriginalFilename());
            // File nuevo =new File(fileName);

            Files.copy(file.getInputStream(), path);
            // Files.copy(file.getInputStream(), nuevo.toPath(),
            // StandardCopyOption.REPLACE_EXISTING);
            // Files.move(original.toPath(), nuevo.toPath());
            return path.toString();
            // Files.copy(file.getInputStream(),
            // this.rootLocation.resolve(file.getOriginalFilename()));

            // Path source = this.rootLocation.resolve(file.getOriginalFilename());

            // Files.move(source, source.resolveSibling("nuevoNombre.png"));
        } catch (Exception e) {
            return null;
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    // Método para armar el nombre nuevo
    private String handleFileName(String fileName, Long idFicha, Integer nroImagen) {

        String cleanFileName = fileName.replaceAll("[^A-Za-z0-9.()]", "");
        String extension = getFileExtension(cleanFileName);
        String base = cleanFileName.substring(0, cleanFileName.length() - extension.length() - 1);
        Integer nroCons = 1;

        base = idFicha + "_" + nroCons + "_" + nroImagen.intValue();
        cleanFileName = base + "." + extension;

        return cleanFileName;
    }

    public Path loadFile(String filename) {
        Path file = Paths.get(rootLocation.toString(), filename);// rootLocation.resolve(filename);
        return file;
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
   // public Page<FichaDTO> findAllWithFilters(String apellido, String nombre, Long profesionales,  List<String> especialidades, Pageable pageable) {
    public Page<FichaDTO> findAllWithFilters(String apellido, String nombre, List<String> profesionales, List<String> especialidades,Pageable pageable) {

        List<String> esp = null;
        List<String> prof = null;
        String ape = null;
        String nom = null;

        if (!apellido.isEmpty() && !apellido.equals("undefined")) {
            ape = apellido;
        }

        if (!nombre.isEmpty()  && !nombre.equals("undefined")) {
            nom = nombre;
        }

        if (!profesionales.isEmpty()) {
            prof = profesionales;
        }

        if (!especialidades.isEmpty()) {
            esp = especialidades;
        }

      return fichaRepository.findAllWithFilters(ape, nom, prof, esp, pageable).map(fichaMapper::toDto);

    }

    @Override
    public Integer existeFichaIdPac(Long idPac) {
        return fichaRepository.existeFichaIdPac(idPac);
    }

    @Override
    public Long fichaRelPacProfEsp(Long idPaciente, Long idProfesional, Long idEspecialidad) {
        return fichaRepository.fichaRelPacProfEsp(idPaciente, idProfesional, idEspecialidad);
    }
    
    // Fin métodos para archivos
}