package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Ficha.
 */
@Entity
@Table(name = "ficha")
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Paciente paciente;

    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Profesional profesional;
/*
    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Consulta consulta;
*/
    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Especialidad especialidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Ficha fechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Ficha paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public Ficha profesional(Profesional profesional) {
        this.profesional = profesional;
        return this;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

/*    public Consulta getConsulta() {
        return consulta;
    }

    public Ficha consulta(Consulta consulta) {
        this.consulta = consulta;
        return this;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ficha ficha = (Ficha) o;
        if (ficha.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ficha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ficha{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            "}";
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Ficha especialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
        return this;
    }

}
