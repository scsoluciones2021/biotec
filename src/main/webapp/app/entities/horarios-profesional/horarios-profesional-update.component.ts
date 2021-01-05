import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';
import { HorariosProfesionalService } from './horarios-profesional.service';
import { IProfesional, Profesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from 'app/entities/profesional';
import { IBloqueos } from 'app/shared/model/bloqueos.model';
import { BloqueosService } from 'app/entities/bloqueos';

import { Especialidad, IEspecialidad } from 'app/shared/model/especialidad.model';
import { IDetalleHorarios, DetalleHorarios } from '../../shared/model/detalle-horarios.model';
import { DetalleHorariosService } from '../detalle-horarios/detalle-horarios.service';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetalleHorariosModalComponent } from '../detalle-horarios/detalle-horarios-modal.component';

interface Dias {
    nombreDia: string;
    numero: string;
}

// Fin selección días de la semana

@Component({
    selector: 'jhi-horarios-profesional-update',
    templateUrl: './horarios-profesional-update.component.html'
})
export class HorariosProfesionalUpdateComponent implements OnInit {
    private _horariosProfesional: IHorariosProfesional;
    isSaving: boolean;

    bloqueos: IBloqueos[];
    detalles: IDetalleHorarios[];
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

    private modalRef: NgbModalRef;

