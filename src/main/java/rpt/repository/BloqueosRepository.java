package rpt.repository;

import rpt.domain.Bloqueos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bloqueos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloqueosRepository extends JpaRepository<Bloqueos, Long> {

}
