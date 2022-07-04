package gestWeb.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Subgruposcie10 entity.
 */
public class Subgruposcie10DTO implements Serializable {

    private Long id;

    @NotNull
    private String clave;
   
    @NotNull
    private String descripcion;

    private Long idgrupo;

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

    public Long getIdGrupo() {
        return idgrupo;
    }

    public void setIdGrupo(Long idgrupo) {
        this.idgrupo = idgrupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subgruposcie10DTO subgruposcie10DTO = (Subgruposcie10DTO) o;
        if (subgruposcie10DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subgruposcie10DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subgruposcie10DTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idgrupo='" + getIdGrupo() + "'" +
            "}";
    }
}
