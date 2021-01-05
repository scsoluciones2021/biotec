package rpt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rpt.domain.PagoConsulta} entity.
 */
public class PagoConsultaDTO implements Serializable {

    private Long id;

    private Double monto;

    private String tipo;

    private Integer cupones;


    private Long pagoturnoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCupones() {
        return cupones;
    }

    public void setCupones(Integer cupones) {
        this.cupones = cupones;
    }

    public Long getPagoturnoId() {
        return pagoturnoId;
    }

    public void setPagoturnoId(Long turnoId) {
        this.pagoturnoId = turnoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagoConsultaDTO pagoConsultaDTO = (PagoConsultaDTO) o;
        if (pagoConsultaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagoConsultaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagoConsultaDTO{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", tipo='" + getTipo() + "'" +
            ", cupones=" + getCupones() +
            ", pagoturno=" + getPagoturnoId() +
            "}";
    }
}
