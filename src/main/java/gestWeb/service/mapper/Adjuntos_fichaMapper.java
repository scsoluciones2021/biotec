package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.Adjuntos_fichaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Adjuntos_ficha and its DTO Adjuntos_fichaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Adjuntos_fichaMapper extends EntityMapper<Adjuntos_fichaDTO, Adjuntos_ficha> {



    default Adjuntos_ficha fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adjuntos_ficha adjuntos_ficha = new Adjuntos_ficha();
        adjuntos_ficha.setId(id);
        return adjuntos_ficha;
    }
}
