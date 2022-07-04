package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.AntecedentesPersonalesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AntecedentesPersonales and its DTO AntecedentesPersonalesDTO.
 */
@Mapper(componentModel = "spring", uses = {EnfermedadMapper.class, AlergiaMapper.class, IntoleranciaMapper.class, RegimenMapper.class, EjercicioMapper.class, BebidaMapper.class})
public interface AntecedentesPersonalesMapper extends EntityMapper<AntecedentesPersonalesDTO, AntecedentesPersonales> {

    @Mapping(source = "ejercicios.id", target = "ejerciciosId")
    @Mapping(source = "ejercicios.valor", target = "ejerciciosValor")
    @Mapping(source = "bebidas.id", target = "bebidasId")
    @Mapping(source = "bebidas.valor", target = "bebidasValor")
    AntecedentesPersonalesDTO toDto(AntecedentesPersonales antecedentesPersonales);

    @Mapping(target = "obsantecPersonals", ignore = true)
    @Mapping(source = "ejerciciosId", target = "ejercicios")
    @Mapping(source = "bebidasId", target = "bebidas")
    AntecedentesPersonales toEntity(AntecedentesPersonalesDTO antecedentesPersonalesDTO);

    default AntecedentesPersonales fromId(Long id) {
        if (id == null) {
            return null;
        }
        AntecedentesPersonales antecedentesPersonales = new AntecedentesPersonales();
        antecedentesPersonales.setId(id);
        return antecedentesPersonales;
    }
}
