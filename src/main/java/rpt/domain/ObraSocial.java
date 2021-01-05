package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ObraSocial.
 */
@Entity
@Table(name = "obra_social")
public class ObraSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_obra_social")
    private String codigoObraSocial;

    @Column(name = "nombre_obra_social")
    private String nombreObraSocial;

    @Column(name = "direccion_obra_social")
    private String direccionObraSocial;

    @Column(name = "telefono_obra_social")
    private String telefonoObraSocial;

    @Column(name = "email_obra_social")
    private String emailObraSocial;

    @Column(name = "sigla_obra_social")
    private String siglaObraSocial;

    @ManyToMany(mappedBy = "obrasocials")
    @JsonIgnore
    private Set<Profesional> profesionals = new HashSet<>();

    @ManyToMany(mappedBy = "obrasocials")
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("obrasocials")
    private AgrupadorOS agrupador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoObraSocial() {
        return codigoObraSocial;
    }

    public ObraSocial codigoObraSocial(String codigoObraSocial) {
        this.codigoObraSocial = codigoObraSocial;
        return this;
    }

    public void setCodigoObraSocial(String codigoObraSocial) {
        this.codigoObraSocial = codigoObraSocial;
    }

    public String getNombreObraSocial() {
        return nombreObraSocial;
    }

    public ObraSocial nombreObraSocial(String nombreObraSocial) {
        this.nombreObraSocial = nombreObraSocial;
        return this;
    }

    public void setNombreObraSocial(String nombreObraSocial) {
        this.nombreObraSocial = nombreObraSocial;
    }

    public String getDireccionObraSocial() {
        return direccionObraSocial;
    }

    public ObraSocial direccionObraSocial(String direccionObraSocial) {
        this.direccionObraSocial = direccionObraSocial;
        return this;
    }

    public void setDireccionObraSocial(String direccionObraSocial) {
        this.direccionObraSocial = direccionObraSocial;
    }

    public String getTelefonoObraSocial() {
        return telefonoObraSocial;
    }

    public ObraSocial telefonoObraSocial(String telefonoObraSocial) {
        this.telefonoObraSocial = telefonoObraSocial;
        return this;
    }

    public void setTelefonoObraSocial(String telefonoObraSocial) {
        this.telefonoObraSocial = telefonoObraSocial;
    }

    public String getEmailObraSocial() {
        return emailObraSocial;
    }

    public ObraSocial emailObraSocial(String emailObraSocial) {
        this.emailObraSocial = emailObraSocial;
        return this;
    }

    public void setEmailObraSocial(String emailObraSocial) {
        this.emailObraSocial = emailObraSocial;
    }

    public String getSiglaObraSocial() {
        return siglaObraSocial;
    }

    public ObraSocial siglaObraSocial(String siglaObraSocial) {
        this.siglaObraSocial = siglaObraSocial;
        return this;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public ObraSocial profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public ObraSocial addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.getObrasocials().add(this);
        return this;
    }

    public ObraSocial removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.getObrasocials().remove(this);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public ObraSocial pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public ObraSocial addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.getObrasocials().add(this);
        return this;
    }

    public ObraSocial removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.getObrasocials().remove(this);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
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
        ObraSocial obraSocial = (ObraSocial) o;
        if (obraSocial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obraSocial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObraSocial{" +
            "id=" + getId() +
            ", codigoObraSocial='" + getCodigoObraSocial() + "'" +
            ", nombreObraSocial='" + getNombreObraSocial() + "'" +
            ", direccionObraSocial='" + getDireccionObraSocial() + "'" +
            ", telefonoObraSocial='" + getTelefonoObraSocial() + "'" +
            ", emailObraSocial='" + getEmailObraSocial() + "'" +
            "}";
    }

	
	/**
	 * @param siglaObraSocial the siglaObraSocial to set
	 */
	public void setSiglaObraSocial(String siglaObraSocial) {
		this.siglaObraSocial = siglaObraSocial;
	}

    /**
     * @return the agrupador
     */
    public AgrupadorOS getAgrupador() {
        return agrupador;
    }

    /**
     * @param agrupador the agrupador to set
     */
    public void setAgrupador(AgrupadorOS agrupador) {
        this.agrupador = agrupador;
    }


}
