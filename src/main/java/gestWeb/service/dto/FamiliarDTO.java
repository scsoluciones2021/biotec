package gestWeb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Familiar entity.
 */
public class FamiliarDTO implements Serializable {

    private Long id;

    private String parentezco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FamiliarDTO familiarDTO = (FamiliarDTO) o;
        if (familiarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familiarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamiliarDTO{" +
            "id=" + getId() +
            ", parentezco='" + getParentezco() + "'" +
            "}";
    }
}
