package gestWeb.repository;

import gestWeb.domain.Familiar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Familiar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

}
