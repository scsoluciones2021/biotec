package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.AlergiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Alergia and its DTO AlergiaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlergiaMapper extends EntityMapper<AlergiaDTO, Alergia> {



    default Alergia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alergia alergia = new Alergia();
        alergia.setId(id);
        return alergia;
    }
}
