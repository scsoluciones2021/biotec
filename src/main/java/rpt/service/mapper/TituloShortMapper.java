package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.TituloShortDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TituloShort and its DTO TituloShortDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TituloShortMapper extends EntityMapper<TituloShortDTO, TituloShort> {



    default TituloShort fromId(Long id) {
        if (id == null) {
            return null;
        }
        TituloShort tituloShort = new TituloShort();
        tituloShort.setId(id);
        return tituloShort;
    }
}
