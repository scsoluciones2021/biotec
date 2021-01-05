package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AntecedentesPersonales.
 */
@Entity
@Table(name = "antecedentes_personales")
public class AntecedentesPersonales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tabaco")
    private Boolean tabaco;

    @Column(name = "tabaco_observ")
    private String tabacoObserv;

    @Column(name = "tecafe")
    private Boolean tecafe;

    @OneToMany(mappedBy = "antecedentesPersonales")
    private Set<ObsAntecPersonal> obsantecPersonals = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "antecedentes_personales_enfermedades",
               joinColumns = @JoinColumn(name = "antecedentes_personales_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "enfermedades_id", referencedColumnName = "id"))
    private Set<Enfermedad> enfermedades = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "antecedentes_personales_alergias",
               joinColumns = @JoinColumn(name = "antecedentes_personales_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "alergias_id", referencedColumnName = "id"))
    private Set<Alergia> alergias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "antecedentes_personales_intolerancias",
               joinColumns = @JoinColumn(name = "antecedentes_personales_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intolerancias_id", referencedColumnName = "id"))
    private Set<Intolerancia> intolerancias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "antecedentes_personales_regimenes",
               joinColumns = @JoinColumn(name = "antecedentes_personales_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "regimenes_id", referencedColumnName = "id"))
    private Set<Regimen> regimenes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("antecedentesPersonales")
    private Ejercicio ejercicios;

    @ManyToOne
    @JsonIgnoreProperties("antecedentesPersonales")
    private Bebida bebidas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTabaco() {
        return tabaco;
    }

    public AntecedentesPersonales tabaco(Boolean tabaco) {
        this.tabaco = tabaco;
        return this;
    }

    public void setTabaco(Boolean tabaco) {
        this.tabaco = tabaco;
    }

    public String getTabacoObserv() {
        return tabacoObserv;
    }

    public AntecedentesPersonales tabacoObserv(String tabacoObserv) {
        this.tabacoObserv = tabacoObserv;
        return this;
    }

    public void setTabacoObserv(String tabacoObserv) {
        this.tabacoObserv = tabacoObserv;
    }

    public Boolean isTecafe() {
        return tecafe;
    }

    public AntecedentesPersonales tecafe(Boolean tecafe) {
        this.tecafe = tecafe;
        return this;
    }

    public void setTecafe(Boolean tecafe) {
        this.tecafe = tecafe;
    }

    public Set<ObsAntecPersonal> getObsantecPersonals() {
        return obsantecPersonals;
    }

    public AntecedentesPersonales obsantecPersonals(Set<ObsAntecPersonal> obsAntecPersonals) {
        this.obsantecPersonals = obsAntecPersonals;
        return this;
    }

    public AntecedentesPersonales addObsantecPersonal(ObsAntecPersonal obsAntecPersonal) {
        this.obsantecPersonals.add(obsAntecPersonal);
        obsAntecPersonal.setAntecedentesPersonales(this);
        return this;
    }

    public AntecedentesPersonales removeObsantecPersonal(ObsAntecPersonal obsAntecPersonal) {
        this.obsantecPersonals.remove(obsAntecPersonal);
        obsAntecPersonal.setAntecedentesPersonales(null);
        return this;
    }

    public void setObsantecPersonals(Set<ObsAntecPersonal> obsAntecPersonals) {
        this.obsantecPersonals = obsAntecPersonals;
    }

    public Set<Enfermedad> getEnfermedades() {
        return enfermedades;
    }

    public AntecedentesPersonales enfermedades(Set<Enfermedad> enfermedads) {
        this.enfermedades = enfermedads;
        return this;
    }

    public AntecedentesPersonales addEnfermedades(Enfermedad enfermedad) {
        this.enfermedades.add(enfermedad);
        return this;
    }

    public AntecedentesPersonales removeEnfermedades(Enfermedad enfermedad) {
        this.enfermedades.remove(enfermedad);
        return this;
    }

    public void setEnfermedades(Set<Enfermedad> enfermedads) {
        this.enfermedades = enfermedads;
    }

    public Set<Alergia> getAlergias() {
        return alergias;
    }

    public AntecedentesPersonales alergias(Set<Alergia> alergias) {
        this.alergias = alergias;
        return this;
    }

    public AntecedentesPersonales addAlergias(Alergia alergia) {
        this.alergias.add(alergia);
        return this;
    }

    public AntecedentesPersonales removeAlergias(Alergia alergia) {
        this.alergias.remove(alergia);
        return this;
    }

    public void setAlergias(Set<Alergia> alergias) {
        this.alergias = alergias;
    }

    public Set<Intolerancia> getIntolerancias() {
        return intolerancias;
    }

    public AntecedentesPersonales intolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
        return this;
    }

    public AntecedentesPersonales addIntolerancias(Intolerancia intolerancia) {
        this.intolerancias.add(intolerancia);
        return this;
    }

    public AntecedentesPersonales removeIntolerancias(Intolerancia intolerancia) {
        this.intolerancias.remove(intolerancia);
        return this;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public Set<Regimen> getRegimenes() {
        return regimenes;
    }

    public AntecedentesPersonales regimenes(Set<Regimen> regimen) {
        this.regimenes = regimen;
        return this;
    }

    public AntecedentesPersonales addRegimenes(Regimen regimen) {
        this.regimenes.add(regimen);
        return this;
    }

    public AntecedentesPersonales removeRegimenes(Regimen regimen) {
        this.regimenes.remove(regimen);
        return this;
    }

    public void setRegimenes(Set<Regimen> regimen) {
        this.regimenes = regimen;
    }

    public Ejercicio getEjercicios() {
        return ejercicios;
    }

    public AntecedentesPersonales ejercicios(Ejercicio ejercicio) {
        this.ejercicios = ejercicio;
        return this;
    }

    public void setEjercicios(Ejercicio ejercicio) {
        this.ejercicios = ejercicio;
    }

    public Bebida getBebidas() {
        return bebidas;
    }

    public AntecedentesPersonales bebidas(Bebida bebida) {
        this.bebidas = bebida;
        return this;
    }

    public void setBebidas(Bebida bebida) {
        this.bebidas = bebida;
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
        AntecedentesPersonales antecedentesPersonales = (AntecedentesPersonales) o;
        if (antecedentesPersonales.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), antecedentesPersonales.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AntecedentesPersonales{" +
            "id=" + getId() +
            ", tabaco='" + isTabaco() + "'" +
            ", tabacoObserv='" + getTabacoObserv() + "'" +
            ", tecafe='" + isTecafe() + "'" +
            "}";
    }
}
