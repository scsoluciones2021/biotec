import { Dayjs } from 'dayjs';

export interface IObsAntecPersonal {
    id?: number;
    idEspecialidad?: number;
    idMedico?: number;
    observaciones?: string;
    fecha?: Dayjs;
    antecedentesPersonalesId?: number;
}

export class ObsAntecPersonal implements IObsAntecPersonal {
    constructor(
        public id?: number,
        public idEspecialidad?: number,
        public idMedico?: number,
        public observaciones?: string,
        public fecha?: Dayjs,
        public antecedentesPersonalesId?: number
    ) {}
}
