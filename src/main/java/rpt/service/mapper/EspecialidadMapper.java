package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.EspecialidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Especialidad and its DTO EspecialidadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EspecialidadMapper extends EntityMapper<EspecialidadDTO, Especialidad> {


    @Mapping(target = "profesionals", ignore = true)
    Especialidad toEntity(EspecialidadDTO especialidadDTO);

    default Especialidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Especialidad especialidad = new Especialidad();
        especialidad.setId(id);
        return especialidad;
    }
}
