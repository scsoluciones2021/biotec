import { Moment } from 'moment';

export interface IObsAntecPersonal {
    id?: number;
    idEspecialidad?: number;
    idMedico?: number;
    observaciones?: string;
    fecha?: Moment;
    antecedentesPersonalesId?: number;
}

export class ObsAntecPersonal implements IObsAntecPersonal {
    constructor(
        public id?: number,
        public idEspecialidad?: number,
        public idMedico?: number,
        public observaciones?: string,
        public fecha?: Moment,
        public antecedentesPersonalesId?: number
    ) {}
}
