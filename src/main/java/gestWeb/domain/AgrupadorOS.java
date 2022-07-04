package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AgrupadorOS.
 */
@Entity
@Table(name = "agrupadoros")
public class AgrupadorOS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "agrupador")
    @JsonIgnore
    private Set<ObraSocial> agrupador_obrasocials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public AgrupadorOS nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public AgrupadorOS descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ObraSocial> getAgrupador_obrasocials() {
        return agrupador_obrasocials;
    }

    public AgrupadorOS agrupador_obrasocials(Set<ObraSocial> obraSocials) {
        this.agrupador_obrasocials = obraSocials;
        return this;
    }

    public AgrupadorOS addAgrupador_obrasocial(ObraSocial obraSocial) {
        this.agrupador_obrasocials.add(obraSocial);
        obraSocial.setAgrupador(this);
        return this;
    }

    public AgrupadorOS removeAgrupador_obrasocial(ObraSocial obraSocial) {
        this.agrupador_obrasocials.remove(obraSocial);
        obraSocial.setAgrupador(null);
        return this;
    }

    public void setAgrupador_obrasocials(Set<ObraSocial> obraSocials) {
        this.agrupador_obrasocials = obraSocials;
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
        AgrupadorOS agrupadorOS = (AgrupadorOS) o;
        if (agrupadorOS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agrupadorOS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgrupadorOS{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
