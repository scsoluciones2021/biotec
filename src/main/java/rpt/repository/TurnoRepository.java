package rpt.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rpt.domain.Turno;


/**
 * Spring Data  repository for the Turno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query(value = "SELECT *                     " +
    "FROM {h-schema}turno p                       " +
    "WHERE                                        " +
    "(?1 is null or p.apellido like %?1%)         " +
    "and (?2 is null or p.nombre like %?2%)       " +
    "and (?3 is null or p.dni like %?3%)           " +
    "and (?4 is null or p.dia = ?4)               " +
    "and ((p.tur_esp_rel_id in (?5))is null or (p.tur_esp_rel_id in (?5))) " +
    "and ((p.tur_prof_rel_id in (?6))is null or (p.tur_prof_rel_id in (?6))) " +
    "and (?7 = -1 or p.usuario = ?7) and p.estado <> 5"+
    " order by dia, hora ", 
     nativeQuery = true)
    Page<Turno> busqueda_filtros(String ape, String nom, String dni, String fech, List<String> esp, List<String> prof, Long usuario, Pageable pageable);

    @Query(value = "select horarios_ocupados as horario from {h-schema}turno t where tur_prof_rel_id = ?1 and tur_esp_rel_id = ?2 and dia= ?3 and estado <> 5 order by horario",
     nativeQuery = true)
     List<String> horariosOcupadosProf(Long idProf, Long idEsp, String dia);

     // @Query(value = "SELECT id, dni, apellido, nombre, telefono, email, dia, hora, id_horario, tur_esp_rel_id, tur_prof_rel_id, tipo_paciente, tur_obs_rel_id, horarios_ocupados, usuario, estado " +
     @Query(value = "SELECT * " +
     "FROM {h-schema}turno p                       " +
     "WHERE                                        " +
     "(?1 is null or p.apellido like %?1%)         " +
     "and (?2 is null or p.nombre like %?2%)       " +
     "and (?3 is null or p.dni like %?3%)           " +
     "and (?4 is null or p.dia = ?4)               " +
     "and ((p.tur_esp_rel_id in (?5))is null or (p.tur_esp_rel_id in (?5))) " +
     "and ((p.tur_prof_rel_id in (?6))is null or (p.tur_prof_rel_id in (?6))) " +
     "and (?7 = -1 or p.usuario = ?7) and p.estado <> 5" +
     " order by dia, hora ", 
      nativeQuery = true)
	List<Turno> busqueda_filtros_impresion(String ape, String nom, String dni, String fech, List<String> esp,
            List<String> prof, Long usuario);
    
    //1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado"
    @Modifying
    @Query(value = "UPDATE {h-schema}turno " +
        " set estado = ?2 "+
        " where id = ?1", nativeQuery = true
        )
    void cambiarEstado(Long id, Integer estado);

    @Query(value = "SELECT * " +
     "FROM {h-schema}turno p                       " +
     "WHERE                                        " +
     "(?1 is null or p.dia = ?1)               " +
     "and ((p.tur_esp_rel_id in (?3))is null or (p.tur_esp_rel_id in (?3))) " +
     "and ((p.tur_prof_rel_id in (?2))is null or (p.tur_prof_rel_id in (?2))) " +
     " order by dia, hora ", 
      nativeQuery = true)
	Page<Turno> busqueda_medico(LocalDate fecha, Long profesional, List<String> esp, Integer estado,
			Pageable pageable);
    
}
