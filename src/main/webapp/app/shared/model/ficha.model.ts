import { Moment } from 'moment';

export interface IFicha {
    id?: number;
    fechaIngreso?: Moment;
    pacienteNombrePaciente?: string;
    pacienteId?: number;
    profesionalNombreProfesional?: string;
    profesionalId?: number;
    especialidadId?: number;
    especialidadNombreEspecialidad?: string;
}

export class Ficha implements IFicha {
    constructor(
        public id?: number,
        public fechaIngreso?: Moment,
        public pacienteNombrePaciente?: string,
        public pacienteId?: number,
        public profesionalNombreProfesional?: string,
        public profesionalId?: number,
        public especialidadId?: number,
        public especialidadNombreEspecialidad?: string
    ) {}
}
