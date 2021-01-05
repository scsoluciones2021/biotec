package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paciente and its DTO PacienteDTO.
 */
@Mapper(componentModel = "spring", uses = {ObraSocialMapper.class, UserMapper.class, CodigoPostalMapper.class, ProvinciaMapper.class})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    @Mapping(source = "provincia.id", target = "provinciaId")
    @Mapping(source = "provincia.nombreProvincia", target = "provinciaNombreProvincia")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "codigoPostal.id", target = "codigoPostalId")
    @Mapping(source = "codigoPostal.nombreCiudad", target = "codigoPostalNombreCiudad")
    PacienteDTO toDto(Paciente paciente);

    @Mapping(source = "provinciaId", target = "provincia")
    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "fichas", ignore = true)
    @Mapping(source = "codigoPostalId", target = "codigoPostal")
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
