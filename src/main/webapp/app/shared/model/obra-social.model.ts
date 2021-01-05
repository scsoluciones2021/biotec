import { IProfesional } from 'app/shared/model//profesional.model';
import { IAgrupadorOS } from './agrupador-os.model';

export interface IObraSocial {
    id?: number;
    codigoObraSocial?: string;
    nombreObraSocial?: string;
    direccionObraSocial?: string;
    telefonoObraSocial?: string;
    emailObraSocial?: string;
    siglaObraSocial?: string;
    agrupadorId?: number;
    agrupadorNombre?: string;
}

export class ObraSocial implements IObraSocial {
    constructor(
        public id?: number,
        public codigoObraSocial?: string,
        public nombreObraSocial?: string,
        public direccionObraSocial?: string,
        public telefonoObraSocial?: string,
        public emailObraSocial?: string,
        public siglaObraSocial?: string,
        public agrupadorId?: number,
        public agrupadorNombre?: string,
    ) {}
}
