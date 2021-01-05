package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Provincia entity.
 */
public class ProvinciaDTO implements Serializable {

    private Long id;

    private String nombreProvincia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProvinciaDTO provinciaDTO = (ProvinciaDTO) o;
        if (provinciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provinciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvinciaDTO{" +
            "id=" + getId() +
            ", nombreProvincia='" + getNombreProvincia() + "'" +
            "}";
    }
}
