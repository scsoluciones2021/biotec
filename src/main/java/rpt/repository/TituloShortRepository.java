package rpt.repository;

import rpt.domain.TituloShort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TituloShort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TituloShortRepository extends JpaRepository<TituloShort, Long> {

}
