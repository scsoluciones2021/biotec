package gestWeb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Profesional entity.
 */
public class ProfesionalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreProfesional;

    @NotNull
    private String apellidoProfesional;

    @NotNull
    private String documentoProfesional;

    private String direccionProfesional;

    private String telefonoProfesional;

    private String emailProfesional;

    @NotNull
    private String matriculaProfesional;

    @Lob
    private byte[] imagenProfesional;
    private String imagenProfesionalContentType;

    private Long usuarioId;

    private Long tituloId;

    private String tituloValor;

    private Set<ObraSocialDTO> obrasocials = new HashSet<>();

    private Set<EspecialidadDTO> especialidads = new HashSet<>();

    private Long codigoPostalId;

    private String codigoPostalNombreCiudad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

    public String getApellidoProfesional() {
        return apellidoProfesional;
    }

    public void setApellidoProfesional(String apellidoProfesional) {
        this.apellidoProfesional = apellidoProfesional;
    }

    public String getDocumentoProfesional() {
        return documentoProfesional;
    }

    public void setDocumentoProfesional(String documentoProfesional) {
        this.documentoProfesional = documentoProfesional;
    }

    public String getDireccionProfesional() {
        return direccionProfesional;
    }

    public void setDireccionProfesional(String direccionProfesional) {
        this.direccionProfesional = direccionProfesional;
    }

    public String getTelefonoProfesional() {
        return telefonoProfesional;
    }

    public void setTelefonoProfesional(String telefonoProfesional) {
        this.telefonoProfesional = telefonoProfesional;
    }

    public String getEmailProfesional() {
        return emailProfesional;
    }

    public void setEmailProfesional(String emailProfesional) {
        this.emailProfesional = emailProfesional;
    }

    public String getMatriculaProfesional() {
        return matriculaProfesional;
    }

    public void setMatriculaProfesional(String matriculaProfesional) {
        this.matriculaProfesional = matriculaProfesional;
    }

    public byte[] getImagenProfesional() {
        return imagenProfesional;
    }

    public void setImagenProfesional(byte[] imagenProfesional) {
        this.imagenProfesional = imagenProfesional;
    }

    public String getImagenProfesionalContentType() {
        return imagenProfesionalContentType;
    }

    public void setImagenProfesionalContentType(String imagenProfesionalContentType) {
        this.imagenProfesionalContentType = imagenProfesionalContentType;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public Long getTituloId() {
        return tituloId;
    }

    public void setTituloId(Long tituloShortId) {
        this.tituloId = tituloShortId;
    }

    public String getTituloValor() {
        return tituloValor;
    }

    public void setTituloValor(String tituloShortValor) {
        this.tituloValor = tituloShortValor;
    }

    public Set<ObraSocialDTO> getObrasocials() {
        return obrasocials;
    }

    public void setObrasocials(Set<ObraSocialDTO> obraSocials) {
        this.obrasocials = obraSocials;
    }

    public Set<EspecialidadDTO> getEspecialidads() {
        return especialidads;
    }

    public void setEspecialidads(Set<EspecialidadDTO> especialidads) {
        this.especialidads = especialidads;
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

        ProfesionalDTO profesionalDTO = (ProfesionalDTO) o;
        if (profesionalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profesionalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfesionalDTO{" +
            "id=" + getId() +
            ", nombreProfesional='" + getNombreProfesional() + "'" +
            ", apellidoProfesional='" + getApellidoProfesional() + "'" +
            ", documentoProfesional='" + getDocumentoProfesional() + "'" +
            ", direccionProfesional='" + getDireccionProfesional() + "'" +
            ", telefonoProfesional='" + getTelefonoProfesional() + "'" +
            ", emailProfesional='" + getEmailProfesional() + "'" +
            ", matriculaProfesional='" + getMatriculaProfesional() + "'" +
            ", imagenProfesional='" + getImagenProfesional() + "'" +
            ", usuario=" + getUsuarioId() +
            ", titulo=" + getTituloId() +
            ", titulo='" + getTituloValor() + "'" +
            ", codigoPostal=" + getCodigoPostalId() +
            ", codigoPostal='" + getCodigoPostalNombreCiudad() + "'" +
            "}";
    }
}
