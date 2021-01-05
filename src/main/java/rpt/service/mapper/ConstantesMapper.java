package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.ConstantesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Constantes and its DTO ConstantesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConstantesMapper extends EntityMapper<ConstantesDTO, Constantes> {



    default Constantes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Constantes constantes = new Constantes();
        constantes.setId(id);
        return constantes;
    }
}
