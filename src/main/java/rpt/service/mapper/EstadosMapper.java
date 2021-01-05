package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.EstadosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Estados and its DTO EstadosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadosMapper extends EntityMapper<EstadosDTO, Estados> {



    default Estados fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estados estados = new Estados();
        estados.setId(id);
        return estados;
    }
}
