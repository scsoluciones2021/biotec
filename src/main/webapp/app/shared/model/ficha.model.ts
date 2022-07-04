import { Dayjs } from 'dayjs';

export interface IFicha {
    id?: number;
    fechaIngreso?: Dayjs;
    pacienteNombrePaciente?: string;
    pacienteApellidoPaciente?: string;
    pacienteId?: number;
    profesionalNombreProfesional?: string;
    profesionalId?: number;
    especialidadId?: number;
    especialidadNombreEspecialidad?: string;
    consultaId?: number;
}

export class Ficha implements IFicha {
    constructor(
        public id?: number,
        public fechaIngreso?: Dayjs,
        public pacienteNombrePaciente?: string,
        public pacienteApellidoPaciente?: string,
        public pacienteId?: number,
        public profesionalNombreProfesional?: string,
        public profesionalId?: number,
        public especialidadId?: number,
        public especialidadNombreEspecialidad?: string,
        public consultaId?: number
    ) {}
}
