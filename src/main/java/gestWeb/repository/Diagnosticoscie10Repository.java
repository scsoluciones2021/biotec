package gestWeb.repository;

import gestWeb.domain.Diagnosticoscie10;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Diagnosticoscie10 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Diagnosticoscie10Repository extends JpaRepository<Diagnosticoscie10, Long> {
}
