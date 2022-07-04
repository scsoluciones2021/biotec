package gestWeb.service.impl;

import gestWeb.service.ConsultaService;
import gestWeb.domain.Consulta;
import gestWeb.repository.ConsultaRepository;
import gestWeb.service.dto.ConsultaDTO;
import gestWeb.service.mapper.ConsultaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.FileSystemUtils;
/**
 * Service Implementation for managing Consulta.
 */
@Service
@Transactional
public class ConsultaServiceImpl implements ConsultaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaServiceImpl.class);

    private final ConsultaRepository consultaRepository;

    private final ConsultaMapper consultaMapper;

	private final Path rootLocation = Paths.get("upload");

    public ConsultaServiceImpl(ConsultaRepository consultaRepository, ConsultaMapper consultaMapper) {
        this.consultaRepository = consultaRepository;
        this.consultaMapper = consultaMapper;
        try {
            Files.createDirectory(rootLocation);
		} catch (IOException e) {
			
		}
    }

    /**
     * Save a consulta.
     *
     * @param consultaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultaDTO save(ConsultaDTO consultaDTO) {
        log.debug("Request to save Consulta : {}", consultaDTO);
        Consulta consulta = consultaMapper.toEntity(consultaDTO);
        consulta = consultaRepository.save(consulta);
        return consultaMapper.toDto(consulta);
    }

    /**
     * Get all the consultas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultaDTO> findAll() {
        log.debug("Request to get all Consultas");
        return consultaRepository.findAll().stream()
            .map(consultaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one consulta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultaDTO> findOne(Long id) {
        log.debug("Request to get Consulta : {}", id);
        return consultaRepository.findById(id)
            .map(consultaMapper::toDto);
    }

    /**
     * Delete the consulta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consulta : {}", id);
        consultaRepository.deleteById(id);
    }

    // Métodos para subir / ver archivos cargados
    @Override
    public void store(MultipartFile file) {
        try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
    }
    
    public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
            log.debug("ruta: " + file);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
 
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public List<ConsultaDTO> findConsultasXFicha(Long idFicha) {
        return consultaRepository.findConsultasXFicha(idFicha).stream()
            .map(consultaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void setFichaConsulta(Long idFicha, Long idConsulta) {
        log.debug("Request to setFichaConsulta Turno ficha : {}", idFicha);
        log.debug("Request to setFichaConsulta Turno consulta : {}", idConsulta);

        consultaRepository.setFichaConsulta(idFicha, idConsulta);
    }

    @Override
    public void relacionTurnoProfPacCons(Long idTurno, Long idPaciente, Long idProfesional, Long idConsulta, Long idEspecialidad){
        log.debug("Request to relacionTurnoProfPac Turno paciente : {}", idPaciente);
        log.debug("Request to relacionTurnoProfPac Turno profesional : {}", idProfesional);
        log.debug("Request to relacionTurnoProfPac Turno turno : {}", idTurno);
        log.debug("Request to relacionTurnoProfPac Turno consulta : {}", idConsulta);
        log.debug("Request to relacionTurnoProfPac Turno especialidad : {}", idEspecialidad);

        consultaRepository.relacionTurnoProfPacCons(idTurno, idPaciente, idProfesional, idConsulta, idEspecialidad);
    }
 
}
