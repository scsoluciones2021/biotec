import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';

export interface ICategoriascie10 {
    id?: number;
    clave?: string;
    descripcion?: string;
    idsubgrupo?: number;
    rel_subrupos_categorias_cie10?: ISubgruposcie10;
}

export class Categoriascie10 implements ICategoriascie10 {
    constructor(
        public id?: number,
        public clave?: string,
        public descripcion?: string,
        public idsubgrupo?: number,
        public rel_subrupos_categorias_cie10?: ISubgruposcie10
    ) {}
}
