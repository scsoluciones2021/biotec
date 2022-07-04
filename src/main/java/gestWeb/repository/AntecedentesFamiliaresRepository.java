package gestWeb.repository;

import gestWeb.domain.AntecedentesFamiliares;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AntecedentesFamiliares entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntecedentesFamiliaresRepository extends JpaRepository<AntecedentesFamiliares, Long> {

}
