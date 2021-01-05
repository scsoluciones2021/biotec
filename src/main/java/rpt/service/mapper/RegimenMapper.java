package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.RegimenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Regimen and its DTO RegimenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegimenMapper extends EntityMapper<RegimenDTO, Regimen> {



    default Regimen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regimen regimen = new Regimen();
        regimen.setId(id);
        return regimen;
    }
}
