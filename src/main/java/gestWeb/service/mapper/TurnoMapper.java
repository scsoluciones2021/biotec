package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.TurnoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Turno and its DTO TurnoDTO.
 */
@Mapper(componentModel = "spring", uses = {EspecialidadMapper.class, ProfesionalMapper.class, ObraSocialMapper.class})
public interface TurnoMapper extends EntityMapper<TurnoDTO, Turno> {

    @Mapping(source = "tur_esp_rel.id", target = "tur_esp_relId")
    @Mapping(source = "tur_esp_rel.nombreEspecialidad", target = "tur_esp_relNombreEspecialidad")
    @Mapping(source = "tur_prof_rel.id", target = "tur_prof_relId")
    @Mapping(source = "tur_prof_rel.nombreProfesional", target = "tur_prof_relNombreProfesional")
    @Mapping(source = "tur_obs_rel.id", target = "tur_obs_relId")
    @Mapping(source = "tur_obs_rel.nombreObraSocial", target = "tur_obs_relNombreObraSocial")
  //  @Mapping(source = "usuario", target = "usuarioCarga")
    TurnoDTO toDto(Turno turno);

    @Mapping(source = "tur_esp_relId", target = "tur_esp_rel")
    @Mapping(source = "tur_prof_relId", target = "tur_prof_rel")
    @Mapping(source = "tur_obs_relId", target = "tur_obs_rel")
   // @Mapping(source = "usuarioCarga", target = "usuario")
    Turno toEntity(TurnoDTO turnoDTO);

    default Turno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Turno turno = new Turno();
        turno.setId(id);
        return turno;
    }
}
