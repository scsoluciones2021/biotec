package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ejercicio.
 */
@Entity
@Table(name = "ejercicio")
public class Ejercicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valor", nullable = false)
    private String valor;

    @OneToMany(mappedBy = "ejercicios")
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

    public Ejercicio valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Set<AntecedentesPersonales> getAntecedentesPersonales() {
        return antecedentesPersonales;
    }

    public Ejercicio antecedentesPersonales(Set<AntecedentesPersonales> antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
        return this;
    }

    public Ejercicio addAntecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales.add(antecedentesPersonales);
        antecedentesPersonales.setEjercicios(this);
        return this;
    }

    public Ejercicio removeAntecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales.remove(antecedentesPersonales);
        antecedentesPersonales.setEjercicios(null);
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
        Ejercicio ejercicio = (Ejercicio) o;
        if (ejercicio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ejercicio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
