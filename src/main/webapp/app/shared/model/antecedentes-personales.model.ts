import { IObsAntecPersonal } from 'app/shared/model//obs-antec-personal.model';
import { IEnfermedad } from 'app/shared/model//enfermedad.model';
import { IAlergia } from 'app/shared/model//alergia.model';
import { IIntolerancia } from 'app/shared/model//intolerancia.model';
import { IRegimen } from 'app/shared/model//regimen.model';

export interface IAntecedentesPersonales {
    id?: number;
    tabaco?: boolean;
    tabacoObserv?: string;
    tecafe?: boolean;
    obsantecPersonals?: IObsAntecPersonal[];
    enfermedades?: IEnfermedad[];
    alergias?: IAlergia[];
    intolerancias?: IIntolerancia[];
    regimenes?: IRegimen[];
    ejerciciosValor?: string;
    ejerciciosId?: number;
    bebidasValor?: string;
    bebidasId?: number;
}

export class AntecedentesPersonales implements IAntecedentesPersonales {
    constructor(
        public id?: number,
        public tabaco?: boolean,
        public tabacoObserv?: string,
        public tecafe?: boolean,
        public obsantecPersonals?: IObsAntecPersonal[],
        public enfermedades?: IEnfermedad[],
        public alergias?: IAlergia[],
        public intolerancias?: IIntolerancia[],
        public regimenes?: IRegimen[],
        public ejerciciosValor?: string,
        public ejerciciosId?: number,
        public bebidasValor?: string,
        public bebidasId?: number
    ) {
        this.tabaco = false;
        this.tecafe = false;
    }
}
