package gestWeb.repository;

import gestWeb.domain.Bloqueos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Bloqueos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloqueosRepository extends JpaRepository<Bloqueos, Long> {

    @Query(value = "select * from {h-schema}bloqueos b where fecha_desde >= ?1 order by fecha_desde",
     nativeQuery = true)
     List<Bloqueos> obtenerBloqueosDesde(String fechaDesde);
}
