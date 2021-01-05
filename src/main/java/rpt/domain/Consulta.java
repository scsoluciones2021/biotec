package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Consulta.
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_consulta")
    private LocalDate fechaConsulta;

    @Column(name = "sintomas_consulta")
    private String sintomasConsulta;

    @Column(name = "diagnostico_consulta")
    private String diagnosticoConsulta;

    @Column(name = "observaciones_consulta")
    private String observacionesConsulta;

    @Column(name = "tratamiento_consulta")
    private String tratamientoConsulta;

    @Column(name = "indicaciones_consulta")
    private String indicacionesConsulta;

    @OneToOne
    @JoinColumn(unique = true)
    private Constantes constantesConsulta;

    /*@OneToMany(mappedBy = "consulta")
    private Set<Ficha> fichas = new HashSet<>();*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public Consulta fechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
        return this;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getObservacionesConsulta() {
        return observacionesConsulta;
    }

    public Consulta observacionesConsulta(String observacionesConsulta) {
        this.observacionesConsulta = observacionesConsulta;
        return this;
    }

    public void setObservacionesConsulta(String observacionesConsulta) {
        this.observacionesConsulta = observacionesConsulta;
    }

    public Constantes getConstantesConsulta() {
        return constantesConsulta;
    }

    public Consulta constantesConsulta(Constantes constantes) {
        this.constantesConsulta = constantes;
        return this;
    }

    public void setConstantesConsulta(Constantes constantes) {
        this.constantesConsulta = constantes;
    }

   /* public Set<Ficha> getFichas() {
        return fichas;
    }

    public Consulta fichas(Set<Ficha> fichas) {
        this.fichas = fichas;
        return this;
    }

   public Consulta addFicha(Ficha ficha) {
        this.fichas.add(ficha);
        ficha.setConsulta(this);
        return this;
    }

    public Consulta removeFicha(Ficha ficha) {
        this.fichas.remove(ficha);
        ficha.setConsulta(null);
        return this;
    }

    public void setFichas(Set<Ficha> fichas) {
        this.fichas = fichas;
    }*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consulta consulta = (Consulta) o;
        if (consulta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consulta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + getId() +
            ", fechaConsulta='" + getFechaConsulta() + "'" +
            ", observacionesConsulta='" + getObservacionesConsulta() + "'" +
            "}";
    }

    public String getSintomasConsulta() {
        return sintomasConsulta;
    }

    public void setSintomasConsulta(String sintomasConsulta) {
        this.sintomasConsulta = sintomasConsulta;
    }

    public String getDiagnosticoConsulta() {
        return diagnosticoConsulta;
    }

    public void setDiagnosticoConsulta(String diagnosticoConsulta) {
        this.diagnosticoConsulta = diagnosticoConsulta;
    }

    public String getTratamientoConsulta() {
        return tratamientoConsulta;
    }

    public void setTratamientoConsulta(String tratamientoConsulta) {
        this.tratamientoConsulta = tratamientoConsulta;
    }

    public String getIndicacionesConsulta() {
        return indicacionesConsulta;
    }

    public void setIndicacionesConsulta(String indicacionesConsulta) {
        this.indicacionesConsulta = indicacionesConsulta;
    }

    public Consulta indicacionesConsulta(String indicacionesConsulta) {
        this.indicacionesConsulta = indicacionesConsulta;
        return this;
    }
    public Consulta diagnosticoConsulta(String diagnosticoConsulta) {
        this.diagnosticoConsulta = diagnosticoConsulta;
        return this;
    }

    public Consulta tratamientoConsulta(String tratamientoConsulta) {
        this.tratamientoConsulta = tratamientoConsulta;
        return this;
    }

    public Consulta sintomasConsulta(String sintomasConsulta) {
        this.sintomasConsulta = sintomasConsulta;
        return this;
    }
}
