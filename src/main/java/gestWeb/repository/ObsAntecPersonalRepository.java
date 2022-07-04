package gestWeb.repository;

import gestWeb.domain.ObsAntecPersonal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ObsAntecPersonal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObsAntecPersonalRepository extends JpaRepository<ObsAntecPersonal, Long> {

}
