export interface IPagoConsulta {
    id?: number;
    monto?: number;
    tipo?: string;
    cupones?: number;
    pagoturnoId?: number;
}

export class PagoConsulta implements IPagoConsulta {
    constructor(public id?: number, public monto?: number, public tipo?: string, public cupones?: number, public pagoturnoId?: number) {}
}
