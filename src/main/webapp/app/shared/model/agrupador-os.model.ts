import { IObraSocial } from 'app/shared/model//obra-social.model';

export interface IAgrupadorOS {
    id?: number;
    nombre?: string;
    descripcion?: string;
    agrupador_obrasocials?: IObraSocial[];
}

export class AgrupadorOS implements IAgrupadorOS {
    constructor(public id?: number, public nombre?: string, public descripcion?: string, public agrupador_obrasocials?: IObraSocial[]) {}
}
