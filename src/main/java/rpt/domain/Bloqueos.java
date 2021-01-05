package rpt.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * A Bloqueos.
 */
@Entity
@Table(name = "bloqueos")
public class Bloqueos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_bloqueo", nullable = false)
    private String nombreBloqueo;

    @NotNull
    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @NotNull
    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    @Column(name = "hora_desde")
    private Instant horaDesde;

    @Column(name = "hora_hasta")
    private Instant horaHasta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreBloqueo() {
        return nombreBloqueo;
    }

    public Bloqueos nombreBloqueo(String nombreBloqueo) {
        this.nombreBloqueo = nombreBloqueo;
        return this;
    }

    public void setNombreBloqueo(String nombreBloqueo) {
        this.nombreBloqueo = nombreBloqueo;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public Bloqueos fechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
        return this;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public Bloqueos fechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
        return this;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Instant getHoraDesde() {
        return horaDesde;
    }

    public Bloqueos horaDesde(Instant horaDesde) {
        this.horaDesde = horaDesde;
        return this;
    }

    public void setHoraDesde(Instant horaDesde) {
        this.horaDesde = horaDesde;
    }

    public Instant getHoraHasta() {
        return horaHasta;
    }

    public Bloqueos horaHasta(Instant horaHasta) {
        this.horaHasta = horaHasta;
        return this;
    }

    public void setHoraHasta(Instant horaHasta) {
        this.horaHasta = horaHasta;
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
        Bloqueos bloqueos = (Bloqueos) o;
        if (bloqueos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bloqueos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bloqueos{" +
            "id=" + getId() +
            ", nombreBloqueo='" + getNombreBloqueo() + "'" +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", horaDesde='" + getHoraDesde() + "'" +
            ", horaHasta='" + getHoraHasta() + "'" +
            "}";
    }

	public Set<Bloqueos> getHorariosProfesionals() {
		return null;
	}
}
