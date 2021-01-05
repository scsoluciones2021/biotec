package rpt.repository;

import rpt.domain.Alergia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Alergia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlergiaRepository extends JpaRepository<Alergia, Long> {

}
