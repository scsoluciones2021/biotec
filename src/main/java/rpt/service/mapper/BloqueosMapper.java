package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.BloqueosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bloqueos and its DTO BloqueosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BloqueosMapper extends EntityMapper<BloqueosDTO, Bloqueos> {



    default Bloqueos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bloqueos bloqueos = new Bloqueos();
        bloqueos.setId(id);
        return bloqueos;
    }
}
