package gestWeb.repository;

import gestWeb.domain.Ficha;
import gestWeb.service.dto.FichaDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ficha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {
    
    @Query(value = "SELECT f.* " +
    "FROM {h-schema}ficha f JOIN {h-schema}paciente p on f.paciente_id = p.id " + 
    "WHERE                                        " +
    "(?1 is null or p.apellido_paciente = ?1)         " +
    "and (?2 is null or p.nombre_paciente like %?2%)       " + 
    "and ((f.profesional_id in (?3)) is null or f.profesional_id in (?3))         " +
    "and ((f.especialidad_id in (?4)) is null or f.especialidad_id in (?4))       "  ,
     nativeQuery = true)
	Page<Ficha> findAllWithFilters(String apellido, String nombre, List<String> prof, List<String> esp, Pageable pageable);

  @Query(value = "SELECT EXISTS(" +
    "SELECT * FROM {h-schema}ficha f WHERE f.paciente_id = ?1) as resultado ",
     nativeQuery = true)
	Integer existeFichaIdPac(Long idPac);

  @Query(value = "SELECT f.id " +
    "from {h-schema}ficha f " +
    "where f.paciente_id = ?1 and f.profesional_id = ?2 and f.especialidad_id = ?3",
     nativeQuery = true)
  Long fichaRelPacProfEsp(Long idPaciente, Long idProfesional, Long idEspecialidad);

}
