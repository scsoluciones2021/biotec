package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.FamiliarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Familiar and its DTO FamiliarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FamiliarMapper extends EntityMapper<FamiliarDTO, Familiar> {


    @Mapping(target = "antecedentesFamiliares", ignore = true)
    Familiar toEntity(FamiliarDTO familiarDTO);

    default Familiar fromId(Long id) {
        if (id == null) {
            return null;
        }
        Familiar familiar = new Familiar();
        familiar.setId(id);
        return familiar;
    }
}
