import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IConsulta, Consulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from './consulta.service';
import { IConstantes } from 'app/shared/model/constantes.model';
import { ConstantesService } from 'app/entities/constantes';
import * as dayjs from 'dayjs';
import { PacienteService } from '../paciente/paciente.service';
import { IPaciente } from 'app/shared/model/paciente.model';
import { TurnoService } from '../turno';

@Component({
    selector: 'jhi-consulta-update',
    templateUrl: './consulta-update.component.html'
})
export class ConsultaUpdateComponent implements OnInit {
    // es el dni que viene desde el "atender" del turno
    private nroPaciente: number;
    paciente: IPaciente;

    private _consulta: IConsulta;
    isSaving: boolean;

    private fechaBusqueda: Date;
    private idTurno: number;
    private idProfesional: number;
    private idEspecialidad: number;

    constantesconsultas: IConstantes[];
    fechaConsultaDp: any;

    fileUploads: Observable<string[]>;
    showFile = false;
    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = { percentage: 0 };

    constructor(
        private jhiAlertService: JhiAlertService,
        private consultaService: ConsultaService,
        private constantesService: ConstantesService,
        private pacienteService: PacienteService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private turnoService: TurnoService
    ) {
        this.activatedRoute.queryParams.subscribe(params => {
            this.nroPaciente = params['nroPaciente'];
        });
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.queryParams.subscribe(params => {
            this.nroPaciente = params['nroPaciente'];
            this.fechaBusqueda = params['fechaBusqueda'];
            this.idTurno = params['idTurno'];
            this.idEspecialidad = params['idEspecialidad'];
            this.idProfesional = params['idProfesional'];

            this.activatedRoute.data.subscribe(({ consulta }) => {
                this.consulta = consulta;
                if (this.consulta.fechaConsulta === undefined) {
                    this.consulta.fechaConsulta = dayjs();
                }
            });
        });

        this.pacienteService
            .buscarPacienteXDNI(this.nroPaciente)
            .toPromise()
            .then(
                (pac: HttpResponse<IPaciente>) => {
                    this.paciente = pac.body;
                },
                (pac: HttpErrorResponse) => this.onError(pac.message)
            );
    }

    previousState() {
        // window.history.back();
        this.router.navigate(['/turno-profesional']);
    }

    save() {
        this.isSaving = true;
        // controlar si tiene ficha --> si tiene directamente guardar relación consulta-ficha
        // si no tiene, crear ficha y luego crear la relación
        if (this.consulta.id !== undefined) {
            this.subscribeToSaveResponse(this.consultaService.update(this.consulta), 2);
        } else {
            this.subscribeToSaveResponse(this.consultaService.create(this.consulta), 1);
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConsulta>>, tipo: number) {
        result.subscribe(
            (res: HttpResponse<IConsulta>) => {
                // tipo = 1 consulta nueva
                // tipo = 2 actualización
                console.log(tipo);
                console.log('hola');
                console.log(res.body);
                console.log(this.paciente);
                console.log(this.idProfesional);
                console.log(this.idEspecialidad);
                if (tipo == 1) {
                    this.consultaService
                        .setFichaConsulta(res.body.id, this.paciente.id, this.idProfesional, this.idTurno, this.idEspecialidad)
                        .subscribe(
                            response => {
                                console.log('llego al ok');
                            },
                            (res: HttpErrorResponse) => this.onSaveError()
                        );
                }
                this.onSaveSuccess();
                console.log('chau');
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        //this.previousState();

        this.turnoService.cambiarEstado(this.idTurno, 4).subscribe(response => {
            let navigationExtras: NavigationExtras = {
                queryParams: {
                    fechaBusqueda: this.fechaBusqueda
                },
                skipLocationChange: true
            };
            this.router.navigate([this.activatedRoute.snapshot.paramMap.get('previousUrl')], navigationExtras);
        });
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackConstantesById(index: number, item: IConstantes) {
        return item.id;
    }
    get consulta() {
        return this._consulta;
    }

    set consulta(consulta: IConsulta) {
        this._consulta = consulta;
    }

    // Para carga de archivos
    onFileChanged(event) {
        this.selectedFiles = event.target.files;
    }

    onUpload() {
        this.progress.percentage = 0;

        this.currentFileUpload = this.selectedFiles.item(0);

        this.consultaService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
            if (event.type === HttpEventType.UploadProgress) {
                this.progress.percentage = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
                this.delay(3000).then(any => {
                    this.currentFileUpload = null;
                    this.progress.percentage = 0;
                });
            }
        });

        this.selectedFiles = undefined;
    }

    showFiles(enable: boolean) {
        this.showFile = enable;

        if (enable) {
            this.fileUploads = this.consultaService.getFiles();
            console.log('archivos: ' + this.fileUploads);
        }
    }

    // Función para que el progressbar se muestre un ratito...
    async delay(ms: number) {
        await new Promise<void>(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }
}
