export interface IRegimen {
    id?: number;
    valor?: string;
}

export class Regimen implements IRegimen {
    constructor(public id?: number, public valor?: string) {}
}
