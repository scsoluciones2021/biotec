package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.PagoConsultaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PagoConsulta} and its DTO {@link PagoConsultaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TurnoMapper.class})
public interface PagoConsultaMapper extends EntityMapper<PagoConsultaDTO, PagoConsulta> {

    @Mapping(source = "pagoturno.id", target = "pagoturnoId")
    PagoConsultaDTO toDto(PagoConsulta pagoConsulta);

    @Mapping(source = "pagoturnoId", target = "pagoturno")
    PagoConsulta toEntity(PagoConsultaDTO pagoConsultaDTO);

    default PagoConsulta fromId(Long id) {
        if (id == null) {
            return null;
        }
        PagoConsulta pagoConsulta = new PagoConsulta();
        pagoConsulta.setId(id);
        return pagoConsulta;
    }
}
