import { IProfesional } from 'app/shared/model//profesional.model';

export interface IEspecialidad {
    id?: number;
    codigoEspecilidad?: string;
    nombreEspecialidad?: string;
    profesionals?: IProfesional[];
}

export class Especialidad implements IEspecialidad {
    constructor(
        public id?: number,
        public codigoEspecilidad?: string,
        public nombreEspecialidad?: string,
        public profesionals?: IProfesional[]
    ) {}
}
