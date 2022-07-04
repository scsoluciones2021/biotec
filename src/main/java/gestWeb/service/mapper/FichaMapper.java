package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.FichaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ficha and its DTO FichaDTO.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class, ProfesionalMapper.class, EspecialidadMapper.class,  ConsultaMapper.class})
public interface FichaMapper extends EntityMapper<FichaDTO, Ficha> {
// log.debug("Paciente en ficha mapper", paciente);
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.nombrePaciente", target = "pacienteNombrePaciente")
    @Mapping(source = "paciente.apellidoPaciente", target = "pacienteApellidoPaciente")
    @Mapping(source = "profesional.id", target = "profesionalId")
    @Mapping(source = "profesional.nombreProfesional", target = "profesionalNombreProfesional")
    @Mapping(source = "especialidad.nombreEspecialidad", target = "especialidadNombreEspecialidad")
    @Mapping(source = "especialidad.id", target = "especialidadId")
    // @Mapping(source = "consulta.id", target = "consultaId")
    FichaDTO toDto(Ficha ficha);

    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "profesionalId", target = "profesional")
    @Mapping(source = "especialidadId", target = "especialidad")
    // @Mapping(source = "consultaId", target = "consulta")
    Ficha toEntity(FichaDTO fichaDTO);

    default Ficha fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ficha ficha = new Ficha();
        ficha.setId(id);
        return ficha;
    }
}
