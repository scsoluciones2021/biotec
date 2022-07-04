import { IFicha } from 'app/shared/model//ficha.model';
import { IObraSocial } from './obra-social.model';
import { Dayjs } from 'dayjs';

export interface IPaciente {
    id?: number;
    nombrePaciente?: string;
    apellidoPaciente?: string;
    documentoPaciente?: string;
    direccionPaciente?: string;
    telefonoPaciente?: string;
    emailPaciente?: string;
    obrasocials?: IObraSocial[];
    usuarioId?: number;
    fichas?: IFicha[];
    codigoPostalNombreCiudad?: string;
    codigoPostalId?: number;
    provinciaNombreProvincia?: string;
    provinciaId?: number;
    fechaNacimiento?: Dayjs;
    edad?: number;
}

export class Paciente implements IPaciente {
    constructor(
        public id?: number,
        public nombrePaciente?: string,
        public apellidoPaciente?: string,
        public documentoPaciente?: string,
        public direccionPaciente?: string,
        public telefonoPaciente?: string,
        public emailPaciente?: string,
        public obrasocials?: IObraSocial[],
        public usuarioId?: number,
        public fichas?: IFicha[],
        public codigoPostalNombreCiudad?: string,
        public codigoPostalId?: number,
        public provinciaNombreProvincia?: string,
        public provinciaId?: number,
        public fechaNacimiento?: Dayjs,
        public edad?: number
    ) {}
}
