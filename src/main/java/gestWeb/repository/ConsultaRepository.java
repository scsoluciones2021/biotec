package gestWeb.repository;

import gestWeb.domain.Consulta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data  repository for the Consulta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query(value = "SELECT c.* " +
    "FROM {h-schema}consulta c JOIN {h-schema}ficha_consulta r on c.id = r.idConsulta " + 
    "WHERE r.idFicha = ?1         " + 
    "order by c.fecha_consulta desc" ,
     nativeQuery = true)     
    List<Consulta> findConsultasXFicha(Long idFicha);
    /*void setFichaConsulta (Long idFicha, Long idConsulta) {
        entityManager.createNativeQuery("INSERT INTO {h-schema}ficha_consulta (idFicha, idConsulta) VALUES (?,?)")
        .setParameter(1, idFicha)
        .setParameter(2, idConsulta)
        .executeUpdate();
    }*/
    @Modifying
   // @Transactional
    @Query(value = "INSERT INTO {h-schema}ficha_consulta (idFicha, idConsulta) VALUES(?1,?2)", nativeQuery = true)
    void setFichaConsulta (Long idFicha, Long idConsulta);
    @Modifying
   // @Transactional
    @Query(value = "INSERT INTO {h-schema}tur_prof_pac_cons (idpaciente, idprofesional, idturno, idconsulta, idespecialidad) " +
        " values(?2, ?3, ?1, ?4, ?5)", 
        nativeQuery = true)
    void relacionTurnoProfPacCons(Long idTurno, Long idPaciente, Long idProfesional, Long idConsulta, Long idEspecialidad);

}
