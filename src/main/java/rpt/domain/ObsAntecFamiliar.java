package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ObsAntecFamiliar.
 */
@Entity
@Table(name = "obs_antec_familiar")
public class ObsAntecFamiliar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_especialidad", nullable = false)
    private Integer idEspecialidad;

    @NotNull
    @Column(name = "id_medico", nullable = false)
    private Integer idMedico;

    @NotNull
    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("obsantecFamiliars")
    private AntecedentesFamiliares antecedentesFamiliares;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public ObsAntecFamiliar idEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
        return this;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public ObsAntecFamiliar idMedico(Integer idMedico) {
        this.idMedico = idMedico;
        return this;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ObsAntecFamiliar observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ObsAntecFamiliar fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public AntecedentesFamiliares getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }

    public ObsAntecFamiliar antecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
        return this;
    }

    public void setAntecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
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
        ObsAntecFamiliar obsAntecFamiliar = (ObsAntecFamiliar) o;
        if (obsAntecFamiliar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obsAntecFamiliar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObsAntecFamiliar{" +
            "id=" + getId() +
            ", idEspecialidad=" + getIdEspecialidad() +
            ", idMedico=" + getIdMedico() +
            ", observaciones='" + getObservaciones() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
