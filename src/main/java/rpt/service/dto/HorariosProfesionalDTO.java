package rpt.service.dto;
import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the HorariosProfesional entity.
 */
public class HorariosProfesionalDTO implements Serializable {

    private Long id;

    private String consultorio;

 /*  @NotNull
    private String dias; */

    @NotNull
    private LocalDate fechaDesde;

    private LocalDate fechaHasta;

  /*  @NotNull
    private String horaDesde;

    @NotNull
    private String horaHasta;

    @NotNull
    private String intervalo; */

    private Long horario_prof_relId;

    private String horario_prof_relNombreProfesional;

    private Set<BloqueosDTO> horario_bloq_rels = new HashSet<>();

    private Long horario_esp_relId;

    private String horario_esp_relNombreEspecialidad;

    public Long getId() {
        return id;
    }

    public Long getHorario_esp_relId() {
        return horario_esp_relId;
    }

    public void setHorario_esp_relId(Long horario_esp_relId) {
        this.horario_esp_relId = horario_esp_relId;
    }

    public String getHorario_esp_relNombreEspecialidad() {
        return horario_esp_relNombreEspecialidad;
    }

    public void setHorario_esp_relNombreEspecialidad(String horario_esp_relNombreEspecialidad) {
        this.horario_esp_relNombreEspecialidad = horario_esp_relNombreEspecialidad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

  /*  public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }
*/
    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

 /*   public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }
*/
    public Long getHorario_prof_relId() {
        return horario_prof_relId;
    }

    public void setHorario_prof_relId(Long profesionalId) {
        this.horario_prof_relId = profesionalId;
    }

    public String getHorario_prof_relNombreProfesional() {
        return horario_prof_relNombreProfesional;
    }

    public void setHorario_prof_relNombreProfesional(String profesionalNombreProfesional) {
        this.horario_prof_relNombreProfesional = profesionalNombreProfesional;
    }

    public Set<BloqueosDTO> getHorario_bloq_rels() {
        return horario_bloq_rels;
    }

    public void setHorario_bloq_rels(Set<BloqueosDTO> bloqueos) {
        this.horario_bloq_rels = bloqueos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorariosProfesionalDTO horariosProfesionalDTO = (HorariosProfesionalDTO) o;
        if (horariosProfesionalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horariosProfesionalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorariosProfesionalDTO{" +
            "id=" + getId() +
            ", consultorio='" + getConsultorio() + "'" +
            // ", dias='" + getDias() + "'" +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
           /* ", horaDesde='" + getHoraDesde() + "'" +
            ", horaHasta='" + getHoraHasta() + "'" +
            ", intervalo=" + getIntervalo() +*/
            ", horario_prof_rel=" + getHorario_prof_relId() +
            ", horario_prof_rel='" + getHorario_prof_relNombreProfesional() + "'" +
            "}";
    }
}
