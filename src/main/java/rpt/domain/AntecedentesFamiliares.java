package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AntecedentesFamiliares.
 */
@Entity
@Table(name = "antecedentes_familiares")
public class AntecedentesFamiliares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vivo_af")
    private Boolean vivoAF;

    @Column(name = "causa_muerte_af")
    private String causaMuerteAF;

    @OneToMany(mappedBy = "antecedentesFamiliares")
    private Set<ObsAntecFamiliar> obsantecFamiliars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("antecedentesFamiliares")
    private Familiar parentezco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isVivoAF() {
        return vivoAF;
    }

    public AntecedentesFamiliares vivoAF(Boolean vivoAF) {
        this.vivoAF = vivoAF;
        return this;
    }

    public void setVivoAF(Boolean vivoAF) {
        this.vivoAF = vivoAF;
    }

    public String getCausaMuerteAF() {
        return causaMuerteAF;
    }

    public AntecedentesFamiliares causaMuerteAF(String causaMuerteAF) {
        this.causaMuerteAF = causaMuerteAF;
        return this;
    }

    public void setCausaMuerteAF(String causaMuerteAF) {
        this.causaMuerteAF = causaMuerteAF;
    }

    public Set<ObsAntecFamiliar> getObsantecFamiliars() {
        return obsantecFamiliars;
    }

    public AntecedentesFamiliares obsantecFamiliars(Set<ObsAntecFamiliar> obsAntecFamiliars) {
        this.obsantecFamiliars = obsAntecFamiliars;
        return this;
    }

    public AntecedentesFamiliares addObsantecFamiliar(ObsAntecFamiliar obsAntecFamiliar) {
        this.obsantecFamiliars.add(obsAntecFamiliar);
        obsAntecFamiliar.setAntecedentesFamiliares(this);
        return this;
    }

    public AntecedentesFamiliares removeObsantecFamiliar(ObsAntecFamiliar obsAntecFamiliar) {
        this.obsantecFamiliars.remove(obsAntecFamiliar);
        obsAntecFamiliar.setAntecedentesFamiliares(null);
        return this;
    }

    public void setObsantecFamiliars(Set<ObsAntecFamiliar> obsAntecFamiliars) {
        this.obsantecFamiliars = obsAntecFamiliars;
    }

    public Familiar getParentezco() {
        return parentezco;
    }

    public AntecedentesFamiliares parentezco(Familiar familiar) {
        this.parentezco = familiar;
        return this;
    }

    public void setParentezco(Familiar familiar) {
        this.parentezco = familiar;
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
        AntecedentesFamiliares antecedentesFamiliares = (AntecedentesFamiliares) o;
        if (antecedentesFamiliares.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), antecedentesFamiliares.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AntecedentesFamiliares{" +
            "id=" + getId() +
            ", vivoAF='" + isVivoAF() + "'" +
            ", causaMuerteAF='" + getCausaMuerteAF() + "'" +
            "}";
    }
}
