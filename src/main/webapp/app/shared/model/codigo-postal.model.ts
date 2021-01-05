import { IEmpresa } from 'app/shared/model//empresa.model';
import { IPaciente } from 'app/shared/model//paciente.model';
import { IProfesional } from 'app/shared/model//profesional.model';

export interface ICodigoPostal {
    id?: number;
    codigo?: string;
    nombreCiudad?: string;
    empresas?: IEmpresa[];
    pacientes?: IPaciente[];
    profesionals?: IProfesional[];
    provinciaNombreProvincia?: string;
    provinciaId?: number;
}

export class CodigoPostal implements ICodigoPostal {
    constructor(
        public id?: number,
        public codigo?: string,
        public nombreCiudad?: string,
        public empresas?: IEmpresa[],
        public pacientes?: IPaciente[],
        public profesionals?: IProfesional[],
        public provinciaNombreProvincia?: string,
        public provinciaId?: number
    ) {}
}
