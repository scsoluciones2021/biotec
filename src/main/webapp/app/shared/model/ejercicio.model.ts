import { IAntecedentesPersonales } from 'app/shared/model//antecedentes-personales.model';

export interface IEjercicio {
    id?: number;
    valor?: string;
    antecedentesPersonales?: IAntecedentesPersonales[];
}

export class Ejercicio implements IEjercicio {
    constructor(public id?: number, public valor?: string, public antecedentesPersonales?: IAntecedentesPersonales[]) {}
}
