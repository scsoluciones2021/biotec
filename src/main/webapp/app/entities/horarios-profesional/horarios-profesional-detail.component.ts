import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';
import { IProfesional } from 'app/shared/model/profesional.model';
import { Especialidad } from 'app/shared/model/especialidad.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosService } from '../detalle-horarios/detalle-horarios.service';
import { IEspecialidad } from '../../shared/model/especialidad.model';

interface Dias {
    nombreDia: string;
    numero: string;
}

@Component({
    selector: 'jhi-horarios-profesional-detail',
    templateUrl: './horarios-profesional-detail.component.html'
})
export class HorariosProfesionalDetailComponent implements OnInit {
    horariosProfesional: IHorariosProfesional;

    // Agregados

    fechaDesdeDp: any;
    fechaHastaDp: any;

    // Para los profesionales
    profesionals: IProfesional[];
    profesionalesPNG: any[];
    filteredProfesionalSingle: any[];
    profesional: any;

    // Para los días de la semana
    dias: Dias[];

    diasSeleccionados: Dias[];
    es: any;
    dateD: Date;
    dateH: Date;
    // fin para los días de la semana

    // Para los horarios
    horariosD: String[] = ['', '', '', '', '', '', ''];
    horariosH: String[] = ['', '', '', '', '', '', ''];
    intervalo: String[] = ['', '', '', '', '', '', ''];
    checkDias: Boolean[] = [false, false, false, false, false, false, false];
    especialidad: Especialidad;
    especialidades: any;
    detalles: any;

    // Fin Agregados

    constructor(private activatedRoute: ActivatedRoute, private detalleService: DetalleHorariosService) {
        // Arreglo de días
        this.dias = [
            { nombreDia: 'Lunes', numero: '1' },
            { nombreDia: 'Martes', numero: '2' },
            { nombreDia: 'Miércoles', numero: '3' },
            { nombreDia: 'Jueves', numero: '4' },
            { nombreDia: 'Viernes', numero: '5' },
            { nombreDia: 'Sábado', numero: '6' },
            { nombreDia: 'Domingo', numero: '0' }
        ];
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horariosProfesional }) => {
            this.horariosProfesional = horariosProfesional;
        });
        // Calendario en español
        this.es = {
            firstDayOfWeek: 1,
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
            dayNamesMin: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
            monthNames: [
                'Enero',
                'Febrero',
                'Marzo',
                'Abril',
                'Mayo',
                'Junio',
                'Julio',
                'Agosto',
                'Septiembre',
                'Octubre',
                'Noviembre',
                'Diciembre'
            ],
            monthNamesShort: ['En', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            today: 'Hoy',
            clear: 'Borrar',
            dateFormat: 'dd/MM/yy'
        };

        // Para setear la fecha
        //  const datePipe = new DatePipe('en-US');
        this.dateD = this.horariosProfesional.fechaDesde.toDate();
        if (this.horariosProfesional.fechaHasta != null) {
            this.dateH = this.horariosProfesional.fechaHasta.toDate();
        } else {
            this.dateH = null;
        }

        // Especialidad
        this.especialidad = new Especialidad();
        this.especialidad.id = this.horariosProfesional.horario_esp_relId;
        this.especialidad.nombreEspecialidad = this.horariosProfesional.horario_esp_relNombreEspecialidad;
        this.especialidades = new Array();
        this.especialidades.push(this.especialidad);

        this.detalleService.buscarXHorario({ idHorario: this.horariosProfesional.id }).subscribe(
            (res: HttpResponse<IDetalleHorarios[]>) => {
                this.detalles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    onError(message: string) {
        throw new Error('Method not implemented.');
    }

    previousState() {
        window.history.back();
    }

    trackEspecialidadById(index: number, item: IEspecialidad) {
        return item.id;
    }
}
