import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';

export interface ISubgruposcie10 {
    id?: number;
    clave?: string;
    descripcion?: string;
    idGrupo?: number;
    rel_grupo_subgrupo_cie10?: IGruposcie10;
}

export class Subgruposcie10 implements ISubgruposcie10 {
    constructor(
        public id?: number,
        public clave?: string,
        public descripcion?: string,
        public idGrupo?: number,
        public rel_grupo_subgrupo_cie10?: IGruposcie10
    ) {}
}
