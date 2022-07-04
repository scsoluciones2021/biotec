package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.EjercicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ejercicio and its DTO EjercicioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EjercicioMapper extends EntityMapper<EjercicioDTO, Ejercicio> {


    @Mapping(target = "antecedentesPersonales", ignore = true)
    Ejercicio toEntity(EjercicioDTO ejercicioDTO);

    default Ejercicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(id);
        return ejercicio;
    }
}
