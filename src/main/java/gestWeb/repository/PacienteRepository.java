package gestWeb.repository;

import gestWeb.domain.Paciente;
import gestWeb.service.dto.PacienteDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
/**
 * Spring Data  repository for the Paciente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query(value = "SELECT p FROM Paciente p WHERE p.nombrePaciente like %?1% or p.apellidoPaciente like %?1%")
    Page<Paciente> findByNombreApellidoContaining(String query, Pageable pageable);

    @Query(value = "SELECT p FROM Paciente p WHERE p.nombrePaciente like %?1% or p.apellidoPaciente like %?1%")
    List<Paciente> findByNyA(String query);/* {
       User user = getCurrentUser();
       List<Paciente> results = em.createQuery("p FROM Paciente p WHERE p.nombrePaciente like %?1% or p.apellidoPaciente like %?1%", Movie.class).setParameter("title", "%"+title+"%").setParameter("userid", user.getId()).getResultList();
       return results;
    }*/

    @Query(value = "SELECT p FROM Paciente p WHERE (?1 IS NULL or p.apellidoPaciente like %?1%) and (?2 IS NULL or p.nombrePaciente like %?2%) and ( ?3 IS NULL or p.documentoPaciente like %?3%) ")
	Page<Paciente> findPacientes(String apellido, String nombre, String dni, Pageable pageable);

    @Query(value = "SELECT p FROM Paciente p WHERE p.documentoPaciente = ?1 ")
	Optional<Paciente> findByDni(String dni);
}
