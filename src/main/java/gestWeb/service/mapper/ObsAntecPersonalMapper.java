package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.ObsAntecPersonalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ObsAntecPersonal and its DTO ObsAntecPersonalDTO.
 */
@Mapper(componentModel = "spring", uses = {AntecedentesPersonalesMapper.class})
public interface ObsAntecPersonalMapper extends EntityMapper<ObsAntecPersonalDTO, ObsAntecPersonal> {

    @Mapping(source = "antecedentesPersonales.id", target = "antecedentesPersonalesId")
    ObsAntecPersonalDTO toDto(ObsAntecPersonal obsAntecPersonal);

    @Mapping(source = "antecedentesPersonalesId", target = "antecedentesPersonales")
    ObsAntecPersonal toEntity(ObsAntecPersonalDTO obsAntecPersonalDTO);

    default ObsAntecPersonal fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObsAntecPersonal obsAntecPersonal = new ObsAntecPersonal();
        obsAntecPersonal.setId(id);
        return obsAntecPersonal;
    }
}
