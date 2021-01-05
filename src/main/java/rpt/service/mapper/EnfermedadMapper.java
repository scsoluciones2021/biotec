package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.EnfermedadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enfermedad and its DTO EnfermedadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnfermedadMapper extends EntityMapper<EnfermedadDTO, Enfermedad> {



    default Enfermedad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setId(id);
        return enfermedad;
    }
}
