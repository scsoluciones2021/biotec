package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Diagnostico entity.
 */
public class DiagnosticoDTO implements Serializable {

    private Long id;

    private String codigoDiagnostico;

    private String descripcionDiagnostico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    public void setCodigoDiagnostico(String codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
    }

    public String getDescripcionDiagnostico() {
        return descripcionDiagnostico;
    }

    public void setDescripcionDiagnostico(String descripcionDiagnostico) {
        this.descripcionDiagnostico = descripcionDiagnostico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiagnosticoDTO diagnosticoDTO = (DiagnosticoDTO) o;
        if (diagnosticoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnosticoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiagnosticoDTO{" +
            "id=" + getId() +
            ", codigoDiagnostico='" + getCodigoDiagnostico() + "'" +
            ", descripcionDiagnostico='" + getDescripcionDiagnostico() + "'" +
            "}";
    }
}
