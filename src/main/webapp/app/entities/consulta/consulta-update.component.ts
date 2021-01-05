import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IConsulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from './consulta.service';
import { IConstantes } from 'app/shared/model/constantes.model';
import { ConstantesService } from 'app/entities/constantes';
import * as moment from 'moment';
import { PacienteService } from '../paciente/paciente.service';
import { IPaciente } from 'app/shared/model/paciente.model';

@Component({
    selector: 'jhi-consulta-update',
    templateUrl: './consulta-update.component.html'
})
export class ConsultaUpdateComponent implements OnInit {
    private nroPaciente: number;
    paciente: IPaciente;

    private _consulta: IConsulta;
    isSaving: boolean;

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
        private activatedRoute: ActivatedRoute
    ) {
        this.activatedRoute.queryParams.subscribe(params => {
            this.nroPaciente = params['nroPaciente'];
        });
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ consulta }) => {
            this.consulta = consulta;
        });
        this.pacienteService.find(this.nroPaciente).subscribe(
            (pac: HttpResponse<IPaciente>) => {
                this.paciente = pac.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.constantesService.query({ filter: 'consulta-is-null' }).subscribe(
            (res: HttpResponse<IConstantes[]>) => {
                if (!this.consulta.constantesConsultaId) {
                    this.constantesconsultas = res.body;
                    this.consulta.fechaConsulta = moment(new Date());
                } else {
                    this.constantesService.find(this.consulta.constantesConsultaId).subscribe(
                        (subRes: HttpResponse<IConstantes>) => {
                            this.constantesconsultas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.consulta.id !== undefined) {
            this.subscribeToSaveResponse(this.consultaService.update(this.consulta));
        } else {
            this.subscribeToSaveResponse(this.consultaService.create(this.consulta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConsulta>>) {
        result.subscribe((res: HttpResponse<IConsulta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
                console.log('File is completely uploaded!');
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
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }
}
