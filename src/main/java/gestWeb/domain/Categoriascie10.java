package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Categoriascie10.
 */
@Entity
@Table(name = "categoriascie10")
public class Categoriascie10 implements Serializable {

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
    @Column(name = "idsubgrupo", nullable = false)
    private Integer idsubgrupo;

    @ManyToOne
    @JsonIgnoreProperties(value = "categoriascie10s", allowSetters = true)
    private Subgruposcie10 rel_subrupos_categorias_cie10;

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

    public Categoriascie10 clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoriascie10 descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdsubgrupo() {
        return idsubgrupo;
    }

    public Categoriascie10 idsubgrupo(Integer idsubgrupo) {
        this.idsubgrupo = idsubgrupo;
        return this;
    }

    public void setIdsubgrupo(Integer idsubgrupo) {
        this.idsubgrupo = idsubgrupo;
    }

    public Subgruposcie10 getRel_subrupos_categorias_cie10() {
        return rel_subrupos_categorias_cie10;
    }

    public Categoriascie10 rel_subrupos_categorias_cie10(Subgruposcie10 subgruposcie10) {
        this.rel_subrupos_categorias_cie10 = subgruposcie10;
        return this;
    }

    public void setRel_subrupos_categorias_cie10(Subgruposcie10 subgruposcie10) {
        this.rel_subrupos_categorias_cie10 = subgruposcie10;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoriascie10)) {
            return false;
        }
        return id != null && id.equals(((Categoriascie10) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoriascie10{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idsubgrupo=" + getIdsubgrupo() +
            "}";
    }
}
