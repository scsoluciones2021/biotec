export interface IGruposcie10 {
    id?: number;
    clave?: string;
    descripcion?: string;
}

export class Gruposcie10 implements IGruposcie10 {
    constructor(public id?: number, public clave?: string, public descripcion?: string) {}
}
