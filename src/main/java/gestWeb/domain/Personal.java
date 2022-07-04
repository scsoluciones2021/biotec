package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Personal.
 */
@Entity
@Table(name = "personal")
public class Personal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_personal", nullable = false)
    private String nombrePersonal;

    @NotNull
    @Column(name = "apellido_personal", nullable = false)
    private String apellidoPersonal;

    @NotNull
    @Column(name = "documento_personal", nullable = false)
    private String documentoPersonal;

    @Column(name = "direccion_personal")
    private String direccionPersonal;

    @Column(name = "telefono_personal")
    private String telefonoPersonal;

    @NotNull
    @Column(name = "email_personal", nullable = false)
    private String emailPersonal;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("personals")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public Personal nombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
        return this;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getApellidoPersonal() {
        return apellidoPersonal;
    }

    public Personal apellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
        return this;
    }

    public void setApellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
    }

    public String getDocumentoPersonal() {
        return documentoPersonal;
    }

    public Personal documentoPersonal(String documentoPersonal) {
        this.documentoPersonal = documentoPersonal;
        return this;
    }

    public void setDocumentoPersonal(String documentoPersonal) {
        this.documentoPersonal = documentoPersonal;
    }

    public String getDireccionPersonal() {
        return direccionPersonal;
    }

    public Personal direccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
        return this;
    }

    public void setDireccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public Personal telefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
        return this;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public Personal emailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
        return this;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public User getUsuario() {
        return usuario;
    }

    public Personal usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Personal empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        Personal personal = (Personal) o;
        if (personal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personal{" +
            "id=" + getId() +
            ", nombrePersonal='" + getNombrePersonal() + "'" +
            ", apellidoPersonal='" + getApellidoPersonal() + "'" +
            ", documentoPersonal='" + getDocumentoPersonal() + "'" +
            ", direccionPersonal='" + getDireccionPersonal() + "'" +
            ", telefonoPersonal='" + getTelefonoPersonal() + "'" +
            ", emailPersonal='" + getEmailPersonal() + "'" +
            "}";
    }
}
