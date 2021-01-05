package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CodigoPostal.
 */
@Entity
@Table(name = "codigo_postal")
public class CodigoPostal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre_ciudad")
    private String nombreCiudad;

    @OneToMany(mappedBy = "codigoPostal")
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(mappedBy = "codigoPostal")
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "codigoPostal")
    private Set<Profesional> profesionals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("codigopostals")
    private Provincia provincia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public CodigoPostal codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public CodigoPostal nombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
        return this;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public CodigoPostal empresas(Set<Empresa> empresas) {
        this.empresas = empresas;
        return this;
    }

    public CodigoPostal addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setCodigoPostal(this);
        return this;
    }

    public CodigoPostal removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setCodigoPostal(null);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public CodigoPostal pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public CodigoPostal addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setCodigoPostal(this);
        return this;
    }

    public CodigoPostal removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setCodigoPostal(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Profesional> getProfesionals() {
        return profesionals;
    }

    public CodigoPostal profesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
        return this;
    }

    public CodigoPostal addProfesional(Profesional profesional) {
        this.profesionals.add(profesional);
        profesional.setCodigoPostal(this);
        return this;
    }

    public CodigoPostal removeProfesional(Profesional profesional) {
        this.profesionals.remove(profesional);
        profesional.setCodigoPostal(null);
        return this;
    }

    public void setProfesionals(Set<Profesional> profesionals) {
        this.profesionals = profesionals;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public CodigoPostal provincia(Provincia provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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
        CodigoPostal codigoPostal = (CodigoPostal) o;
        if (codigoPostal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codigoPostal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodigoPostal{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombreCiudad='" + getNombreCiudad() + "'" +
            "}";
    }
}
