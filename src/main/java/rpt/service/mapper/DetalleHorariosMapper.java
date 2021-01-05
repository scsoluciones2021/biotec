package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.DetalleHorariosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetalleHorarios and its DTO DetalleHorariosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DetalleHorariosMapper extends EntityMapper<DetalleHorariosDTO, DetalleHorarios> {



    default DetalleHorarios fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleHorarios detalleHorarios = new DetalleHorarios();
        detalleHorarios.setId(id);
        return detalleHorarios;
    }
}
