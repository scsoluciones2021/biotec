package rpt.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TituloShort entity.
 */
public class TituloShortDTO implements Serializable {

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

        TituloShortDTO tituloShortDTO = (TituloShortDTO) o;
        if (tituloShortDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tituloShortDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TituloShortDTO{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
