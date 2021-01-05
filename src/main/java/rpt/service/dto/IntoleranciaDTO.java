package rpt.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Intolerancia entity.
 */
public class IntoleranciaDTO implements Serializable {

    private Long id;

    @NotNull
    private String valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntoleranciaDTO intoleranciaDTO = (IntoleranciaDTO) o;
        if (intoleranciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intoleranciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntoleranciaDTO{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
