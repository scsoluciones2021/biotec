package rpt.repository;

import rpt.domain.ObsAntecFamiliar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ObsAntecFamiliar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObsAntecFamiliarRepository extends JpaRepository<ObsAntecFamiliar, Long> {

}
