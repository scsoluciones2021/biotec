import { ICodigoPostal } from 'app/shared/model//codigo-postal.model';

export interface IProvincia {
    id?: number;
    nombreProvincia?: string;
    codigopostals?: ICodigoPostal[];
}

export class Provincia implements IProvincia {
    constructor(public id?: number, public nombreProvincia?: string, public codigopostals?: ICodigoPostal[]) {}
}
