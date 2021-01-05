package rpt.repository;

import rpt.domain.CodigoPostal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Spring Data  repository for the CodigoPostal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Long> {
    @Query(value = "SELECT p FROM CodigoPostal p WHERE p.nombreCiudad like %?1% or p.codigo like %?1%")
    Page<CodigoPostal> findByNombreCiudadContaining(String query, Pageable pageable);

    @Query(value = "SELECT p FROM CodigoPostal p WHERE p.nombreCiudad like %?1% or p.codigo like %?1%")
    List<CodigoPostal> findByNoCp(String query);
    
    @Query(value = "SELECT * FROM codigo_postal p WHERE p.provincia_id = ?1", nativeQuery = true)
    List<CodigoPostal> findCPProvincia(String idProvincia);
}
