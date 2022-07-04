package gestWeb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the Paciente entity.
 */
public class PacienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombrePaciente;

    @NotNull
    private String apellidoPaciente;

    @NotNull
    private String documentoPaciente;

    private String direccionPaciente;

    private String telefonoPaciente;
 
    @NotNull
    private String emailPaciente;
    
    private LocalDate fechaNacimiento;

    private Integer edad;
    /*
     * private Long pacienteObraSocialId;
     * 
     * private String pacienteObraSocialNombreObraSocial;
     */
    private Set<ObraSocialDTO> obrasocials = new HashSet<>();

    private Long usuarioId;

    private Long codigoPostalId;

    private String codigoPostalNombreCiudad;

    private Long provinciaId;

    private String provinciaNombreProvincia;

    public Long getId() {
        return id;
    }

    /**
     * @return the fechaNacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getDocumentoPaciente() {
        return documentoPaciente;
    }

    public void setDocumentoPaciente(String documentoPaciente) {
        this.documentoPaciente = documentoPaciente;
    }

    public String getDireccionPaciente() {
        return direccionPaciente;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
    }

    public String getTelefonoPaciente() {
        return telefonoPaciente;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public Set<ObraSocialDTO> getObrasocials() {
        return obrasocials;
    }

    public void setObrasocials(Set<ObraSocialDTO> obraSocials) {
        this.obrasocials = obraSocials;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
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

    /******** */

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    public String getProvinciaNombreProvincia() {
        return provinciaNombreProvincia;
    }

    public void setProvinciaNombreProvincia(String provinciaNombreProvincia) {
        this.provinciaNombreProvincia = provinciaNombreProvincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;
        if (pacienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacienteDTO{" + "id=" + getId() + ", nombrePaciente='" + getNombrePaciente() + "'"
                + ", apellidoPaciente='" + getApellidoPaciente() + "'" + ", documentoPaciente='"
                + getDocumentoPaciente() + "'" + ", direccionPaciente='" + getDireccionPaciente() + "'"
                + ", telefonoPaciente='" + getTelefonoPaciente() + "'" + ", emailPaciente='" + getEmailPaciente() + "'"
                +
                /*
                 * ", pacienteObraSocial=" + getPacienteObraSocialId() +
                 * ", pacienteObraSocial='" + getPacienteObraSocialNombreObraSocial() + "'" +
                 */
                ", usuario=" + getUsuarioId() + ", codigoPostal=" + getCodigoPostalId() + ", codigoPostal='"
                + getCodigoPostalNombreCiudad() + "'" + "}";
    }
}
