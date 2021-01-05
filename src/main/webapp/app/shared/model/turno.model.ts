import { Moment } from 'moment';

export interface ITurno {
    id?: number;
    dni?: string;
    apellido?: string;
    nombre?: string;
    telefono?: string;
    email?: string;
    dia?: Moment;
    hora?: Moment;
    tur_esp_relNombreEspecialidad?: string;
    tur_prof_relNombreProfesional?: string;
    idHorario?: number;
    tur_esp_relId?: number;
    tur_prof_relId?: number;
    tur_obs_relNombreObraSocial?: string;
    tur_obs_relId?: number;
    tipoPaciente?: number;
    horariosOcupados?: String;
    usuarioCarga?: number;
    estado?: number;
}

export class Turno implements ITurno {
    constructor(
        public id?: number,
        public dni?: string,
        public apellido?: string,
        public nombre?: string,
        public telefono?: string,
        public email?: string,
        public dia?: Moment,
        public hora?: Moment,
        public tur_esp_relNombreEspecialidad?: string,
        public tur_prof_relNombreProfesional?: string,
        public idHorario?: number,
        public tur_esp_relId?: number,
        public tur_prof_relId?: number,
        public tur_obs_relNombreObraSocial?: string,
        public tur_obs_relId?: number,
        public tipoPaciente?: number,
        public horariosOcupados?: String,
        public usuarioCarga?: number,
        public estado?: number
    ) {}
}
