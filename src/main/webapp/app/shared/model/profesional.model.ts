import { IFicha } from 'app/shared/model//ficha.model';
import { IObraSocial } from 'app/shared/model//obra-social.model';
import { IEspecialidad } from 'app/shared/model//especialidad.model';

export interface IProfesional {
    id?: number;
    nombreProfesional?: string;
    apellidoProfesional?: string;
    documentoProfesional?: string;
    direccionProfesional?: string;
    telefonoProfesional?: string;
    emailProfesional?: string;
    matriculaProfesional?: string;
    imagenProfesionalContentType?: string;
    imagenProfesional?: any;
    usuarioId?: number;
    tituloValor?: string;
    tituloId?: number;
    fichas?: IFicha[];
    obrasocials?: IObraSocial[];
    especialidads?: IEspecialidad[];
    codigoPostalNombreCiudad?: string;
    codigoPostalId?: number;
}

export class Profesional implements IProfesional {
    constructor(
        public id?: number,
        public nombreProfesional?: string,
        public apellidoProfesional?: string,
        public documentoProfesional?: string,
        public direccionProfesional?: string,
        public telefonoProfesional?: string,
        public emailProfesional?: string,
        public matriculaProfesional?: string,
        public imagenProfesionalContentType?: string,
        public imagenProfesional?: any,
        public usuarioId?: number,
        public tituloValor?: string,
        public tituloId?: number,
        public fichas?: IFicha[],
        public obrasocials?: IObraSocial[],
        public especialidads?: IEspecialidad[],
        public codigoPostalNombreCiudad?: string,
        public codigoPostalId?: number
    ) {}
}

export interface IProfesionalTurno {
    id?: number;
    nombreProfesional?: string;
    idsEspecialidades?: string;
    nombreEspecialidades?: string;
}

export class ProfesionalTurno implements IProfesional {
    constructor(
        public id?: number,
        public nombreProfesional?: string,
        public idsEspecialidades?: string,
        public nombreEspecialidades?: string
    ) {}
}
