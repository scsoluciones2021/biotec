package rpt.repository;

import rpt.domain.AntecedentesPersonales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AntecedentesPersonales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntecedentesPersonalesRepository extends JpaRepository<AntecedentesPersonales, Long> {

    @Query(value = "select distinct antecedentes_personales from AntecedentesPersonales antecedentes_personales left join fetch antecedentes_personales.enfermedades left join fetch antecedentes_personales.alergias left join fetch antecedentes_personales.intolerancias left join fetch antecedentes_personales.regimenes",
        countQuery = "select count(distinct antecedentes_personales) from AntecedentesPersonales antecedentes_personales")
    Page<AntecedentesPersonales> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct antecedentes_personales from AntecedentesPersonales antecedentes_personales left join fetch antecedentes_personales.enfermedades left join fetch antecedentes_personales.alergias left join fetch antecedentes_personales.intolerancias left join fetch antecedentes_personales.regimenes")
    List<AntecedentesPersonales> findAllWithEagerRelationships();

    @Query("select antecedentes_personales from AntecedentesPersonales antecedentes_personales left join fetch antecedentes_personales.enfermedades left join fetch antecedentes_personales.alergias left join fetch antecedentes_personales.intolerancias left join fetch antecedentes_personales.regimenes where antecedentes_personales.id =:id")
    Optional<AntecedentesPersonales> findOneWithEagerRelationships(@Param("id") Long id);

}
