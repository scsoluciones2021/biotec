package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Estados entity.
 */
public class EstadosDTO implements Serializable {

    private Long id;

    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstadosDTO estadosDTO = (EstadosDTO) o;
        if (estadosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estadosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstadosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
