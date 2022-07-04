package gestWeb.repository;

import gestWeb.domain.Diagnostico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Diagnostico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {

}
