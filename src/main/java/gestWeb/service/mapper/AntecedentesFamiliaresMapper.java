package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.AntecedentesFamiliaresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AntecedentesFamiliares and its DTO AntecedentesFamiliaresDTO.
 */
@Mapper(componentModel = "spring", uses = {FamiliarMapper.class})
public interface AntecedentesFamiliaresMapper extends EntityMapper<AntecedentesFamiliaresDTO, AntecedentesFamiliares> {

    @Mapping(source = "parentezco.id", target = "parentezcoId")
    @Mapping(source = "parentezco.parentezco", target = "parentezcoParentezco")
    AntecedentesFamiliaresDTO toDto(AntecedentesFamiliares antecedentesFamiliares);

    @Mapping(target = "obsantecFamiliars", ignore = true)
    @Mapping(source = "parentezcoId", target = "parentezco")
    AntecedentesFamiliares toEntity(AntecedentesFamiliaresDTO antecedentesFamiliaresDTO);

    default AntecedentesFamiliares fromId(Long id) {
        if (id == null) {
            return null;
        }
        AntecedentesFamiliares antecedentesFamiliares = new AntecedentesFamiliares();
        antecedentesFamiliares.setId(id);
        return antecedentesFamiliares;
    }
}
