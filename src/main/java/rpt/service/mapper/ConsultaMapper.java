package rpt.service.mapper;

import rpt.domain.*;
import rpt.service.dto.ConsultaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consulta and its DTO ConsultaDTO.
 */
@Mapper(componentModel = "spring", uses = {ConstantesMapper.class})
public interface ConsultaMapper extends EntityMapper<ConsultaDTO, Consulta> {

    @Mapping(source = "constantesConsulta.id", target = "constantesConsultaId")
    ConsultaDTO toDto(Consulta consulta);

    @Mapping(source = "constantesConsultaId", target = "constantesConsulta")
    // @Mapping(target = "fichas", ignore = true)
    Consulta toEntity(ConsultaDTO consultaDTO);

    default Consulta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consulta consulta = new Consulta();
        consulta.setId(id);
        return consulta;
    }
}
