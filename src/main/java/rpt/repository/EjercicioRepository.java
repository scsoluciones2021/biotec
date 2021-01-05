package rpt.repository;

import rpt.domain.Ejercicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ejercicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

}
