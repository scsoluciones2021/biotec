package rpt.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Adjuntos_ficha entity.
 */
public class Adjuntos_fichaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long idFicha;

    private Long idProfesional;

    private Long idPaciente;

    private Long idEspecialidad;

    private String ruta;

    private String nombreOriginal;

    private String nombreActual;

    private ZonedDateTime fecha;

    private Integer usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Long idFicha) {
        this.idFicha = idFicha;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getNombreActual() {
        return nombreActual;
    }

    public void setNombreActual(String nombreActual) {
        this.nombreActual = nombreActual;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Adjuntos_fichaDTO adjuntos_fichaDTO = (Adjuntos_fichaDTO) o;
        if (adjuntos_fichaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adjuntos_fichaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adjuntos_fichaDTO{" +
            "id=" + getId() +
            ", idFicha=" + getIdFicha() +
            ", idProfesional=" + getIdProfesional() +
            ", idPaciente=" + getIdPaciente() +
            ", idEspecialidad=" + getIdEspecialidad() +
            ", ruta='" + getRuta() + "'" +
            ", nombreOriginal='" + getNombreOriginal() + "'" +
            ", nombreActual='" + getNombreActual() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", usuario=" + getUsuario() +
            "}";
    }
}
