package gestWeb.service.mapper;

import gestWeb.domain.*;
import gestWeb.service.dto.HorariosProfesionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HorariosProfesional and its DTO HorariosProfesionalDTO.
 */
@Mapper(componentModel = "spring", uses = {EspecialidadMapper.class, ProfesionalMapper.class, BloqueosMapper.class})
public interface HorariosProfesionalMapper extends EntityMapper<HorariosProfesionalDTO, HorariosProfesional> {

    @Mapping(source = "horario_esp_rel.id", target = "horario_esp_relId")
    @Mapping(source = "horario_esp_rel.nombreEspecialidad", target = "horario_esp_relNombreEspecialidad")
    @Mapping(source = "horario_prof_rel.id", target = "horario_prof_relId")
    @Mapping(source = "horario_prof_rel.nombreProfesional", target = "horario_prof_relNombreProfesional")
    HorariosProfesionalDTO toDto(HorariosProfesional horariosProfesional);

    @Mapping(source = "horario_esp_relId", target = "horario_esp_rel")
    @Mapping(source = "horario_prof_relId", target = "horario_prof_rel")
    HorariosProfesional toEntity(HorariosProfesionalDTO horariosProfesionalDTO);

    default HorariosProfesional fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorariosProfesional horariosProfesional = new HorariosProfesional();
        horariosProfesional.setId(id);
        return horariosProfesional;
    }
}
