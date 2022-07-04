package gestWeb.repository;

import gestWeb.domain.HorariosProfesional;
import gestWeb.service.dto.HorariosProfesionalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the HorariosProfesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorariosProfesionalRepository extends JpaRepository<HorariosProfesional, Long> {

    @Query(value = "select distinct horarios_profesional from HorariosProfesional horarios_profesional left join fetch horarios_profesional.horario_bloq_rels",
        countQuery = "select count(distinct horarios_profesional) from HorariosProfesional horarios_profesional")
    Page<HorariosProfesional> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct horarios_profesional from HorariosProfesional horarios_profesional left join fetch horarios_profesional.horario_bloq_rels")
    List<HorariosProfesional> findAllWithEagerRelationships();

    @Query("select horarios_profesional from HorariosProfesional horarios_profesional left join fetch horarios_profesional.horario_bloq_rels where horarios_profesional.id =:id")
    Optional<HorariosProfesional> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct horarios_profesional from HorariosProfesional horarios_profesional where horario_prof_rel_id = ?1 and horario_esp_rel_id = ?2")
	List<HorariosProfesional> findXProf(Long idProf, Long idEsp);

    @Query(value = "SELECT hp.* FROM {h-schema}horarios_profesional hp " +
                   "inner join {h-schema}especialidad e on hp.horario_esp_rel_id = e.id " +
                   "inner join {h-schema}profesional p on hp.horario_prof_rel_id = p.id " +
                   "WHERE (?1 IS NULL or p.nombre_profesional like %?1%) and (?2 IS NULL or e.nombre_especialidad like %?2%)", 
     nativeQuery = true)
	Page<HorariosProfesional> findHorariosProfesional(String nombre, String especialidad, Pageable pageable);

}
