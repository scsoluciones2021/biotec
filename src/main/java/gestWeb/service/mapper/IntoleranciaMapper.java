package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.IntoleranciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Intolerancia and its DTO IntoleranciaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntoleranciaMapper extends EntityMapper<IntoleranciaDTO, Intolerancia> {



    default Intolerancia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intolerancia intolerancia = new Intolerancia();
        intolerancia.setId(id);
        return intolerancia;
    }
}
