package rpt.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Adjuntos_ficha entity. This class is used in Adjuntos_fichaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /adjuntos-fichas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Adjuntos_fichaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter idFicha;

    private LongFilter idProfesional;

    private LongFilter idPaciente;

    private LongFilter idEspecialidad;

    private StringFilter ruta;

    private StringFilter nombreOriginal;

    private StringFilter nombreActual;

    private ZonedDateTimeFilter fecha;

    private IntegerFilter usuario;

    public Adjuntos_fichaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(LongFilter idFicha) {
        this.idFicha = idFicha;
    }

    public LongFilter getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(LongFilter idProfesional) {
        this.idProfesional = idProfesional;
    }

    public LongFilter getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(LongFilter idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LongFilter getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(LongFilter idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public StringFilter getRuta() {
        return ruta;
    }

    public void setRuta(StringFilter ruta) {
        this.ruta = ruta;
    }

    public StringFilter getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(StringFilter nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public StringFilter getNombreActual() {
        return nombreActual;
    }

    public void setNombreActual(StringFilter nombreActual) {
        this.nombreActual = nombreActual;
    }

    public ZonedDateTimeFilter getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTimeFilter fecha) {
        this.fecha = fecha;
    }

    public IntegerFilter getUsuario() {
        return usuario;
    }

    public void setUsuario(IntegerFilter usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Adjuntos_fichaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idFicha != null ? "idFicha=" + idFicha + ", " : "") +
                (idProfesional != null ? "idProfesional=" + idProfesional + ", " : "") +
                (idPaciente != null ? "idPaciente=" + idPaciente + ", " : "") +
                (idEspecialidad != null ? "idEspecialidad=" + idEspecialidad + ", " : "") +
                (ruta != null ? "ruta=" + ruta + ", " : "") +
                (nombreOriginal != null ? "nombreOriginal=" + nombreOriginal + ", " : "") +
                (nombreActual != null ? "nombreActual=" + nombreActual + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (usuario != null ? "usuario=" + usuario + ", " : "") +
            "}";
    }

}
