package gestWeb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Diagnosticoscie10.
 */
@Entity
@Table(name = "diagnosticoscie10")
public class Diagnosticoscie10 implements Serializable {

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
    @Column(name = "idcategoria", nullable = false)
    private Integer idcategoria;

    @ManyToOne
    @JsonIgnoreProperties(value = "diagnosticoscie10s", allowSetters = true)
    private Categoriascie10 rel_categorias_diagnosticos_cie10;

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

    public Diagnosticoscie10 clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Diagnosticoscie10 descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public Diagnosticoscie10 idcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
        return this;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Categoriascie10 getRel_categorias_diagnosticos_cie10() {
        return rel_categorias_diagnosticos_cie10;
    }

    public Diagnosticoscie10 rel_categorias_diagnosticos_cie10(Categoriascie10 categoriascie10) {
        this.rel_categorias_diagnosticos_cie10 = categoriascie10;
        return this;
    }

    public void setRel_categorias_diagnosticos_cie10(Categoriascie10 categoriascie10) {
        this.rel_categorias_diagnosticos_cie10 = categoriascie10;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diagnosticoscie10)) {
            return false;
        }
        return id != null && id.equals(((Diagnosticoscie10) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diagnosticoscie10{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idcategoria=" + getIdcategoria() +
            "}";
    }
}
