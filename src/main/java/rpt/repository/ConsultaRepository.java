package rpt.repository;

import rpt.domain.Consulta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Consulta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
