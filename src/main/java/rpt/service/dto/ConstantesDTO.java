package rpt.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Constantes entity.
 */
public class ConstantesDTO implements Serializable {

    private Long id;

    private Long peso;

    private Long altura;

    private Long presionArterial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Long getAltura() {
        return altura;
    }

    public void setAltura(Long altura) {
        this.altura = altura;
    }

    public Long getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(Long presionArterial) {
        this.presionArterial = presionArterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConstantesDTO constantesDTO = (ConstantesDTO) o;
        if (constantesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), constantesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConstantesDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", altura=" + getAltura() +
            ", presionArterial=" + getPresionArterial() +
            "}";
    }
}