    constructor(
        private jhiAlertService: JhiAlertService,
        private horariosProfesionalService: HorariosProfesionalService,
        private profesionalService: ProfesionalService,
        private bloqueoService: BloqueosService,
        private detalleService: DetalleHorariosService,
        private modalService: NgbModal,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
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
        // Fin calendario en español
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ horariosProfesional }) => {
            this.horariosProfesional = horariosProfesional;
        });
        if (this.horariosProfesional.horario_prof_relId) {
            this.profesional = new Profesional();
            this.profesional.id = this.horariosProfesional.horario_prof_relId;
            this.profesional.nombreProfesional = this.horariosProfesional.horario_prof_relNombreProfesional;
        }
        /*this.bloqueoService.query().subscribe(
            (res: HttpResponse<IBloqueos[]>) => {
                this.bloqueos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/

        if (this._horariosProfesional.id) {
            // Para setear la fecha
            //  const datePipe = new DatePipe('en-US');
            this.dateD = this._horariosProfesional.fechaDesde.toDate();
            if (this._horariosProfesional.fechaHasta !== null) {
                this.dateH = this._horariosProfesional.fechaHasta.toDate();
            } else {
                this.dateH = null;
            }

            // Especialidad
            if (this.horariosProfesional.horario_esp_relId) {
                this.especialidad = new Especialidad();
                this.especialidad.id = this.horariosProfesional.horario_esp_relId;
                this.especialidad.nombreEspecialidad = this.horariosProfesional.horario_esp_relNombreEspecialidad;
                this.especialidades = new Array();
                this.especialidades.push(this.especialidad);
            }
            // console.log('ID profesional:' + this.horariosProfesional.id);
            this.detalleService.buscarXHorario({ idHorario: this.horariosProfesional.id }).subscribe(
                (res: HttpResponse<IDetalleHorarios[]>) => {
                    console.log('res: ' + res.body);
                    this.detalles = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        } // fin editar
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        // Fechas
        const moment = require('moment');
        this.horariosProfesional.fechaDesde = moment(this.dateD);
        if (this.dateH != null) {
            this.horariosProfesional.fechaHasta = moment(this.dateH);
        } else {
            this.horariosProfesional.fechaHasta = null;
        }

        // this.horariosProfesional.especialidadId = this.especialidad.id;
        if (this.horariosProfesional.id !== undefined) {
            this.subscribeToSaveResponseCreate(this.horariosProfesionalService.update(this.horariosProfesional));
        } else {
            this.horariosProfesional.horario_esp_relNombreEspecialidad = this.profesional.especialidads.find(
                x => x.id === this.horariosProfesional.horario_esp_relId
            ).nombreEspecialidad;
            this.subscribeToSaveResponse(this.horariosProfesionalService.create(this.horariosProfesional));
        }
    }

    private subscribeToSaveResponseCreate(result: Observable<HttpResponse<IHorariosProfesional>>) {
        result.subscribe(() => this.onSaveSuccessCreate(), () => this.onSaveError());
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHorariosProfesional>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    private onSaveSuccessCreate() {
        this.isSaving = false;
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProfesionalById(item: IProfesional) {
        return item.id;
    }

    trackBloqueoById(item: IBloqueos) {
        return item.id;
    }

    trackEspecialidadById(index: number, item: IEspecialidad) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get horariosProfesional() {
        return this._horariosProfesional;
    }

    set horariosProfesional(horariosProfesional: IHorariosProfesional) {
        this._horariosProfesional = horariosProfesional;
    }

    // Métodos agregados
    setearCheckDia(dia, event) {
        if (event) {
            this.checkDias[dia] = event;
        }
    }

    // Profesional
    filterProfesionalSingle(event) {
        const query = event.query;

        // this.profesionalService.query(true).subscribe(
        this.profesionalService.todos().subscribe(
            (res: HttpResponse<IProfesional[]>) => {
                // console.log("profesional: " , res.body);
                this.filteredProfesionalSingle = this.filterProfesional(query, res.body);
            },
            (res: HttpErrorResponse) => {
                // console.log('paso por aca error');
                this.onError(res.message);
            }
        );
    }

    filterProfesional(query, profesionalesPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < profesionalesPNG.length; i++) {
            const profesional = profesionalesPNG[i];
            if (query === '') {
                filtered.push(profesional);
            } else if (profesional.nombreProfesional.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(profesional);
            }
        }
        return filtered;
    }

    handleDropdown() {
        this.profesionalService.todos().subscribe(
            (res: HttpResponse<IProfesional[]>) => {
                this.profesionals = this.filterProfesional('', res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    seleccionProfesional(event) {
        this.profesional = event;
        this.horariosProfesional.horario_prof_relId = event.id;
        this.horariosProfesional.horario_prof_relNombreProfesional = event.nombreProfesional;
        this.especialidades = this.profesional.especialidads;
    }

    borrarProfesional() {
        this.profesional = null;
        this.horariosProfesional.horario_prof_relId = null;
        this.horariosProfesional.horario_prof_relNombreProfesional = null;
    }

    borrarDetalle(detalle: IDetalleHorarios): void {
        this.detalleService.delete(detalle.id).subscribe(data => {
            this.detalles = this.detalles.filter(u => u !== detalle);
        });
    }

    modificarDetalle(detalle: IDetalleHorarios): void {
        this.modalRef = this.modalService.open(DetalleHorariosModalComponent as Component, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.idH = this.horariosProfesional.id;
        this.modalRef.componentInstance.idDetalleHorarios = detalle.id;
        this.modalRef.componentInstance.detalleHorarios = of(new DetalleHorarios());

        // Qué hace al volver
        this.modalRef.result
            .then(result => {
                this.detalleService.buscarXHorario({ idHorario: result.idDet }).subscribe(
                    (res: HttpResponse<IDetalleHorarios[]>) => {
                        this.detalles = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            })
            .catch(error => {
                console.log(error);
            });
    }

    agregarDetalle(): void {
        // this.router.navigate(['detalle-horarios/new']);
        this.modalRef = this.modalService.open(DetalleHorariosModalComponent as Component, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.idH = this.horariosProfesional.id;
        this.modalRef.componentInstance.detalleHorarios = of(new DetalleHorarios());

        // Qué hace al volver
        this.modalRef.result
            .then(result => {
                this.detalleService.buscarXHorario({ idHorario: this.horariosProfesional.id }).subscribe(
                    (res: HttpResponse<IDetalleHorarios[]>) => {
                        console.log('detalles antes: ', this.detalles);
                        this.detalles = res.body;
                        console.log('detalles despues: ', this.detalles);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            })
            .catch(error => {
                console.log(error);
            });
    }
}
