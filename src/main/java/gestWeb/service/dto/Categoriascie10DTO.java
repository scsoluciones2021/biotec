package gestWeb.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Categoriascie10 entity.
 */
public class Categoriascie10DTO implements Serializable {

    private Long id;

    @NotNull
    private String clave;
   
    @NotNull
    private String descripcion;

    private Long idsubgrupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdSubGrupo() {
        return idsubgrupo;
    }

    public void setIdSubGrupo(Long idsubgrupo) {
        this.idsubgrupo = idsubgrupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Categoriascie10DTO categoriascie10DTO = (Categoriascie10DTO) o;
        if (categoriascie10DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriascie10DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categoriascie10DTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idsubgrupo='" + getIdSubGrupo() + "'" +
            "}";
    }
}
