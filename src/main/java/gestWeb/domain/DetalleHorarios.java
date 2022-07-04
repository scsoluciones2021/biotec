package gestWeb.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DetalleHorarios.
 */
@Entity
@Table(name = "detalle_horarios")
public class DetalleHorarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_horario", nullable = false)
    private Long idHorario;

    @NotNull
    @Column(name = "hora_desde", nullable = false)
    private String horaDesde;

    @NotNull
    @Column(name = "hora_hasta", nullable = false)
    private String horaHasta;

    @Column(name = "lunes")
    private Integer lunes;

    @Column(name = "martes")
    private Integer martes;

    @Column(name = "miercoles")
    private Integer miercoles;

    @Column(name = "jueves")
    private Integer jueves;

    @Column(name = "viernes")
    private Integer viernes;

    @Column(name = "sabado")
    private Integer sabado;

    @Column(name = "domingo")
    private Integer domingo;

    @NotNull
    @Column(name = "intervalo", nullable = false)
    private Integer intervalo;

    @NotNull
    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @NotNull
    @Min(value = 1)
    @Column(name = "cantidad_pacientes", nullable = false)
    private Integer cantidadPacientes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public DetalleHorarios idHorario(Long idHorario) {
        this.idHorario = idHorario;
        return this;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public DetalleHorarios horaDesde(String horaDesde) {
        this.horaDesde = horaDesde;
        return this;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public DetalleHorarios horaHasta(String horaHasta) {
        this.horaHasta = horaHasta;
        return this;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public Integer getLunes() {
        return lunes;
    }

    public DetalleHorarios lunes(Integer lunes) {
        this.lunes = lunes;
        return this;
    }

    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }

    public Integer getMartes() {
        return martes;
    }

    public DetalleHorarios martes(Integer martes) {
        this.martes = martes;
        return this;
    }

    public void setMartes(Integer martes) {
        this.martes = martes;
    }

    public Integer getMiercoles() {
        return miercoles;
    }

    public DetalleHorarios miercoles(Integer miercoles) {
        this.miercoles = miercoles;
        return this;
    }

    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }

    public Integer getJueves() {
        return jueves;
    }

    public DetalleHorarios jueves(Integer jueves) {
        this.jueves = jueves;
        return this;
    }

    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }

    public Integer getViernes() {
        return viernes;
    }

    public DetalleHorarios viernes(Integer viernes) {
        this.viernes = viernes;
        return this;
    }

    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }

    public Integer getSabado() {
        return sabado;
    }

    public DetalleHorarios sabado(Integer sabado) {
        this.sabado = sabado;
        return this;
    }

    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }

    public Integer getDomingo() {
        return domingo;
    }

    public DetalleHorarios domingo(Integer domingo) {
        this.domingo = domingo;
        return this;
    }

    public void setDomingo(Integer domingo) {
        this.domingo = domingo;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public DetalleHorarios intervalo(Integer intervalo) {
        this.intervalo = intervalo;
        return this;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public DetalleHorarios frecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
        return this;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getCantidadPacientes() {
        return cantidadPacientes;
    }

    public DetalleHorarios cantidadPacientes(Integer cantidadPacientes) {
        this.cantidadPacientes = cantidadPacientes;
        return this;
    }

    public void setCantidadPacientes(Integer cantidadPacientes) {
        this.cantidadPacientes = cantidadPacientes;
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
        DetalleHorarios detalleHorarios = (DetalleHorarios) o;
        if (detalleHorarios.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleHorarios.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleHorarios{" +
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
