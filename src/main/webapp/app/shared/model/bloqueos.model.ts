import { Moment } from 'moment';

export interface IBloqueos {
    id?: number;
    nombreBloqueo?: string;
    fechaDesde?: Moment;
    fechaHasta?: Moment;
    horaDesde?: Moment;
    horaHasta?: Moment;
}

export class Bloqueos implements IBloqueos {
    constructor(
        public id?: number,
        public nombreBloqueo?: string,
        public fechaDesde?: Moment,
        public fechaHasta?: Moment,
        public horaDesde?: Moment,
        public horaHasta?: Moment
    ) {}
}
