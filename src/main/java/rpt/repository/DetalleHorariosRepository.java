package rpt.repository;

import rpt.domain.DetalleHorarios;

import java.awt.print.Pageable;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the DetalleHorarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleHorariosRepository extends JpaRepository<DetalleHorarios, Long> {

    @Query(value = "SELECT d FROM DetalleHorarios d where d.idHorario = ?1 order by d.idHorario")
    PageImpl<DetalleHorarios> buscarXHorario(Long idHorario, org.springframework.data.domain.Pageable pageable);
}
