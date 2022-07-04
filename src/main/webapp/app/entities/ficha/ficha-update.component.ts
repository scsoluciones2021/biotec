import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFicha } from 'app/shared/model/ficha.model';
import { FichaService } from './ficha.service';
import { IPaciente } from 'app/shared/model/paciente.model';
import { PacienteService } from 'app/entities/paciente';
import { IProfesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from 'app/entities/profesional';
import { IConsulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from 'app/entities/consulta';
import { FormControl } from '@angular/forms';
import { of } from 'rxjs/observable/of';
import { tap, map, debounceTime, switchMap, finalize, distinctUntilChanged, catchError } from 'rxjs/operators';
import { AntecedentesFamiliaresUpdateComponent } from 'app/entities/antecedentes-familiares';
import { Paciente } from '../../shared/model/paciente.model';
import { Profesional } from '../../shared/model/profesional.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PacienteModalComponent } from '../paciente/paciente-modal.component';
import { IEspecialidad } from 'app/shared/model/especialidad.model';
import { EspecialidadService } from '../especialidad';
import { Especialidad } from '../../shared/model/especialidad.model';

@Component({
    selector: 'jhi-ficha-update',
    templateUrl: './ficha-update.component.html'
})
export class FichaUpdateComponent implements OnInit {
    private _ficha: IFicha;
    isSaving: boolean;

    pacientes: IPaciente[];

    profesionales: IProfesional[];

    especialidades: IEspecialidad[];

    consultas: IConsulta[];

    fechaIngresoDp: any;

    options: IPaciente[];

    // autocomplete primeng
    paciente: any;
    pacientesPNG: any[];
    filteredPacienteSingle: any[];

    /* profesional: any;
    especialidad: any;*/
    profesionalesPNG: any[];
    // filteredProfesionalSingle: any[];

    profesional: any;
    especialidad: any;
    especialidadesPNG: any[];
    filteredEspecialidadSingle: any[];

    // fin autocomplete

    // uploadfile
    uploadedFiles: any[] = [];

    private modalRef: NgbModalRef;

    constructor(
        private jhiAlertService: JhiAlertService,
        private fichaService: FichaService,
        private pacienteService: PacienteService,
        private profesionalService: ProfesionalService,
        private especialidadService: EspecialidadService,
        private consultaService: ConsultaService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private modalService: NgbModal
    ) {}

    // Modal para crear un nuevo paciente
    openFormModal() {
        this.modalRef = this.modalService.open(PacienteModalComponent as Component, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.id = this.ficha.id;
        this.modalRef.componentInstance.paciente = of(new Paciente());

        // Qué hace al volver
        this.modalRef.result
            .then(result => {
                this.paciente = new Paciente();
                this.paciente.id = result.idPac;
                this.paciente.nombrePaciente = result.pacienteNombre;
                this.ficha.pacienteId = result.idPac;
                this.ficha.pacienteNombrePaciente = result.pacienteNombre;
            })
            .catch(error => {
                console.log(error);
            });
    }

    // PRIMENG
    // Paciente
    filterPacienteSingle(event) {
        // Original funcionando
        const query = event.query;

        this.pacienteService.buscarPaciente(query).subscribe(
            (res: HttpResponse<IPaciente[]>) => {
                this.filteredPacienteSingle = this.filterPaciente(query, res.body);
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
        /*this.pacienteService.query({'nombrePaciente.contains': event.query}).subscribe(
            (res: HttpResponse<IPaciente[]>) => {
                this.filteredPacienteSingle = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/
    }

    filterPaciente(query, pacientesPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < pacientesPNG.length; i++) {
            const paciente = pacientesPNG[i];
            if (paciente.nombrePaciente.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(paciente);
            }
        }
        return filtered;
    }

    seleccionPaciente(event) {
        this.paciente = event;
        this.ficha.pacienteId = event.id;
        this.ficha.pacienteNombrePaciente = event.nombrePaciente;
    }

    borrarPaciente() {
        this.paciente = null;
        this.ficha.pacienteId = null;
        this.ficha.pacienteNombrePaciente = null;
    }

    // Profesional
    filterProfesionalSingle(idEsp) {
        // con el true traigo también las relaciones
        this.profesionalService.query('true').subscribe(
            (res: HttpResponse<IProfesional[]>) => {
                this.profesionales = this.filterProfesional(idEsp, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterProfesional(query, profesionalesPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < profesionalesPNG.length; i++) {
            const profesional = profesionalesPNG[i];
            profesional.especialidads.forEach(esp => {
                if (esp.id == query) {
                    filtered.push(profesional);
                }
            });
        }
        return filtered;
    }

    // Especialidad
    filterEspecialidadSingle(event) {
        const query = event.query;

        this.especialidadService.query().subscribe(
            (res: HttpResponse<IEspecialidad[]>) => {
                this.filteredEspecialidadSingle = this.filterEspecialidad(query, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterEspecialidad(query, especialidadesPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < especialidadesPNG.length; i++) {
            const especialidad = especialidadesPNG[i];
            if (especialidad.nombreEspecialidad.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(especialidad);
            }
        }
        return filtered;
    }

    seleccionEspecialidad(event) {
        this.especialidad = event;
        this.ficha.especialidadId = event.id;
        this.ficha.especialidadNombreEspecialidad = event.nombreEspecialidad;
        this.filterProfesionalSingle(event.id);
        //this.profesionales = event.profesionales;
    }

    borrarEspecialidad() {
        this.especialidad = null;
        this.ficha.especialidadId = null;
        this.ficha.especialidadNombreEspecialidad = null;
        this.ficha.profesionalId = null;
        this.ficha.profesionalNombreProfesional = null;
        this.profesionales = null;
    }
    // PRIMENG FIN

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ficha }) => {
            this.ficha = ficha;
        });

        this.consultaService.query().subscribe(
            (res: HttpResponse<IConsulta[]>) => {
                this.consultas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        if (this.ficha.pacienteId) {
            this.paciente = new Paciente();
            this.paciente.id = this.ficha.pacienteId;
            this.paciente.nombrePaciente = this.ficha.pacienteNombrePaciente;
        }

        if (this.ficha.especialidadId) {
            this.especialidad = new Especialidad();
            this.especialidad.id = this.ficha.especialidadId;
            this.especialidad.nombreEspecialidad = this.ficha.especialidadNombreEspecialidad;
        }

        if (this.ficha.profesionalId) {
            this.profesional = new Profesional();
            this.profesional.id = this.ficha.profesionalId;
            this.profesional.nombreProfesional = this.ficha.profesionalNombreProfesional;
            this.profesionales = new Array();
            this.profesionales.push(this.profesional);
        }
    }

    previousState() {
        this.ficha = null;
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ficha.id !== undefined) {
            this.subscribeToSaveResponse(this.fichaService.update(this.ficha));
        } else {
            this.subscribeToSaveResponse(this.fichaService.create(this.ficha));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFicha>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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

    trackPacienteById(item: IPaciente) {
        return item.id;
    }

    trackProfesionalById(item: IProfesional) {
        return item.id;
    }

    trackEspecialidadById(item: IEspecialidad) {
        return item.id;
    }

    trackConsultaById(item: IConsulta) {
        return item.id;
    }
    get ficha() {
        return this._ficha;
    }

    set ficha(ficha: IFicha) {
        this._ficha = ficha;
    }
}
