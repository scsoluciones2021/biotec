package gestWeb.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PagoConsulta.
 */
@Entity
@Table(name = "pago_consulta")
public class PagoConsulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cupones")
    private Integer cupones;

    @ManyToOne
    private Turno pagoturno;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public PagoConsulta monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public PagoConsulta tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCupones() {
        return cupones;
    }

    public PagoConsulta cupones(Integer cupones) {
        this.cupones = cupones;
        return this;
    }

    public void setCupones(Integer cupones) {
        this.cupones = cupones;
    }

    public Turno getPagoturno() {
        return pagoturno;
    }

    public PagoConsulta pagoturno(Turno turno) {
        this.pagoturno = turno;
        return this;
    }

    public void setPagoturno(Turno turno) {
        this.pagoturno = turno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagoConsulta)) {
            return false;
        }
        return id != null && id.equals(((PagoConsulta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PagoConsulta{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", tipo='" + getTipo() + "'" +
            ", cupones=" + getCupones() +
            "}";
    }
}
