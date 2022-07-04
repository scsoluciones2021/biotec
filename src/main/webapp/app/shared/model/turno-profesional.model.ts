import { Dayjs } from 'dayjs';
import { IEspecialidad } from './especialidad.model';

export interface ITurnoProf {
    fecha?: Dayjs;
    especialidades?: IEspecialidad[];
    profesional?: number;
    estado?: number;
    retorno?: String;
}

export class TurnoProf implements ITurnoProf {
    constructor(
        public fecha?: Dayjs,
        public especialidades?: IEspecialidad[],
        public profesional?: number,
        public estado?: number,
        public retorno?: String
    ) {}
}
