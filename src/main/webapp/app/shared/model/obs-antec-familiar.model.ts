import { Dayjs } from 'dayjs';

export interface IObsAntecFamiliar {
    id?: number;
    idEspecialidad?: number;
    idMedico?: number;
    observaciones?: string;
    fecha?: Dayjs;
    antecedentesFamiliaresId?: number;
}

export class ObsAntecFamiliar implements IObsAntecFamiliar {
    constructor(
        public id?: number,
        public idEspecialidad?: number,
        public idMedico?: number,
        public observaciones?: string,
        public fecha?: Dayjs,
        public antecedentesFamiliaresId?: number
    ) {}
}
