export interface IDiagnostico {
    id?: number;
    codigoDiagnostico?: string;
    descripcionDiagnostico?: string;
}

export class Diagnostico implements IDiagnostico {
    constructor(public id?: number, public codigoDiagnostico?: string, public descripcionDiagnostico?: string) {}
}
