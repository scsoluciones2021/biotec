package gestWeb.repository;

import gestWeb.domain.Profesional;
import gestWeb.service.dto.ProfesionalDTO;

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

    @Query(value=" SELECT Upper(p) FROM Profesional p WHERE (?1 IS NULL or UPPER(p.apellidoProfesional) like %?1%) and (?2 IS NULL or UPPER(p.nombreProfesional) like %?2%)")
    Page<Profesional> findByApeNomContaining(String ape, String nom,  Pageable pageable);
    

    @Query(value = "SELECT p FROM Profesional p order by p.apellidoProfesional, p.nombreProfesional")
    List<Profesional> todos();

    @Query(value="select * from {h-schema}profesional p where p.usuario_id = ?1",
    nativeQuery = true)
	Optional<Profesional> findOneByUserId(Long id);

    @Modifying
    @Query(value="update {h-schema}profesional set usuario_id = ?2 where id = ?1",
    nativeQuery = true)
	void updateUserId(Long idProfesional, Long idUsuario);

    @Query(value="select case when (p.usuario_id is null)  then 'true' else 'false' end "+
     " from {h-schema}profesional p where p.id = ?1",
    nativeQuery = true)
	Boolean searchProfesionalWithoutUser(Long id);
    
    @Modifying
    @Query(value="update {h-schema}profesional set usuario_id = null where usuario_id = ?1",
    nativeQuery = true)
	void updateUserIdEliminado(Long idUsuario);
}
