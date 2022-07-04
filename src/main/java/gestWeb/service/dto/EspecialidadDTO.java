package gestWeb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Especialidad entity.
 */
public class EspecialidadDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigoEspecilidad;

    @NotNull
    private String nombreEspecialidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEspecilidad() {
        return codigoEspecilidad;
    }

    public void setCodigoEspecilidad(String codigoEspecilidad) {
        this.codigoEspecilidad = codigoEspecilidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EspecialidadDTO especialidadDTO = (EspecialidadDTO) o;
        if (especialidadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), especialidadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EspecialidadDTO{" +
            "id=" + getId() +
            ", codigoEspecilidad='" + getCodigoEspecilidad() + "'" +
            ", nombreEspecialidad='" + getNombreEspecialidad() + "'" +
            "}";
    }
}
