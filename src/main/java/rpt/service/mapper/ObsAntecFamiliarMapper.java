package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.ObsAntecFamiliarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ObsAntecFamiliar and its DTO ObsAntecFamiliarDTO.
 */
@Mapper(componentModel = "spring", uses = {AntecedentesFamiliaresMapper.class})
public interface ObsAntecFamiliarMapper extends EntityMapper<ObsAntecFamiliarDTO, ObsAntecFamiliar> {

    @Mapping(source = "antecedentesFamiliares.id", target = "antecedentesFamiliaresId")
    ObsAntecFamiliarDTO toDto(ObsAntecFamiliar obsAntecFamiliar);

    @Mapping(source = "antecedentesFamiliaresId", target = "antecedentesFamiliares")
    ObsAntecFamiliar toEntity(ObsAntecFamiliarDTO obsAntecFamiliarDTO);

    default ObsAntecFamiliar fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObsAntecFamiliar obsAntecFamiliar = new ObsAntecFamiliar();
        obsAntecFamiliar.setId(id);
        return obsAntecFamiliar;
    }
}
