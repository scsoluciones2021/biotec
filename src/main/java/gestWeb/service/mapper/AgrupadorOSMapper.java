package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.AgrupadorOSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AgrupadorOS and its DTO AgrupadorOSDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgrupadorOSMapper extends EntityMapper<AgrupadorOSDTO, AgrupadorOS> {


    @Mapping(target = "agrupador_obrasocials", ignore = true)
    AgrupadorOS toEntity(AgrupadorOSDTO agrupadorOSDTO);

    default AgrupadorOS fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgrupadorOS agrupadorOS = new AgrupadorOS();
        agrupadorOS.setId(id);
        return agrupadorOS;
    }
}
