package gestWeb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Empresa entity.
 */
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreEmpresa;

    private String direccionEmpresa;

    private String telefonoEmpresa;

    @NotNull
    private String emailEmpresa;

    @NotNull
    private Integer nroSucursal;

    private Long codigoPostalId;

    private String codigoPostalNombreCiudad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public Integer getNroSucursal() {
        return nroSucursal;
    }

    public void setNroSucursal(Integer nroSucursal) {
        this.nroSucursal = nroSucursal;
    }

    public Long getCodigoPostalId() {
        return codigoPostalId;
    }

    public void setCodigoPostalId(Long codigoPostalId) {
        this.codigoPostalId = codigoPostalId;
    }

    public String getCodigoPostalNombreCiudad() {
        return codigoPostalNombreCiudad;
    }

    public void setCodigoPostalNombreCiudad(String codigoPostalNombreCiudad) {
        this.codigoPostalNombreCiudad = codigoPostalNombreCiudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (empresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", direccionEmpresa='" + getDireccionEmpresa() + "'" +
            ", telefonoEmpresa='" + getTelefonoEmpresa() + "'" +
            ", emailEmpresa='" + getEmailEmpresa() + "'" +
            ", nroSucursal=" + getNroSucursal() +
            ", codigoPostal=" + getCodigoPostalId() +
            ", codigoPostal='" + getCodigoPostalNombreCiudad() + "'" +
            "}";
    }
}
