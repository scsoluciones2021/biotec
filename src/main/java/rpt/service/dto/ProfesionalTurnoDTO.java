package rpt.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Profesional entity.
 */
public class ProfesionalTurnoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreProfesional;

    private String idsEspecialidades;

    private String nombreEspecialidades;

    public Long getId() {
        return id;
    }

    /**
     * @return the idsEspecialidades
     */
    public String getIdsEspecialidades() {
        return idsEspecialidades;
    }

    /**
     * @param idsEspecialidades the idsEspecialidades to set
     */
    public void setIdsEspecialidades(String idsEspecialidades) {
        this.idsEspecialidades = idsEspecialidades;
    }

    /**
     * @return the nombreEspecialidades
     */
    public String getNombreEspecialidades() {
        return nombreEspecialidades;
    }

    /**
     * @param nombreEspecialidades the nombreEspecialidades to set
     */
    public void setNombreEspecialidades(String nombreEspecialidades) {
        this.nombreEspecialidades = nombreEspecialidades;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfesionalTurnoDTO ProfesionalTurnoDTO = (ProfesionalTurnoDTO) o;
        if (ProfesionalTurnoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ProfesionalTurnoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfesionalTurnoDTO{" +
            "id=" + getId() +
            ", nombreProfesional='" + getNombreProfesional() + "'" +
            ", idsEspecialidades='" + getIdsEspecialidades() + "'" +
            ", nombreEspecialidades='" + getNombreEspecialidades() + "'" +

            "}";
    }
}
