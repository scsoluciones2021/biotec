package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.Gruposcie10DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gruposcie10 and its DTO Gruposcie10DTO.
 */
 @Mapper(componentModel = "spring", uses = {})
public interface Gruposcie10Mapper extends EntityMapper<Gruposcie10DTO, Gruposcie10> {

    default Gruposcie10 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gruposcie10 gruposcie10 = new Gruposcie10();
        gruposcie10.setId(id);
        return gruposcie10;
    }
}
