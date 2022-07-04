package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "direccion_empresa")
    private String direccionEmpresa;

    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;

    @NotNull
    @Column(name = "email_empresa", nullable = false)
    private String emailEmpresa;

    @NotNull
    @Column(name = "nro_sucursal", nullable = false)
    private Integer nroSucursal;

    @OneToMany(mappedBy = "empresa")
    private Set<Personal> personals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private CodigoPostal codigoPostal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public Empresa nombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        return this;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public Empresa direccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
        return this;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public Empresa telefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
        return this;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public Empresa emailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
        return this;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public Integer getNroSucursal() {
        return nroSucursal;
    }

    public Empresa nroSucursal(Integer nroSucursal) {
        this.nroSucursal = nroSucursal;
        return this;
    }

    public void setNroSucursal(Integer nroSucursal) {
        this.nroSucursal = nroSucursal;
    }

    public Set<Personal> getPersonals() {
        return personals;
    }

    public Empresa personals(Set<Personal> personals) {
        this.personals = personals;
        return this;
    }

    public Empresa addPersonal(Personal personal) {
        this.personals.add(personal);
        personal.setEmpresa(this);
        return this;
    }

    public Empresa removePersonal(Personal personal) {
        this.personals.remove(personal);
        personal.setEmpresa(null);
        return this;
    }

    public void setPersonals(Set<Personal> personals) {
        this.personals = personals;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Empresa codigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        Empresa empresa = (Empresa) o;
        if (empresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", direccionEmpresa='" + getDireccionEmpresa() + "'" +
            ", telefonoEmpresa='" + getTelefonoEmpresa() + "'" +
            ", emailEmpresa='" + getEmailEmpresa() + "'" +
            ", nroSucursal=" + getNroSucursal() +
            "}";
    }
}
