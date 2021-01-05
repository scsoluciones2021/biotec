package rpt.repository;

import rpt.domain.AgrupadorOS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AgrupadorOS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgrupadorOSRepository extends JpaRepository<AgrupadorOS, Long> {

}
