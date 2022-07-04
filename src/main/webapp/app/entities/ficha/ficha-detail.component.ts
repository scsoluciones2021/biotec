import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';

import { IFicha } from 'app/shared/model/ficha.model';

import { HttpResponse, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { FichaService } from './ficha.service';
import { Observable } from 'rxjs';
import { FileUpload } from 'primeng/components/fileupload/fileupload';
import { TreeNode } from 'primeng/primeng';
import { ConsultaService, ConsultaModalComponent } from '../consulta';
import { IConsulta } from '../../shared/model/consulta.model';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import dayjs = require('dayjs');
import { AdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';
import { AdjuntosFichaService } from '../adjuntos-ficha';
import * as fileSaver from 'file-saver';

@Component({
    selector: 'jhi-ficha-detail',
    templateUrl: './ficha-detail.component.html'
})
export class FichaDetailComponent implements OnInit {
    ficha: IFicha;

    // Variables para archivos
    fileUploads: any[] = []; //Observable<string[]>;
    showFile = false;
    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = { percentage: 0 };
    // fin variables para archivos

    // primeng upload
    uploadedFiles: any[] = [];
    hayArchivosParaSubir = false;

    // fin primeng upload

    // Para arbol de consultas
    // arbolConsultas: TreeNode[];
    listaConsultas: IConsulta[];
    // fin arbol de consultas

    // modal para las consultas
    modalRef: NgbModalRef;

    jhiAlertService: any;

    constructor(
        private activatedRoute: ActivatedRoute,
        private fichaService: FichaService,
        private consultaService: ConsultaService,
        private modalService: NgbModal,
        private router: Router,
        private adjuntosService: AdjuntosFichaService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ficha }) => {
            this.ficha = ficha;
        });
        this.showFile = false;
        this.showFiles(true);

        dayjs.locale('es');
        this.consultaService.getConsultasXFicha(this.ficha.id).subscribe(
            (res: HttpResponse<IConsulta[]>) => {
                this.listaConsultas = res.body;
            },
            (res: HttpErrorResponse) => {
                console.log('paso por aca error');
                this.onError(res.message);
            }
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    // Modal para ver la consulta seleccionada
    openFormModal(consulta) {
        this.modalRef = this.modalService.open(ConsultaModalComponent as Component, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.consulta = consulta;

        // al volver
        this.modalRef.result
            .then(result => {
                // reload del timeline?
            })
            .catch(error => {
                console.log(error);
            });
    }

    previousState() {
        window.history.back();
    }

    showFiles(enable: boolean) {
        this.showFile = enable;
        if (enable) {
            const listaDeNombres = this.fichaService.getFilesName(this.ficha.id);
            const archivos = this.fileUploads;

            listaDeNombres.forEach(function(nom) {
                nom.forEach(function(contenido) {
                    const sep = contenido.split(',');
                    archivos.push(sep);
                });
            });

            this.fileUploads = archivos;
        }
    }

    // Función para que el progressbar se muestre un ratito...
    async delay(ms: number) {
        await new Promise<void>(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }

    onUploadEnd(event, form) {
        for (const file of event.files) {
            this.uploadedFiles.push(file);
        }

        this.progress.percentage = 0;
        this.hayArchivosParaSubir = true;

        for (let i = 0; i < this.uploadedFiles.length; i++) {
            this.fichaService.pushFileToStorage(this.uploadedFiles[i], this.ficha).subscribe(evento => {
                if (evento.type === HttpEventType.UploadProgress) {
                    this.progress.percentage = Math.round(100 * evento.loaded / evento.total);
                    form.clear();
                } else if (evento instanceof HttpResponse) {
                    form.clear();
                    this.delay(1000).then(any => {
                        this.hayArchivosParaSubir = false;
                        this.progress.percentage = 0;
                        this.showFile = false;
                        this.showFiles(true);
                    });
                }
            });
        }
        this.selectedFiles = undefined;
        this.uploadedFiles = [];
    }

    descargarArchivo(nombreArchivo) {
        this.adjuntosService.getFileToDownload(nombreArchivo).subscribe(data => {
            console.log('volvió: ' + data.headers.get('filename'));
            this.saveFile(data.body, data.headers.get('filename'));
        });
    }

    saveFile(data: any, filename?: string) {
        const blob = new Blob([data], { type: 'octet-stream' });
        // opción 1 funciona pero abre el archivo en una página aparte
        const url = window.URL.createObjectURL(blob);
        window.open(url);

        // opción 2 funciona pero cdo descarga después da error al abrir
        //fileSaver.saveAs(blob, filename);
    }

    getSizeInMegaBytes(file: File) {
        return file ? file.size / 1000000 : 0;
    }

    removeFile(file: File, uploader: FileUpload) {
        const index = uploader.files.indexOf(file);
        uploader.remove(event, index);
    }

    public consultaNueva() {
        // Agregamos skipLocationChange: true para que no se vean los parámetros en la url
        let navigationExtras: NavigationExtras = {
            queryParams: {
                nroPaciente: this.ficha.pacienteId
            },
            skipLocationChange: true
        };
        this.router.navigate(['/consulta/new'], navigationExtras);
    }
}
