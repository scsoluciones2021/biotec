package gestWeb.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AntecedentesPersonales entity.
 */
public class AntecedentesPersonalesDTO implements Serializable {

    private Long id;

    private Boolean tabaco;

    private String tabacoObserv;

    private Boolean tecafe;

    private Set<EnfermedadDTO> enfermedades = new HashSet<>();

    private Set<AlergiaDTO> alergias = new HashSet<>();

    private Set<IntoleranciaDTO> intolerancias = new HashSet<>();

    private Set<RegimenDTO> regimenes = new HashSet<>();

    private Long ejerciciosId;

    private String ejerciciosValor;

    private Long bebidasId;

    private String bebidasValor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTabaco() {
        return tabaco;
    }

    public void setTabaco(Boolean tabaco) {
        this.tabaco = tabaco;
    }

    public String getTabacoObserv() {
        return tabacoObserv;
    }

    public void setTabacoObserv(String tabacoObserv) {
        this.tabacoObserv = tabacoObserv;
    }

    public Boolean isTecafe() {
        return tecafe;
    }

    public void setTecafe(Boolean tecafe) {
        this.tecafe = tecafe;
    }

    public Set<EnfermedadDTO> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Set<EnfermedadDTO> enfermedads) {
        this.enfermedades = enfermedads;
    }

    public Set<AlergiaDTO> getAlergias() {
        return alergias;
    }

    public void setAlergias(Set<AlergiaDTO> alergias) {
        this.alergias = alergias;
    }

    public Set<IntoleranciaDTO> getIntolerancias() {
        return intolerancias;
    }

    public void setIntolerancias(Set<IntoleranciaDTO> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public Set<RegimenDTO> getRegimenes() {
        return regimenes;
    }

    public void setRegimenes(Set<RegimenDTO> regimen) {
        this.regimenes = regimen;
    }

    public Long getEjerciciosId() {
        return ejerciciosId;
    }

    public void setEjerciciosId(Long ejercicioId) {
        this.ejerciciosId = ejercicioId;
    }

    public String getEjerciciosValor() {
        return ejerciciosValor;
    }

    public void setEjerciciosValor(String ejercicioValor) {
        this.ejerciciosValor = ejercicioValor;
    }

    public Long getBebidasId() {
        return bebidasId;
    }

    public void setBebidasId(Long bebidaId) {
        this.bebidasId = bebidaId;
    }

    public String getBebidasValor() {
        return bebidasValor;
    }

    public void setBebidasValor(String bebidaValor) {
        this.bebidasValor = bebidaValor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AntecedentesPersonalesDTO antecedentesPersonalesDTO = (AntecedentesPersonalesDTO) o;
        if (antecedentesPersonalesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), antecedentesPersonalesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AntecedentesPersonalesDTO{" +
            "id=" + getId() +
            ", tabaco='" + isTabaco() + "'" +
            ", tabacoObserv='" + getTabacoObserv() + "'" +
            ", tecafe='" + isTecafe() + "'" +
            ", ejercicios=" + getEjerciciosId() +
            ", ejercicios='" + getEjerciciosValor() + "'" +
            ", bebidas=" + getBebidasId() +
            ", bebidas='" + getBebidasValor() + "'" +
            "}";
    }
}
