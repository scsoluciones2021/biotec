package rpt.repository;

import rpt.domain.HorariosProfesional;
import rpt.service.dto.HorariosProfesionalDTO;

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

}
