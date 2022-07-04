package gestWeb.repository;

import gestWeb.domain.PagoConsulta;
import gestWeb.service.dto.PagoConsultaDTO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PagoConsulta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagoConsultaRepository extends JpaRepository<PagoConsulta, Long> {

    @Query(value = "SELECT c.* " +
    "FROM {h-schema}pago_consulta c                       " +
    "inner join {h-schema}turno p   on c.pagoturno_id = p.id                    " +
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
	List<PagoConsulta> busqueda_filtros_impresion(String ape, String nom, String dni, String fech,
			List<String> esp, List<String> prof, Long usuarioCarga);

}
