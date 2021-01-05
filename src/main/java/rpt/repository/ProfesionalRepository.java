package rpt.repository;

import rpt.domain.Profesional;
import rpt.service.dto.ProfesionalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for the Profesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    @Query(value = "select distinct profesional from Profesional profesional left join fetch profesional.obrasocials left join fetch profesional.especialidads", countQuery = "select count(distinct profesional) from Profesional profesional")
    Page<Profesional> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct profesional from Profesional profesional left join fetch profesional.obrasocials left join fetch profesional.especialidads")
    List<Profesional> findAllWithEagerRelationships();

    @Query("select profesional from Profesional profesional left join fetch profesional.obrasocials left join fetch profesional.especialidads where profesional.id =:id")
    Optional<Profesional> findOneWithEagerRelationships(@Param("id") Long id);

    // búsqueda de profesionales para listados autocomplete
    @Query(value = "SELECT p FROM Profesional p WHERE p.nombreProfesional like %?1%")
    List<Profesional> findByN(String query);

    // búsqueda de profesionales para listados: excluye la , después del apellido
    // @Query(value = "SELECT p FROM Profesional p WHERE
    // REPLACE(p.nombreProfesional, ',', '') like %?1%")
    @Query(value = "SELECT p  " +
    " FROM Profesional p " +
    " where ( SUBSTRING_INDEX(?1, ',', 1) IS NULL " +
    "         or " +
    "         SUBSTRING_INDEX(nombreProfesional, ',', 1) like CONCAT('%', SUBSTRING_INDEX(?1, ',', 1), '%') ) " +
    " and  ( SUBSTRING_INDEX(SUBSTRING_INDEX(?1, ',', 2), ',', -1) IS NULL  " +
    "        or " +
    "        SUBSTRING_INDEX(SUBSTRING_INDEX(nombreProfesional,',', 2), ',', -1) like CONCAT('%', SUBSTRING_INDEX(SUBSTRING_INDEX(?1, ',', 2), ',', -1), '%') ) ")
    Page<Profesional> findByApeNomContaining(String ape,  Pageable pageable);
    

    /* Búsqueda de profesionales para listado en turnos
     No se utiliza, porque se cambió por findAllWithEagerRelationships
    @Query(value="SELECT distinct a.id, a.nombre_profesional, GROUP_CONCAT(c.id separator '|') as id_especialidad, GROUP_CONCAT(c.nombre_especialidad separator '|') as nombreEspecialidades from rompetodo.profesional a left join rompetodo.profesionaL_especialidad b on a.id = b.profesionals_id  left join rompetodo.especialidad c on b.especialidads_id = c.id group by a.id;")
	List<String> findAllCustom();*/

    @Query(value = "SELECT p FROM Profesional p order by p.nombreProfesional")
    List<Profesional> todos();

    @Query(value="select * from {h-schema}profesional p where p.usuario_id = ?1",
    nativeQuery = true)
	Optional<Profesional> findOneByUserId(Long id);
}
