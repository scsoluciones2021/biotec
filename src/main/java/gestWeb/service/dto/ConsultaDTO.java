package gestWeb.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Consulta entity.
 */
public class ConsultaDTO implements Serializable {

    private Long id;

    private LocalDate fechaConsulta;

    private String sintomasConsulta;
    private String diagnosticoConsulta;
    private String observacionesConsulta;
    private String tratamientoConsulta;
    private String indicacionesConsulta;

    private Long constantesConsultaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getObservacionesConsulta() {
        return observacionesConsulta;
    }

    public void setObservacionesConsulta(String observacionesConsulta) {
        this.observacionesConsulta = observacionesConsulta;
    }

    public String getSintomasConsulta() {
        return sintomasConsulta;
    }

    public void setSintomasConsulta(String sintomasConsulta) {
        this.sintomasConsulta = sintomasConsulta;
    }

    public String getDiagnosticoConsulta() {
        return diagnosticoConsulta;
    }

    public void setDiagnosticoConsulta(String diagnosticoConsulta) {
        this.diagnosticoConsulta = diagnosticoConsulta;
    }

    public String getTratamientoConsulta() {
        return tratamientoConsulta;
    }

    public void setTratamientoConsulta(String tratamientoConsulta) {
        this.tratamientoConsulta = tratamientoConsulta;
    }

    public String getIndicacionesConsulta() {
        return indicacionesConsulta;
    }

    public void setIndicacionesConsulta(String indicacionesConsulta) {
        this.indicacionesConsulta = indicacionesConsulta;
    }

    public Long getConstantesConsultaId() {
        return constantesConsultaId;
    }

    public void setConstantesConsultaId(Long constantesId) {
        this.constantesConsultaId = constantesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultaDTO consultaDTO = (ConsultaDTO) o;
        if (consultaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultaDTO{" +
            "id=" + getId() +
            ", fechaConsulta='" + getFechaConsulta() + "'" +
            ", observacionesConsulta='" + getObservacionesConsulta() + "'" +
            ", constantesConsulta=" + getConstantesConsultaId() +
            "}";
    }
}
