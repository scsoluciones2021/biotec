package rpt.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Turno entity.
 */
public class TurnoProfDTO implements Serializable {

    @NotNull
    private LocalDate fecha;

    private Set<EspecialidadDTO> especialidades = new HashSet<>();

    private Long profesional;

    private Integer estado;

    private String retorno;

    public Long getProfesional() {
        return profesional;
    }

    public void setProfesional(Long profesional){
        this.profesional = profesional;
    }
    
    public Set<EspecialidadDTO> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Set<EspecialidadDTO>especialidades) {
        this.especialidades = especialidades;
    }
  

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }
    
}
