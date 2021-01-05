export interface IAlergia {
    id?: number;
    valor?: string;
}

export class Alergia implements IAlergia {
    constructor(public id?: number, public valor?: string) {}
}
