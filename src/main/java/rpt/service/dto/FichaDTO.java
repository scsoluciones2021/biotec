package rpt.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ficha entity.
 */
public class FichaDTO implements Serializable {

    private Long id;

    private LocalDate fechaIngreso;

    private Long pacienteId;

    private String pacienteNombrePaciente;

    private Long profesionalId;

    private String profesionalNombreProfesional;
    
    private Long especialidadId;
    
    private String especialidadNombreEspecialidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNombrePaciente() {
        return pacienteNombrePaciente;
    }

    public void setPacienteNombrePaciente(String pacienteNombrePaciente) {
        this.pacienteNombrePaciente = pacienteNombrePaciente;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public String getProfesionalNombreProfesional() {
        return profesionalNombreProfesional;
    }

    public void setProfesionalNombreProfesional(String profesionalNombreProfesional) {
        this.profesionalNombreProfesional = profesionalNombreProfesional;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getEspecialidadNombreEspecialidad() {
        return especialidadNombreEspecialidad;
    }

    public void setEspecialidadNombreEspecialidad(String especialidadNombreEspecialidad) {
        this.especialidadNombreEspecialidad = especialidadNombreEspecialidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaDTO fichaDTO = (FichaDTO) o;
        if (fichaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaDTO{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", paciente=" + getPacienteId() +
            ", paciente='" + getPacienteNombrePaciente() + "'" +
            ", profesional=" + getProfesionalId() +
            ", profesional='" + getProfesionalNombreProfesional() + "'" +
            ", especialidad=" + getEspecialidadId() +
            ", especialidad='" + getEspecialidadNombreEspecialidad() + "'" +
            "}";
    }
}
