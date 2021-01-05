package rpt.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Diagnostico.
 */
@Entity
@Table(name = "diagnostico")
public class Diagnostico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_diagnostico")
    private String codigoDiagnostico;

    @Column(name = "descripcion_diagnostico")
    private String descripcionDiagnostico;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    public Diagnostico codigoDiagnostico(String codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
        return this;
    }

    public void setCodigoDiagnostico(String codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
    }

    public String getDescripcionDiagnostico() {
        return descripcionDiagnostico;
    }

    public Diagnostico descripcionDiagnostico(String descripcionDiagnostico) {
        this.descripcionDiagnostico = descripcionDiagnostico;
        return this;
    }

    public void setDescripcionDiagnostico(String descripcionDiagnostico) {
        this.descripcionDiagnostico = descripcionDiagnostico;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diagnostico diagnostico = (Diagnostico) o;
        if (diagnostico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnostico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
            "id=" + getId() +
            ", codigoDiagnostico='" + getCodigoDiagnostico() + "'" +
            ", descripcionDiagnostico='" + getDescripcionDiagnostico() + "'" +
            "}";
    }
}
