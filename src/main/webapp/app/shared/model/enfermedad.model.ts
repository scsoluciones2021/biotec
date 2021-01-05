export interface IEnfermedad {
    id?: number;
    valor?: string;
}

export class Enfermedad implements IEnfermedad {
    constructor(public id?: number, public valor?: string) {}
}
