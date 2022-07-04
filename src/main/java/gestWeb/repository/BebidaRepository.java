package gestWeb.repository;

import gestWeb.domain.Bebida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bebida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {

}
