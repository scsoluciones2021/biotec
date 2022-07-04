package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.CodigoPostalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CodigoPostal and its DTO CodigoPostalDTO.
 */
@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface CodigoPostalMapper extends EntityMapper<CodigoPostalDTO, CodigoPostal> {

    @Mapping(source = "provincia.id", target = "provinciaId")
    @Mapping(source = "provincia.nombreProvincia", target = "provinciaNombreProvincia")
    CodigoPostalDTO toDto(CodigoPostal codigoPostal);

    @Mapping(target = "empresas", ignore = true)
    @Mapping(target = "pacientes", ignore = true)
    @Mapping(target = "profesionals", ignore = true)
    @Mapping(source = "provinciaId", target = "provincia")
    CodigoPostal toEntity(CodigoPostalDTO codigoPostalDTO);

    default CodigoPostal fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setId(id);
        return codigoPostal;
    }
}
