package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ObraSocial entity.
 */
public class ObraSocialDTO implements Serializable {

    private Long id;

    private String codigoObraSocial;

    private String nombreObraSocial;

    private String direccionObraSocial;

    private String telefonoObraSocial;

    private String emailObraSocial;

    private String siglaObraSocial;

    private String agrupadorNombre;

    private Long agrupadorId;

    public Long getId() {
        return id;
    }

    /**
     * @return the agrupadorId
     */
    public Long getAgrupadorId() {
        return agrupadorId;
    }

    /**
     * @param agrupadorId the agrupadorId to set
     */
    public void setAgrupadorId(Long agrupadorId) {
        this.agrupadorId = agrupadorId;
    }

    /**
     * @return the agrupadorNombre
     */
    public String getAgrupadorNombre() {
        return agrupadorNombre;
    }

    /**
     * @param agrupadorNombre the agrupadorNombre to set
     */
    public void setAgrupadorNombre(String agrupadorNombre) {
        this.agrupadorNombre = agrupadorNombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoObraSocial() {
        return codigoObraSocial;
    }

    public void setCodigoObraSocial(String codigoObraSocial) {
        this.codigoObraSocial = codigoObraSocial;
    }

    public String getNombreObraSocial() {
        return nombreObraSocial;
    }

    public void setNombreObraSocial(String nombreObraSocial) {
        this.nombreObraSocial = nombreObraSocial;
    }

    public String getDireccionObraSocial() {
        return direccionObraSocial;
    }

    public void setDireccionObraSocial(String direccionObraSocial) {
        this.direccionObraSocial = direccionObraSocial;
    }

    public String getTelefonoObraSocial() {
        return telefonoObraSocial;
    }

    public void setTelefonoObraSocial(String telefonoObraSocial) {
        this.telefonoObraSocial = telefonoObraSocial;
    }

    public String getEmailObraSocial() {
        return emailObraSocial;
    }

    public void setEmailObraSocial(String emailObraSocial) {
        this.emailObraSocial = emailObraSocial;
    }

    public String getSiglaObraSocial() {
        return siglaObraSocial;
    }

    public void setSiglaObraSocial(String siglaObraSocial) {
        this.siglaObraSocial = siglaObraSocial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObraSocialDTO obraSocialDTO = (ObraSocialDTO) o;
        if (obraSocialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obraSocialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObraSocialDTO{" +
            "id=" + getId() +
            ", codigoObraSocial='" + getCodigoObraSocial() + "'" +
            ", nombreObraSocial='" + getNombreObraSocial() + "'" +
            ", direccionObraSocial='" + getDireccionObraSocial() + "'" +
            ", telefonoObraSocial='" + getTelefonoObraSocial() + "'" +
            ", emailObraSocial='" + getEmailObraSocial() + "'" +
            ", siglaObraSocial='" + getSiglaObraSocial() + "'" +
            "}";
    }

}
