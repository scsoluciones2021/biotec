package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HorariosProfesional.
 */
@Entity
@Table(name = "horarios_profesional")
public class HorariosProfesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultorio")
    private String consultorio;

   /* @NotNull
    @Column(name = "dias", nullable = false)
    private String dias; */

    @NotNull
    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

   /* @NotNull
    @Column(name = "hora_desde", nullable = false)
    private String horaDesde;

    @NotNull
    @Column(name = "hora_hasta", nullable = false)
    private String horaHasta;

    @NotNull
    @Column(name = "intervalo", nullable = false)
    private String intervalo; */

    @ManyToOne
    @JsonIgnoreProperties("")
    private Profesional horario_prof_rel;

   /* @NotNull
    @Column(name = "cant_pacientes_x_tur", nullable = false)
    private Integer cantPacXTur;*/

    @ManyToMany
    @JoinTable(name = "horarios_profesional_horario_bloq_rel",
               joinColumns = @JoinColumn(name = "horarios_profesionals_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "horario_bloq_rels_id", referencedColumnName = "id"))
    private Set<Bloqueos> horario_bloq_rels = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Especialidad horario_esp_rel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public HorariosProfesional consultorio(String consultorio) {
        this.consultorio = consultorio;
        return this;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

   /* public String getDias() {
        return dias;
    }

    public HorariosProfesional dias(String dias) {
        this.dias = dias;
        return this;
    }

    public void setDias(String dias) {
        this.dias = dias;
    } */

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public HorariosProfesional fechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
        return this;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public HorariosProfesional fechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
        return this;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

   /* public String getHoraDesde() {
        return horaDesde;
    }

    public HorariosProfesional horaDesde(String horaDesde) {
        this.horaDesde = horaDesde;
        return this;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public HorariosProfesional horaHasta(String horaHasta) {
        this.horaHasta = horaHasta;
        return this;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public HorariosProfesional intervalo(String intervalo) {
        this.intervalo = intervalo;
        return this;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    } */

    public Profesional getHorario_prof_rel() {
        return horario_prof_rel;
    }

    public HorariosProfesional horario_prof_rel(Profesional profesional) {
        this.horario_prof_rel = profesional;
        return this;
    }

    public void setHorario_prof_rel(Profesional profesional) {
        this.horario_prof_rel = profesional;
    }

    public Set<Bloqueos> getHorario_bloq_rels() {
        return horario_bloq_rels;
    }

    public HorariosProfesional horario_bloq_rels(Set<Bloqueos> bloqueos) {
        this.horario_bloq_rels = bloqueos;
        return this;
    }

   /* public HorariosProfesional addHorario_bloq_rel(Bloqueos bloqueo) {
        this.horario_bloq_rels.add(bloqueo);
        bloqueo.getHorariosProfesionals().add(this);
        return this;
    }

    public HorariosProfesional removeHorario_bloq_rel(Bloqueos bloqueo) {
        this.horario_bloq_rels.remove(bloqueo);
        bloqueo.getHorariosProfesionals().remove(this);
        return this;
    }*/

    public void setHorario_bloq_rels(Set<Bloqueos> bloqueos) {
        this.horario_bloq_rels = bloqueos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HorariosProfesional horariosProfesional = (HorariosProfesional) o;
        if (horariosProfesional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horariosProfesional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorariosProfesional{" +
            "id=" + getId() +
            ", consultorio='" + getConsultorio() + "'" +
            // ", dias='" + getDias() + "'" +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            // ", horaDesde='" + getHoraDesde() + "'" +
            // ", horaHasta='" + getHoraHasta() + "'" + 
            // ", intervalo=" + getIntervalo() +
            "}";
    }

    public Especialidad getHorario_esp_rel() {
        return horario_esp_rel;
    }

    public HorariosProfesional horario_esp_rel(Especialidad especialidad) {
        this.horario_esp_rel = especialidad;
        return this;
    }

    public void setHorario_esp_rel(Especialidad especialidad) {
        this.horario_esp_rel = especialidad;
    }

    /*
    public Integer getCantPacXTur() {
        return cantPacXTur;
    }

    
    public void setCantPacXTur(Integer cantPacXTur) {
        this.cantPacXTur = cantPacXTur;
    } */
}
