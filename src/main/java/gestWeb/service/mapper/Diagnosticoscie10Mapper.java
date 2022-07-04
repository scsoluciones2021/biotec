package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.Diagnosticoscie10DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diagnosticoscie10 and its DTO Diagnosticoscie10DTO.
 */
 @Mapper(componentModel = "spring", uses = {})
public interface Diagnosticoscie10Mapper extends EntityMapper<Diagnosticoscie10DTO, Diagnosticoscie10> {

    @Mapping(target = "rel_categorias_diagnosticos_cie10", ignore = true)
    Diagnosticoscie10 toEntity(Diagnosticoscie10DTO diagnosticoscie10DTO);

    default Diagnosticoscie10 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnosticoscie10 diagnosticoscie10 = new Diagnosticoscie10();
        diagnosticoscie10.setId(id);
        return diagnosticoscie10;
    }
}
