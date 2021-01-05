import { Moment } from 'moment';
import { IBloqueos } from 'app/shared/model/bloqueos.model';

export interface IHorariosProfesional {
    horario_esp_relNombreEspecialidad?: string;
    horario_esp_relId?: number;
    id?: number;
    consultorio?: string;
  //  dias?: string;
    fechaDesde?: Moment;
    fechaHasta?: Moment;
   /* horaDesde?: String;
    horaHasta?: String;
    intervalo?: String;*/
    horario_prof_relNombreProfesional?: string;
    horario_prof_relId?: number;
    horario_bloq_rels?: IBloqueos[];
}

export class HorariosProfesional implements IHorariosProfesional {    
    constructor(
        public id?: number,
        public consultorio?: string,
      //  public dias?: string,
        public fechaDesde?: Moment,
        public fechaHasta?: Moment,
    /*    public horaDesde?: String,
        public horaHasta?: String,
        public intervalo?: String,*/
        public horario_prof_relNombreProfesional?: string,
        public horario_prof_relId?: number,
        public horario_bloq_rels?: IBloqueos[],
        public horario_esp_relNombreEspecialidad?: string,
        public horario_esp_relId?: number,
    ) {}
}
