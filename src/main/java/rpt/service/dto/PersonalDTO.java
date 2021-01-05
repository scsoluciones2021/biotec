package rpt.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Personal entity.
 */
public class PersonalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombrePersonal;

    @NotNull
    private String apellidoPersonal;

    @NotNull
    private String documentoPersonal;

    private String direccionPersonal;

    private String telefonoPersonal;

    @NotNull
    private String emailPersonal;

    private Long usuarioId;

    private Long empresaId;

    private String empresaNombreEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getApellidoPersonal() {
        return apellidoPersonal;
    }

    public void setApellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
    }

    public String getDocumentoPersonal() {
        return documentoPersonal;
    }

    public void setDocumentoPersonal(String documentoPersonal) {
        this.documentoPersonal = documentoPersonal;
    }

    public String getDireccionPersonal() {
        return direccionPersonal;
    }

    public void setDireccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNombreEmpresa() {
        return empresaNombreEmpresa;
    }

    public void setEmpresaNombreEmpresa(String empresaNombreEmpresa) {
        this.empresaNombreEmpresa = empresaNombreEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalDTO personalDTO = (PersonalDTO) o;
        if (personalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalDTO{" +
            "id=" + getId() +
            ", nombrePersonal='" + getNombrePersonal() + "'" +
            ", apellidoPersonal='" + getApellidoPersonal() + "'" +
            ", documentoPersonal='" + getDocumentoPersonal() + "'" +
            ", direccionPersonal='" + getDireccionPersonal() + "'" +
            ", telefonoPersonal='" + getTelefonoPersonal() + "'" +
            ", emailPersonal='" + getEmailPersonal() + "'" +
            ", usuario=" + getUsuarioId() +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            "}";
    }
}
