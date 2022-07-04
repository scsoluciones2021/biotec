package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.BebidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bebida and its DTO BebidaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BebidaMapper extends EntityMapper<BebidaDTO, Bebida> {


    @Mapping(target = "antecedentesPersonales", ignore = true)
    Bebida toEntity(BebidaDTO bebidaDTO);

    default Bebida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bebida bebida = new Bebida();
        bebida.setId(id);
        return bebida;
    }
}
