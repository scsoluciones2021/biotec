package gestWeb.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Adjuntos_ficha.
 */
@Entity
@Table(name = "adjuntos_ficha")
public class Adjuntos_ficha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_ficha")
    private Long idFicha;

    @Column(name = "id_profesional")
    private Long idProfesional;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(name = "id_especialidad")
    private Long idEspecialidad;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "nombre_actual")
    private String nombreActual;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Column(name = "usuario")
    private Integer usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFicha() {
        return idFicha;
    }

    public Adjuntos_ficha idFicha(Long idFicha) {
        this.idFicha = idFicha;
        return this;
    }

    public void setIdFicha(Long idFicha) {
        this.idFicha = idFicha;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public Adjuntos_ficha idProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
        return this;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public Adjuntos_ficha idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public Adjuntos_ficha idEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
        return this;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getRuta() {
        return ruta;
    }

    public Adjuntos_ficha ruta(String ruta) {
        this.ruta = ruta;
        return this;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public Adjuntos_ficha nombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
        return this;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getNombreActual() {
        return nombreActual;
    }

    public Adjuntos_ficha nombreActual(String nombreActual) {
        this.nombreActual = nombreActual;
        return this;
    }

    public void setNombreActual(String nombreActual) {
        this.nombreActual = nombreActual;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Adjuntos_ficha fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public Adjuntos_ficha usuario(Integer usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
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
        Adjuntos_ficha adjuntos_ficha = (Adjuntos_ficha) o;
        if (adjuntos_ficha.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adjuntos_ficha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adjuntos_ficha{" +
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
