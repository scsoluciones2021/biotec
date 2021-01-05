package rpt.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ObsAntecPersonal entity.
 */
public class ObsAntecPersonalDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idEspecialidad;

    @NotNull
    private Integer idMedico;

    @NotNull
    private String observaciones;

    private LocalDate fecha;

    private Long antecedentesPersonalesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getAntecedentesPersonalesId() {
        return antecedentesPersonalesId;
    }

    public void setAntecedentesPersonalesId(Long antecedentesPersonalesId) {
        this.antecedentesPersonalesId = antecedentesPersonalesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObsAntecPersonalDTO obsAntecPersonalDTO = (ObsAntecPersonalDTO) o;
        if (obsAntecPersonalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obsAntecPersonalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObsAntecPersonalDTO{" +
            "id=" + getId() +
            ", idEspecialidad=" + getIdEspecialidad() +
            ", idMedico=" + getIdMedico() +
            ", observaciones='" + getObservaciones() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", antecedentesPersonales=" + getAntecedentesPersonalesId() +
            "}";
    }
}
