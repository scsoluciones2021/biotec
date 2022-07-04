package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A Turno.
 */
@Entity
@Table(name = "turno")
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dni", nullable = false)
    private String dni;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "dia", nullable = false)
    private LocalDate dia;

    @NotNull
    @Column(name = "hora", nullable = false)
    // @JsonFormat(pattern = "YYYY-MM-DDThh:mm")
    private LocalDateTime hora;

    @NotNull
    @Column(name = "id_horario", nullable = false)
    private Integer idHorario;

    @NotNull
    @Column(name = "tipo_paciente", nullable = false)
    private Integer tipoPaciente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Especialidad tur_esp_rel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Profesional tur_prof_rel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private ObraSocial tur_obs_rel;

    @NotNull
    @Column(name = "horarios_ocupados", nullable = false)
    private String horariosOcupados;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private Long usuarioCarga;

    //1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado"
    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public Turno dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public Turno apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Turno nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Turno telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Turno email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDia() {
        return dia;
    }

    public Turno dia(LocalDate dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public Turno hora(LocalDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public Turno idHorario(Integer idHorario) {
        this.idHorario = idHorario;
        return this;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Especialidad getTur_esp_rel() {
        return tur_esp_rel;
    }

    public Turno tur_esp_rel(Especialidad especialidad) {
        this.tur_esp_rel = especialidad;
        return this;
    }

    public void setTur_esp_rel(Especialidad especialidad) {
        this.tur_esp_rel = especialidad;
    }

    public Profesional getTur_prof_rel() {
        return tur_prof_rel;
    }

    public Turno tur_prof_rel(Profesional profesional) {
        this.tur_prof_rel = profesional;
        return this;
    }

    public void setTur_prof_rel(Profesional profesional) {
        this.tur_prof_rel = profesional;
    }

    public ObraSocial getTur_obs_rel() {
        return tur_obs_rel;
    }

    public Turno tur_obs_rel(ObraSocial obraSocial) {
        this.tur_obs_rel = obraSocial;
        return this;
    }

    public void setTur_obs_rel(ObraSocial obraSocial) {
        this.tur_obs_rel = obraSocial;
    }
  

    public Integer getTipoPaciente() {
        return tipoPaciente;
    }

    public Turno tipoPaciente(Integer tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
        return this;
    }

    public void setTipoPaciente(Integer tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
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
        Turno turno = (Turno) o;
        if (turno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Turno{" +
            "id=" + getId() +
            ", dni='" + getDni() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", dia='" + getDia() + "'" +
            ", hora='" + getHora() + "'" +
            ", idHorario=" + getIdHorario() +
            ", tipoPaciente=" + getTipoPaciente() +
            ", estado=" + getEstado() +
            "}";
    }

    /**
     * @return the horariosOcupados
     */
    public String getHorariosOcupados() {
        return horariosOcupados;
    }

    /**
     * @param horariosOcupados the horariosOcupados to set
     */
    public void setHorariosOcupados(String horariosOcupados) {
        this.horariosOcupados = horariosOcupados;
    }

    public Long getUsuarioCarga() {
        return usuarioCarga;
    }

    public void setUsuarioCarga(Long usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
    }

    public Turno usuarioCarga(Long usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
        return this;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Turno estado(Integer estado) {
        this.estado = estado;
        return this;
    }
}
