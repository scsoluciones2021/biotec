package rpt.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Constantes.
 */
@Entity
@Table(name = "constantes")
public class Constantes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "peso")
    private Long peso;

    @Column(name = "altura")
    private Long altura;

    @Column(name = "presion_arterial")
    private Long presionArterial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeso() {
        return peso;
    }

    public Constantes peso(Long peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Long getAltura() {
        return altura;
    }

    public Constantes altura(Long altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Long altura) {
        this.altura = altura;
    }

    public Long getPresionArterial() {
        return presionArterial;
    }

    public Constantes presionArterial(Long presionArterial) {
        this.presionArterial = presionArterial;
        return this;
    }

    public void setPresionArterial(Long presionArterial) {
        this.presionArterial = presionArterial;
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
        Constantes constantes = (Constantes) o;
        if (constantes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), constantes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Constantes{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", altura=" + getAltura() +
            ", presionArterial=" + getPresionArterial() +
            "}";
    }
}
