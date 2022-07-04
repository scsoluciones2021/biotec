package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Subgruposcie10.
 */
@Entity
@Table(name = "subgruposcie10")
public class Subgruposcie10 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "clave", nullable = false)
    private String clave;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "id_grupo", nullable = false)
    private Integer idGrupo;

    @ManyToOne
    @JsonIgnoreProperties(value = "subgruposcie10s", allowSetters = true)
    private Gruposcie10 rel_grupo_subgrupo_cie10;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public Subgruposcie10 clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Subgruposcie10 descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public Subgruposcie10 idGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
        return this;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Gruposcie10 getRel_grupo_subgrupo_cie10() {
        return rel_grupo_subgrupo_cie10;
    }

    public Subgruposcie10 rel_grupo_subgrupo_cie10(Gruposcie10 gruposcie10) {
        this.rel_grupo_subgrupo_cie10 = gruposcie10;
        return this;
    }

    public void setRel_grupo_subgrupo_cie10(Gruposcie10 gruposcie10) {
        this.rel_grupo_subgrupo_cie10 = gruposcie10;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subgruposcie10)) {
            return false;
        }
        return id != null && id.equals(((Subgruposcie10) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subgruposcie10{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idGrupo=" + getIdGrupo() +
            "}";
    }
}
