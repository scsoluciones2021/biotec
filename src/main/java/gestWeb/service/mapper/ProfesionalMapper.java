package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.ProfesionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profesional and its DTO ProfesionalDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TituloShortMapper.class, ObraSocialMapper.class, EspecialidadMapper.class, CodigoPostalMapper.class})
public interface ProfesionalMapper extends EntityMapper<ProfesionalDTO, Profesional> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "titulo.id", target = "tituloId")
    @Mapping(source = "titulo.valor", target = "tituloValor")
    @Mapping(source = "codigoPostal.id", target = "codigoPostalId")
    @Mapping(source = "codigoPostal.nombreCiudad", target = "codigoPostalNombreCiudad")
    ProfesionalDTO toDto(Profesional profesional);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "tituloId", target = "titulo")
    @Mapping(target = "fichas", ignore = true)
    @Mapping(source = "codigoPostalId", target = "codigoPostal")
    Profesional toEntity(ProfesionalDTO profesionalDTO);

    default Profesional fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profesional profesional = new Profesional();
        profesional.setId(id);
        return profesional;
    }
}
