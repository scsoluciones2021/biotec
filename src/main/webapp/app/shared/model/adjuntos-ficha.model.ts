import { Moment } from 'moment';

export interface IAdjuntosFicha {
    id?: number;
    idFicha?: number;
    idProfesional?: number;
    idPaciente?: number;
    idEspecialidad?: number;
    ruta?: string;
    nombreOriginal?: string;
    nombreActual?: string;
    fecha?: Moment;
    usuario?: number;
}

export class AdjuntosFicha implements IAdjuntosFicha {
    constructor(
        public id?: number,
        public idFicha?: number,
        public idProfesional?: number,
        public idPaciente?: number,
        public idEspecialidad?: number,
        public ruta?: string,
        public nombreOriginal?: string,
        public nombreActual?: string,
        public fecha?: Moment,
        public usuario?: number
    ) {}
}
