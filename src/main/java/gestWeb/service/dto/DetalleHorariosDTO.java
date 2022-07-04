package gestWeb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DetalleHorarios entity.
 */
public class DetalleHorariosDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idHorario;

    @NotNull
    private String horaDesde;

    @NotNull
    private String horaHasta;

    private Integer lunes;

    private Integer martes;

    private Integer miercoles;

    private Integer jueves;

    private Integer viernes;

    private Integer sabado;

    private Integer domingo;

    @NotNull
    private Integer intervalo;

    @NotNull
    private Integer frecuencia;

    @NotNull
    @Min(value = 1)
    private Integer cantidadPacientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public Integer getLunes() {
        return lunes;
    }

    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }

    public Integer getMartes() {
        return martes;
    }

    public void setMartes(Integer martes) {
        this.martes = martes;
    }

    public Integer getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }

    public Integer getJueves() {
        return jueves;
    }

    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }

    public Integer getViernes() {
        return viernes;
    }

    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }

    public Integer getSabado() {
        return sabado;
    }

    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }

    public Integer getDomingo() {
        return domingo;
    }

    public void setDomingo(Integer domingo) {
        this.domingo = domingo;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getCantidadPacientes() {
        return cantidadPacientes;
    }

    public void setCantidadPacientes(Integer cantidadPacientes) {
        this.cantidadPacientes = cantidadPacientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalleHorariosDTO detalleHorariosDTO = (DetalleHorariosDTO) o;
        if (detalleHorariosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleHorariosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleHorariosDTO{" +
            "id=" + getId() +
            ", idHorario=" + getIdHorario() +
            ", horaDesde='" + getHoraDesde() + "'" +
            ", horaHasta='" + getHoraHasta() + "'" +
            ", lunes=" + getLunes() +
            ", martes=" + getMartes() +
            ", miercoles=" + getMiercoles() +
            ", jueves=" + getJueves() +
            ", viernes=" + getViernes() +
            ", sabado=" + getSabado() +
            ", domingo=" + getDomingo() +
            ", intervalo=" + getIntervalo() +
            ", frecuencia=" + getFrecuencia() +
            ", cantidadPacientes=" + getCantidadPacientes() +
            "}";
    }
}
