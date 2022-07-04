package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.Subgruposcie10DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subgruposcie10 and its DTO Subgruposcie10DTO.
 */
 @Mapper(componentModel = "spring", uses = {})
public interface Subgruposcie10Mapper extends EntityMapper<Subgruposcie10DTO, Subgruposcie10> {

    @Mapping(target = "rel_grupo_subgrupo_cie10", ignore = true)
    Subgruposcie10 toEntity(Subgruposcie10DTO subgruposcie10DTO);

    default Subgruposcie10 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subgruposcie10 subgruposcie10 = new Subgruposcie10();
        subgruposcie10.setId(id);
        return subgruposcie10;
    }
}
