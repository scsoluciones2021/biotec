package rpt.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Turno entity.
 */
public class TurnoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String dni;

    @NotNull
    private String apellido;

    @NotNull
    private String nombre;

    private String telefono;

    private String email;

    @NotNull
    private LocalDate dia;

    @NotNull
    private LocalDateTime hora;

    @NotNull
    private Integer idHorario;

    @NotNull
    private Integer tipoPaciente;

    private Long tur_esp_relId;

    private String tur_esp_relNombreEspecialidad;

    private Long tur_prof_relId;

    private String tur_prof_relNombreProfesional;

    private Long tur_obs_relId;

    private String tur_obs_relNombreObraSocial;

    @NotNull
    private String horariosOcupados;

    @NotNull
    private Long usuarioCarga;   

    @NotNull
    private Integer estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getTipoPaciente() {
        return tipoPaciente;
    }

    public void setTipoPaciente(Integer tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
    }

    public Long getTur_esp_relId() {
        return tur_esp_relId;
    }

    public void setTur_esp_relId(Long especialidadId) {
        this.tur_esp_relId = especialidadId;
    }

    public String getTur_esp_relNombreEspecialidad() {
        return tur_esp_relNombreEspecialidad;
    }

    public void setTur_esp_relNombreEspecialidad(String especialidadNombreEspecialidad) {
        this.tur_esp_relNombreEspecialidad = especialidadNombreEspecialidad;
    }

    public Long getTur_prof_relId() {
        return tur_prof_relId;
    }

    public void setTur_prof_relId(Long profesionalId) {
        this.tur_prof_relId = profesionalId;
    }

    public String getTur_prof_relNombreProfesional() {
        return tur_prof_relNombreProfesional;
    }

    public void setTur_prof_relNombreProfesional(String profesionalNombreProfesional) {
        this.tur_prof_relNombreProfesional = profesionalNombreProfesional;
    }

    public Long getTur_obs_relId() {
        return tur_obs_relId;
    }

    public void setTur_obs_relId(Long obraSocialId) {
        this.tur_obs_relId = obraSocialId;
    }

    public String getTur_obs_relNombreObraSocial() {
        return tur_obs_relNombreObraSocial;
    }

    public void setTur_obs_relNombreObraSocial(String obraSocialNombreObraSocial) {
        this.tur_obs_relNombreObraSocial = obraSocialNombreObraSocial;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param edad the edad to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TurnoDTO turnoDTO = (TurnoDTO) o;
        if (turnoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turnoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TurnoDTO{" +
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
            ", tur_esp_rel=" + getTur_esp_relId() +
            ", tur_esp_rel='" + getTur_esp_relNombreEspecialidad() + "'" +
            ", tur_prof_rel=" + getTur_prof_relId() +
            ", tur_prof_rel='" + getTur_prof_relNombreProfesional() + "'" +
             ", tur_obs_rel=" + getTur_obs_relId() +
            ", tur_obs_rel='" + getTur_obs_relNombreObraSocial() + "'" +
            ", usuarioCarga=" + getUsuarioCarga() +
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
    
}
