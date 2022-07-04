package gestWeb.repository;

import gestWeb.domain.Categoriascie10;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Categoriascie10 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Categoriascie10Repository extends JpaRepository<Categoriascie10, Long> {
}
