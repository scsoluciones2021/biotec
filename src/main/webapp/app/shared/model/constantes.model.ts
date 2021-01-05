export interface IConstantes {
    id?: number;
    peso?: number;
    altura?: number;
    presionArterial?: number;
}

export class Constantes implements IConstantes {
    constructor(public id?: number, public peso?: number, public altura?: number, public presionArterial?: number) {}
}
