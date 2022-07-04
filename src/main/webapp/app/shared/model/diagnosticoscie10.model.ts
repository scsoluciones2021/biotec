import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';

export interface IDiagnosticoscie10 {
    id?: number;
    clave?: string;
    descripcion?: string;
    idcategoria?: number;
    rel_categorias_diagnosticos_cie10?: ICategoriascie10;
}

export class Diagnosticoscie10 implements IDiagnosticoscie10 {
    constructor(
        public id?: number,
        public clave?: string,
        public descripcion?: string,
        public idcategoria?: number,
        public rel_categorias_diagnosticos_cie10?: ICategoriascie10
    ) {}
}
