import { Moment } from 'moment';
import { IEspecialidad } from './especialidad.model';

export interface ITurnoProf {
    fecha?: Moment;
    especialidades?: IEspecialidad[];
    profesional?: number;
    estado?: number;
    retorno?: String;
}

export class TurnoProf implements ITurnoProf {
    constructor(
        public fecha?: Moment,
        public especialidades?: IEspecialidad[],
        public profesional?: number,
        public estado?: number,
        public retorno?: String
    ) {}
}
