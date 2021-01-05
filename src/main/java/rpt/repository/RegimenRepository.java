package rpt.repository;

import rpt.domain.Regimen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Regimen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Long> {

}
