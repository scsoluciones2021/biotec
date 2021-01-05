export interface ITituloShort {
    id?: number;
    valor?: string;
}

export class TituloShort implements ITituloShort {
    constructor(public id?: number, public valor?: string) {}
}
