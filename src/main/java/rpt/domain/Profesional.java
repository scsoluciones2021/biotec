package rpt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profesional.
 */
@Entity
@Table(name = "profesional")
public class Profesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_profesional", nullable = false)
    private String nombreProfesional;

    @NotNull
    @Column(name = "documento_profesional", nullable = false)
    private String documentoProfesional;

    @Column(name = "direccion_profesional")
    private String direccionProfesional;

    @Column(name = "telefono_profesional")
    private String telefonoProfesional;

    @Column(name = "email_profesional")
    private String emailProfesional;

    @NotNull
    @Column(name = "matricula_profesional", nullable = false)
    private String matriculaProfesional;

    @Lob
    @Column(name = "imagen_profesional")
    private byte[] imagenProfesional;

    @Column(name = "imagen_profesional_content_type")
    private String imagenProfesionalContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToOne
    @JoinColumn(unique = true)
    private TituloShort titulo;

    @OneToMany(mappedBy = "profesional")
    private Set<Ficha> fichas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesional_obrasocial",
               joinColumns = @JoinColumn(name = "profesionals_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "obrasocials_id", referencedColumnName = "id"))
    private Set<ObraSocial> obrasocials = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesional_especialidad",
               joinColumns = @JoinColumn(name = "profesionals_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "especialidads_id", referencedColumnName = "id"))
    private Set<Especialidad> especialidads = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("profesionals")
    private CodigoPostal codigoPostal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public Profesional nombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
        return this;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

    public String getDocumentoProfesional() {
        return documentoProfesional;
    }

    public Profesional documentoProfesional(String documentoProfesional) {
        this.documentoProfesional = documentoProfesional;
        return this;
    }

    public void setDocumentoProfesional(String documentoProfesional) {
        this.documentoProfesional = documentoProfesional;
    }

    public String getDireccionProfesional() {
        return direccionProfesional;
    }

    public Profesional direccionProfesional(String direccionProfesional) {
        this.direccionProfesional = direccionProfesional;
        return this;
    }

    public void setDireccionProfesional(String direccionProfesional) {
        this.direccionProfesional = direccionProfesional;
    }

    public String getTelefonoProfesional() {
        return telefonoProfesional;
    }

    public Profesional telefonoProfesional(String telefonoProfesional) {
        this.telefonoProfesional = telefonoProfesional;
        return this;
    }

    public void setTelefonoProfesional(String telefonoProfesional) {
        this.telefonoProfesional = telefonoProfesional;
    }

    public String getEmailProfesional() {
        return emailProfesional;
    }

    public Profesional emailProfesional(String emailProfesional) {
        this.emailProfesional = emailProfesional;
        return this;
    }

    public void setEmailProfesional(String emailProfesional) {
        this.emailProfesional = emailProfesional;
    }

    public String getMatriculaProfesional() {
        return matriculaProfesional;
    }

    public Profesional matriculaProfesional(String matriculaProfesional) {
        this.matriculaProfesional = matriculaProfesional;
        return this;
    }

    public void setMatriculaProfesional(String matriculaProfesional) {
        this.matriculaProfesional = matriculaProfesional;
    }

    public byte[] getImagenProfesional() {
        return imagenProfesional;
    }

    public Profesional imagenProfesional(byte[] imagenProfesional) {
        this.imagenProfesional = imagenProfesional;
        return this;
    }

    public void setImagenProfesional(byte[] imagenProfesional) {
        this.imagenProfesional = imagenProfesional;
    }

    public String getImagenProfesionalContentType() {
        return imagenProfesionalContentType;
    }

    public Profesional imagenProfesionalContentType(String imagenProfesionalContentType) {
        this.imagenProfesionalContentType = imagenProfesionalContentType;
        return this;
    }

    public void setImagenProfesionalContentType(String imagenProfesionalContentType) {
        this.imagenProfesionalContentType = imagenProfesionalContentType;
    }

    public User getUsuario() {
        return usuario;
    }

    public Profesional usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public TituloShort getTitulo() {
        return titulo;
    }

    public Profesional titulo(TituloShort tituloShort) {
        this.titulo = tituloShort;
        return this;
    }

    public void setTitulo(TituloShort tituloShort) {
        this.titulo = tituloShort;
    }

    public Set<Ficha> getFichas() {
        return fichas;
    }

    public Profesional fichas(Set<Ficha> fichas) {
        this.fichas = fichas;
        return this;
    }

    public Profesional addFicha(Ficha ficha) {
        this.fichas.add(ficha);
        ficha.setProfesional(this);
        return this;
    }

    public Profesional removeFicha(Ficha ficha) {
        this.fichas.remove(ficha);
        ficha.setProfesional(null);
        return this;
    }

    public void setFichas(Set<Ficha> fichas) {
        this.fichas = fichas;
    }

    public Set<ObraSocial> getObrasocials() {
        return obrasocials;
    }

    public Profesional obrasocials(Set<ObraSocial> obraSocials) {
        this.obrasocials = obraSocials;
        return this;
    }

    public Profesional addObrasocial(ObraSocial obraSocial) {
        this.obrasocials.add(obraSocial);
        obraSocial.getProfesionals().add(this);
        return this;
    }

    public Profesional removeObrasocial(ObraSocial obraSocial) {
        this.obrasocials.remove(obraSocial);
        obraSocial.getProfesionals().remove(this);
        return this;
    }

    public void setObrasocials(Set<ObraSocial> obraSocials) {
        this.obrasocials = obraSocials;
    }

    public Set<Especialidad> getEspecialidads() {
        return especialidads;
    }

    public Profesional especialidads(Set<Especialidad> especialidads) {
        this.especialidads = especialidads;
        return this;
    }

    public Profesional addEspecialidad(Especialidad especialidad) {
        this.especialidads.add(especialidad);
        especialidad.getProfesionals().add(this);
        return this;
    }

    public Profesional removeEspecialidad(Especialidad especialidad) {
        this.especialidads.remove(especialidad);
        especialidad.getProfesionals().remove(this);
        return this;
    }

    public void setEspecialidads(Set<Especialidad> especialidads) {
        this.especialidads = especialidads;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Profesional codigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        Profesional profesional = (Profesional) o;
        if (profesional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profesional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profesional{" +
            "id=" + getId() +
            ", nombreProfesional='" + getNombreProfesional() + "'" +
            ", documentoProfesional='" + getDocumentoProfesional() + "'" +
            ", direccionProfesional='" + getDireccionProfesional() + "'" +
            ", telefonoProfesional='" + getTelefonoProfesional() + "'" +
            ", emailProfesional='" + getEmailProfesional() + "'" +
            ", matriculaProfesional='" + getMatriculaProfesional() + "'" +
            ", imagenProfesional='" + getImagenProfesional() + "'" +
            ", imagenProfesionalContentType='" + getImagenProfesionalContentType() + "'" +
            "}";
    }
}
