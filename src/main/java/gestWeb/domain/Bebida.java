package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bebida.
 */
@Entity
@Table(name = "bebida")
public class Bebida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valor", nullable = false)
    private String valor;

    @OneToMany(mappedBy = "bebidas")
    private Set<AntecedentesPersonales> antecedentesPersonales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public Bebida valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Set<AntecedentesPersonales> getAntecedentesPersonales() {
        return antecedentesPersonales;
    }

    public Bebida antecedentesPersonales(Set<AntecedentesPersonales> antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
        return this;
    }

    public Bebida addAntecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales.add(antecedentesPersonales);
        antecedentesPersonales.setBebidas(this);
        return this;
    }

    public Bebida removeAntecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales.remove(antecedentesPersonales);
        antecedentesPersonales.setBebidas(null);
        return this;
    }

    public void setAntecedentesPersonales(Set<AntecedentesPersonales> antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
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
        Bebida bebida = (Bebida) o;
        if (bebida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bebida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bebida{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
