package rpt.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Bloqueos entity.
 */
public class BloqueosDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreBloqueo;

    @NotNull
    private LocalDate fechaDesde;

    @NotNull
    private LocalDate fechaHasta;

    private Instant horaDesde;

    private Instant horaHasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreBloqueo() {
        return nombreBloqueo;
    }

    public void setNombreBloqueo(String nombreBloqueo) {
        this.nombreBloqueo = nombreBloqueo;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Instant getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(Instant horaDesde) {
        this.horaDesde = horaDesde;
    }

    public Instant getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(Instant horaHasta) {
        this.horaHasta = horaHasta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BloqueosDTO bloqueosDTO = (BloqueosDTO) o;
        if (bloqueosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bloqueosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BloqueosDTO{" +
            "id=" + getId() +
            ", nombreBloqueo='" + getNombreBloqueo() + "'" +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", horaDesde='" + getHoraDesde() + "'" +
            ", horaHasta='" + getHoraHasta() + "'" +
            "}";
    }
}
