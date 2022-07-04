package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.Categoriascie10DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Categoriascie10 and its DTO Categoriascie10DTO.
 */
 @Mapper(componentModel = "spring", uses = {})
public interface Categoriascie10Mapper extends EntityMapper<Categoriascie10DTO, Categoriascie10> {

    @Mapping(target = "rel_subrupos_categorias_cie10", ignore = true)
    Categoriascie10 toEntity(Categoriascie10DTO categoriascie10DTO);

    default Categoriascie10 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoriascie10 categoriascie10 = new Categoriascie10();
        categoriascie10.setId(id);
        return categoriascie10;
    }
}
