package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Empresa and its DTO EmpresaDTO.
 */
@Mapper(componentModel = "spring", uses = {CodigoPostalMapper.class})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {

    @Mapping(source = "codigoPostal.id", target = "codigoPostalId")
    @Mapping(source = "codigoPostal.nombreCiudad", target = "codigoPostalNombreCiudad")
    EmpresaDTO toDto(Empresa empresa);

    @Mapping(target = "personals", ignore = true)
    @Mapping(source = "codigoPostalId", target = "codigoPostal")
    Empresa toEntity(EmpresaDTO empresaDTO);

    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
