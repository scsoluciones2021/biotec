package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Provincia.
 */
@Entity
@Table(name = "provincia")
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_provincia")
    private String nombreProvincia;

    @OneToMany(mappedBy = "provincia")
    private Set<CodigoPostal> codigopostals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public Provincia nombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
        return this;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public Set<CodigoPostal> getCodigopostals() {
        return codigopostals;
    }

    public Provincia codigopostals(Set<CodigoPostal> codigoPostals) {
        this.codigopostals = codigoPostals;
        return this;
    }

    public Provincia addCodigopostal(CodigoPostal codigoPostal) {
        this.codigopostals.add(codigoPostal);
        codigoPostal.setProvincia(this);
        return this;
    }

    public Provincia removeCodigopostal(CodigoPostal codigoPostal) {
        this.codigopostals.remove(codigoPostal);
        codigoPostal.setProvincia(null);
        return this;
    }

    public void setCodigopostals(Set<CodigoPostal> codigoPostals) {
        this.codigopostals = codigoPostals;
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
        Provincia provincia = (Provincia) o;
        if (provincia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provincia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provincia{" +
            "id=" + getId() +
            ", nombreProvincia='" + getNombreProvincia() + "'" +
            "}";
    }
}
