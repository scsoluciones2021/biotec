package gestWeb.repository;

import gestWeb.domain.Enfermedad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enfermedad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnfermedadRepository extends JpaRepository<Enfermedad, Long> {

}
