import { IAntecedentesFamiliares } from 'app/shared/model//antecedentes-familiares.model';

export interface IFamiliar {
    id?: number;
    parentezco?: string;
    antecedentesFamiliares?: IAntecedentesFamiliares[];
}

export class Familiar implements IFamiliar {
    constructor(public id?: number, public parentezco?: string, public antecedentesFamiliares?: IAntecedentesFamiliares[]) {}
}
