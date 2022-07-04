package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.PersonalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Personal and its DTO PersonalDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class})
public interface PersonalMapper extends EntityMapper<PersonalDTO, Personal> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    PersonalDTO toDto(Personal personal);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "empresaId", target = "empresa")
    Personal toEntity(PersonalDTO personalDTO);

    default Personal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Personal personal = new Personal();
        personal.setId(id);
        return personal;
    }
}
