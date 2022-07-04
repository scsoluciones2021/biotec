import { Dayjs } from 'dayjs';
import { IFicha } from 'app/shared/model//ficha.model';

export interface IConsulta {
    id?: number;
    fechaConsulta?: Dayjs;
    observacionesConsulta?: string;
    tratamientoConsulta?: string;
    indicacionesConsulta?: string;
    sintomasConsulta?: string;
    diagnosticoConsulta?: string;
    constantesConsultaId?: number;
    fichas?: IFicha[];
}

export class Consulta implements IConsulta {
    constructor(
        public id?: number,
        public fechaConsulta?: Dayjs,
        public observacionesConsulta?: string,
        public tratamientoConsulta?: string,
        public indicacionesConsulta?: string,
        public sintomasConsulta?: string,
        public diagnosticoConsulta?: string,
        public constantesConsultaId?: number,
        public fichas?: IFicha[]
    ) {}
}
