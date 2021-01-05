export interface ICalendario {
    id?: number;
}

export class Calendario implements ICalendario {
    constructor(public id?: number) {}
}
