package rpt.repository;

import rpt.domain.Especialidad;
import rpt.service.dto.EspecialidadDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Especialidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    @Query(value = "SELECT p FROM Especialidad p WHERE p.nombreEspecialidad like %?1% or p.codigoEspecilidad like %?1%")
    Page<Especialidad> findByNombreContaining(String query, Pageable pageable);


    @Query(value = "SELECT p FROM Especialidad p WHERE p.nombreEspecialidad like %?1% or p.codigoEspecilidad like %?1%")
    List<Especialidad> findByNombreCodigo (String query);

    @Query(value = "SELECT p.* FROM especialidad p join profesional_especialidad r on p.id = r.especialidads_id and r.profesionals_id = ?1 ", 
           nativeQuery = true)
	List<Especialidad> espeProfesional(Long id);
}
