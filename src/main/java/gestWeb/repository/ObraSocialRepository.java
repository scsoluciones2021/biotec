package gestWeb.repository;

import gestWeb.domain.ObraSocial;
import gestWeb.service.dto.ObraSocialDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Spring Data  repository for the ObraSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObraSocialRepository extends JpaRepository<ObraSocial, Long> {

    @Query(value = "SELECT p FROM ObraSocial p WHERE p.nombreObraSocial like %?1% or p.codigoObraSocial like %?1%")
    List<ObraSocial> findByNombreCodigo (String query);

   // @Query(value = "SELECT p FROM ObraSocial p  where trim(siglaObraSocial) != '' order by p.nombreObraSocial")
    @Query(value = "SELECT p FROM ObraSocial p order by p.nombreObraSocial")
    List<ObraSocial> todas();

    @Query(value = "SELECT p FROM ObraSocial p WHERE (?1 IS NULL or p.nombreObraSocial like %?1%) ")
	Page<ObraSocial> findObraSocial(String nombre, Pageable pageable);

}
