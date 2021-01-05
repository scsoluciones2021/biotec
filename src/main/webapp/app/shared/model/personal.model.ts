export interface IPersonal {
    id?: number;
    nombrePersonal?: string;
    apellidoPersonal?: string;
    documentoPersonal?: string;
    direccionPersonal?: string;
    telefonoPersonal?: string;
    emailPersonal?: string;
    usuarioId?: number;
    empresaNombreEmpresa?: string;
    empresaId?: number;
}

export class Personal implements IPersonal {
    constructor(
        public id?: number,
        public nombrePersonal?: string,
        public apellidoPersonal?: string,
        public documentoPersonal?: string,
        public direccionPersonal?: string,
        public telefonoPersonal?: string,
        public emailPersonal?: string,
        public usuarioId?: number,
        public empresaNombreEmpresa?: string,
        public empresaId?: number
    ) {}
}
