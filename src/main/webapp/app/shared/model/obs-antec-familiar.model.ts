import { Moment } from 'moment';

export interface IObsAntecFamiliar {
    id?: number;
    idEspecialidad?: number;
    idMedico?: number;
    observaciones?: string;
    fecha?: Moment;
    antecedentesFamiliaresId?: number;
}

export class ObsAntecFamiliar implements IObsAntecFamiliar {
    constructor(
        public id?: number,
        public idEspecialidad?: number,
        public idMedico?: number,
        public observaciones?: string,
        public fecha?: Moment,
        public antecedentesFamiliaresId?: number
    ) {}
}
