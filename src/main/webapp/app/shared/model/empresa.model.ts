import { IPersonal } from 'app/shared/model//personal.model';

export interface IEmpresa {
    id?: number;
    nombreEmpresa?: string;
    direccionEmpresa?: string;
    telefonoEmpresa?: string;
    emailEmpresa?: string;
    nroSucursal?: number;
    personals?: IPersonal[];
    codigoPostalNombreCiudad?: string;
    codigoPostalId?: number;
}

export class Empresa implements IEmpresa {
    constructor(
        public id?: number,
        public nombreEmpresa?: string,
        public direccionEmpresa?: string,
        public telefonoEmpresa?: string,
        public emailEmpresa?: string,
        public nroSucursal?: number,
        public personals?: IPersonal[],
        public codigoPostalNombreCiudad?: string,
        public codigoPostalId?: number
    ) {}
}
