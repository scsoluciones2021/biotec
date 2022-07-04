import { Dayjs } from 'dayjs';

export interface IBloqueos {
    id?: number;
    nombreBloqueo?: string;
    fechaDesde?: Dayjs;
    fechaHasta?: Dayjs;
    horaDesde?: Dayjs;
    horaHasta?: Dayjs;
}

export class Bloqueos implements IBloqueos {
    constructor(
        public id?: number,
        public nombreBloqueo?: string,
        public fechaDesde?: Dayjs,
        public fechaHasta?: Dayjs,
        public horaDesde?: Dayjs,
        public horaHasta?: Dayjs
    ) {}
}
