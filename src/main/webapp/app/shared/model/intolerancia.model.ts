export interface IIntolerancia {
    id?: number;
    valor?: string;
}

export class Intolerancia implements IIntolerancia {
    constructor(public id?: number, public valor?: string) {}
}
