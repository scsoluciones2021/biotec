package rpt.repository;

import rpt.domain.Adjuntos_ficha;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Adjuntos_ficha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Adjuntos_fichaRepository extends JpaRepository<Adjuntos_ficha, Long> {
//Cambiar "usuario" por nro  
//, nativeQuery = true es cuando escribo la consulta directamente como sql 
    @Query(value = "SELECT coalesce(max(usuario), 0)+1 FROM adjuntos_ficha p WHERE p.id_ficha = ?1", nativeQuery = true)
    Integer ultimoNumero(Long ficha);

    @Query(value = "SELECT SUBSTRING_INDEX(nombre_actual,?2,-1) FROM adjuntos_ficha p WHERE p.id_ficha = ?1", nativeQuery = true)
    List<String> obtenerArchivosFichaConsulta(Long idFicha, String separador);
}
