package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import java.time.LocalDate;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_paciente", nullable = false)
    private String nombrePaciente;

    @NotNull
    @Column(name = "apellido_paciente", nullable = false)
    private String apellidoPaciente;

    @NotNull
    @Column(name = "documento_paciente", nullable = false)
    private String documentoPaciente;

    @Column(name = "direccion_paciente")
    private String direccionPaciente;

    @Column(name = "telefono_paciente")
    private String telefonoPaciente;

    @NotNull
    @Column(name = "email_paciente", nullable = false)
    private String emailPaciente;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "edad")
    private Integer edad;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Provincia provincia;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "paciente")
    private Set<Ficha> fichas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private CodigoPostal codigoPostal;

    @ManyToMany
    @JoinTable(name = "paciente_obrasocial",
               joinColumns = @JoinColumn(name = "pacientes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "obrasocials_id", referencedColumnName = "id"))
    private Set<ObraSocial> obrasocials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public Paciente nombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
        return this;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public Paciente apellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
        return this;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getDocumentoPaciente() {
        return documentoPaciente;
    }

    public Paciente documentoPaciente(String documentoPaciente) {
        this.documentoPaciente = documentoPaciente;
        return this;
    }

    public void setDocumentoPaciente(String documentoPaciente) {
        this.documentoPaciente = documentoPaciente;
    }

    public String getDireccionPaciente() {
        return direccionPaciente;
    }

    public Paciente direccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
        return this;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
    }

    public String getTelefonoPaciente() {
        return telefonoPaciente;
    }

    public Paciente telefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
        return this;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public Paciente emailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
        return this;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fn) {
        this.fechaNacimiento = fn;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

public Set<ObraSocial> getObrasocials() {
    return obrasocials;
}

public Paciente obrasocials(Set<ObraSocial> obraSocials) {
    this.obrasocials = obraSocials;
    return this;
}

public Paciente addObrasocial(ObraSocial obraSocial) {
    this.obrasocials.add(obraSocial);
    obraSocial.getPacientes().add(this);
    return this;
}

public Paciente removeObrasocial(ObraSocial obraSocial) {
    this.obrasocials.remove(obraSocial);
    obraSocial.getPacientes().remove(this);
    return this;
}

public void setObrasocials(Set<ObraSocial> obraSocials) {
    this.obrasocials = obraSocials;
}



    public User getUsuario() {
        return usuario;
    }

    public Paciente usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Ficha> getFichas() {
        return fichas;
    }

    public Paciente fichas(Set<Ficha> fichas) {
        this.fichas = fichas;
        return this;
    }

    public Paciente addFicha(Ficha ficha) {
        this.fichas.add(ficha);
        ficha.setPaciente(this);
        return this;
    }

    public Paciente removeFicha(Ficha ficha) {
        this.fichas.remove(ficha);
        ficha.setPaciente(null);
        return this;
    }

    public void setFichas(Set<Ficha> fichas) {
        this.fichas = fichas;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Paciente codigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    /***** */
    public Provincia getProvincia() {
        return provincia;
    }

    public Paciente provincia(Provincia provincia) {
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
        Paciente paciente = (Paciente) o;
        if (paciente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paciente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombrePaciente='" + getNombrePaciente() + "'" +
            ", apellidoPaciente='" + getApellidoPaciente() + "'" +
            ", documentoPaciente='" + getDocumentoPaciente() + "'" +
            ", direccionPaciente='" + getDireccionPaciente() + "'" +
            ", telefonoPaciente='" + getTelefonoPaciente() + "'" +
            ", emailPaciente='" + getEmailPaciente() + "'" +
            "}";
    }
}
