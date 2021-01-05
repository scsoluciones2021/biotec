import { IObsAntecFamiliar } from 'app/shared/model//obs-antec-familiar.model';

export interface IAntecedentesFamiliares {
    id?: number;
    vivoAF?: boolean;
    causaMuerteAF?: string;
    obsantecFamiliars?: IObsAntecFamiliar[];
    parentezcoParentezco?: string;
    parentezcoId?: number;
}

export class AntecedentesFamiliares implements IAntecedentesFamiliares {
    constructor(
        public id?: number,
        public vivoAF?: boolean,
        public causaMuerteAF?: string,
        public obsantecFamiliars?: IObsAntecFamiliar[],
        public parentezcoParentezco?: string,
        public parentezcoId?: number
    ) {
        this.vivoAF = false;
    }
}
