package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Familiar.
 */
@Entity
@Table(name = "familiar")
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parentezco")
    private String parentezco;

    @OneToMany(mappedBy = "parentezco")
    private Set<AntecedentesFamiliares> antecedentesFamiliares = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentezco() {
        return parentezco;
    }

    public Familiar parentezco(String parentezco) {
        this.parentezco = parentezco;
        return this;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public Set<AntecedentesFamiliares> getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }

    public Familiar antecedentesFamiliares(Set<AntecedentesFamiliares> antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
        return this;
    }

    public Familiar addAntecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares.add(antecedentesFamiliares);
        antecedentesFamiliares.setParentezco(this);
        return this;
    }

    public Familiar removeAntecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares.remove(antecedentesFamiliares);
        antecedentesFamiliares.setParentezco(null);
        return this;
    }

    public void setAntecedentesFamiliares(Set<AntecedentesFamiliares> antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
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
        Familiar familiar = (Familiar) o;
        if (familiar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familiar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Familiar{" +
            "id=" + getId() +
            ", parentezco='" + getParentezco() + "'" +
            "}";
    }
}
