package rpt.repository;

import rpt.domain.Constantes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Constantes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConstantesRepository extends JpaRepository<Constantes, Long> {

}
