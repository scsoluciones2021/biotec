package gestWeb.repository;

import gestWeb.domain.Intolerancia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Intolerancia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntoleranciaRepository extends JpaRepository<Intolerancia, Long> {

}
