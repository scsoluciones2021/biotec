package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.ObraSocialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ObraSocial and its DTO ObraSocialDTO.
 */
@Mapper(componentModel = "spring", uses = {AgrupadorOSMapper.class})
public interface ObraSocialMapper extends EntityMapper<ObraSocialDTO, ObraSocial> {


    @Mapping(source = "agrupador.id", target = "agrupadorId")
    @Mapping(source = "agrupador.nombre", target = "agrupadorNombre")
    ObraSocialDTO toDto(ObraSocial personal);

    @Mapping(source = "agrupadorId", target = "agrupador")
    ObraSocial toEntity(ObraSocialDTO obraSocialDTO);

    default ObraSocial fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setId(id);
        return obraSocial;
    }
}
