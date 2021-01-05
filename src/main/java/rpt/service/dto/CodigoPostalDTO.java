package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CodigoPostal entity.
 */
public class CodigoPostalDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nombreCiudad;

    private Long provinciaId;

    private String provinciaNombreProvincia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

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

        CodigoPostalDTO codigoPostalDTO = (CodigoPostalDTO) o;
        if (codigoPostalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codigoPostalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodigoPostalDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombreCiudad='" + getNombreCiudad() + "'" +
            ", provincia=" + getProvinciaId() +
            ", provincia='" + getProvinciaNombreProvincia() + "'" +
            "}";
    }
}
