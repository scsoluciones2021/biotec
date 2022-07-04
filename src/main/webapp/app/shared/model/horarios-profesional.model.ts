import { Dayjs } from 'dayjs';
import { IBloqueos } from 'app/shared/model/bloqueos.model';

export interface IHorariosProfesional {
    horario_esp_relNombreEspecialidad?: string;
    horario_esp_relId?: number;
    id?: number;
    consultorio?: string;
    fechaDesde?: Dayjs;
    fechaHasta?: Dayjs;
    horario_prof_relNombreProfesional?: string;
    horario_prof_relId?: number;
    horario_bloq_rels?: IBloqueos[];
}

export class HorariosProfesional implements IHorariosProfesional {
    constructor(
        public id?: number,
        public consultorio?: string,
        public fechaDesde?: Dayjs,
        public fechaHasta?: Dayjs,
        public horario_prof_relNombreProfesional?: string,
        public horario_prof_relId?: number,
        public horario_bloq_rels?: IBloqueos[],
        public horario_esp_relNombreEspecialidad?: string,
        public horario_esp_relId?: number
    ) {}
}
