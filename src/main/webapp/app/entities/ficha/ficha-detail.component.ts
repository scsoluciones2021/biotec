import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';

import { IFicha } from 'app/shared/model/ficha.model';

import { HttpResponse, HttpEventType } from '@angular/common/http';
import { FichaService } from './ficha.service';
import { Observable } from 'rxjs';
import { FileUpload } from 'primeng/components/fileupload/fileupload';
import { TreeNode } from 'primeng/primeng';

@Component({
    selector: 'jhi-ficha-detail',
    templateUrl: './ficha-detail.component.html'
})
export class FichaDetailComponent implements OnInit {
    ficha: IFicha;

    // Variables para archivos
    fileUploads: Observable<string[]>;
    showFile = false;
    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = { percentage: 0 };
    // fin variables para archivos

    // primeng upload
    uploadedFiles: any[] = [];
    hayArchivosParaSubir = false;

    // fin primeng upload

    // Para consultas
    isFoo = false;
    // Para arbol de consultas
    files: TreeNode[];
    // fin arbol de consultas

    constructor(private activatedRoute: ActivatedRoute, private fichaService: FichaService, private router: Router) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ficha }) => {
            this.ficha = ficha;
        });
        this.showFile = false;
        this.showFiles(true);

        this.files = [
            {
                label: '2019',
                collapsedIcon: 'fa-folder',
                expandedIcon: 'fa-folder-open',
                children: [
                    {
                        label: 'Folder 2',
                        collapsedIcon: 'fa-folder',
                        expandedIcon: 'fa-folder-open',
                        children: [
                            {
                                label: 'File 2',
                                icon: 'fa-file-o'
                            }
                        ]
                    },
                    {
                        label: 'Folder 2',
                        collapsedIcon: 'fa-folder',
                        expandedIcon: 'fa-folder-open'
                    },
                    {
                        label: 'File 1',
                        icon: 'fa-file-o'
                    }
                ]
            },
            {
                label: '2018',
                collapsedIcon: 'fa-folder',
                expandedIcon: 'fa-folder-open'
            }
        ];
    }

    previousState() {
        window.history.back();
    }

    showFiles(enable: boolean) {
        this.showFile = enable;
        if (enable) {
            this.fileUploads = this.fichaService.getFiles(this.ficha.id);
        }
    }

    // Función para que el progressbar se muestre un ratito...
    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
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
        /* console.log("paso previo");
        this.fichaService.getFile(nombreArchivo).subscribe(res=>{
            console.log("paso por acá");
            const url = "file://127.1.1.1/upload/Fichas/"+nombreArchivo;
            console.log(url);*/
        const link = document.createElement('a');
        link.target = '_blank';
        link.href = 'file:///127.1.1.1:9000/upload/Fichas/' + nombreArchivo; // URL.createObjectURL(res);
        link.setAttribute('visibility', 'hidden');
        link.click();
        // });*/

        /* const fileURL = URL.createObjectURL(this.fichaService.getFile(nombreArchivo));
        // return this.fichaService.getFile(nombreArchivo);
       // file://localhost/upload/Fichas/1_1_2.png
       // console.log("Ruta archivo: " + ruta.);
        window.open('file:///127.1.1.1:8080/upload/Fichas/'+nombreArchivo);
       console.log(fileURL);
      const link = document.createElement('a');
link.target = '_blank';
link.href = fileURL;
link.setAttribute('visibility', 'hidden');
link.click();*/
    }

    getSizeInMegaBytes(file: File) {
        return file ? file.size / 1000000 : 0;
    }

    removeFile(file: File, uploader: FileUpload) {
        const index = uploader.files.indexOf(file);
        uploader.remove(event, index);
    }

    private consultaNueva() {
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

/* Para descarga de archivos
 this.fichaService.getFile(nombreArchivo).subscribe(x => {
            // It is necessary to create a new blob object with mime-type explicitly set
            // otherwise only Chrome works like it should
            var newBlob = new Blob([x], { type: "multipart/form-data;" });

            // IE doesn't allow using a blob object directly as link href
            // instead it is necessary to use msSaveOrOpenBlob
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(newBlob);
                return;
            }

            // For other browsers:
            // Create a link pointing to the ObjectURL containing the blob.
            const data = window.URL.createObjectURL(newBlob);

            var link = document.createElement('a');
            link.href = data;
            link.download = "Je kar.pdf";
            // this is necessary as link.click() does not work on the latest firefox
            link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

            setTimeout(function () {
                // For Firefox it is necessary to delay revoking the ObjectURL
                window.URL.revokeObjectURL(data);
            }, 100);
        });

Para carga de archivos
    onFileChanged(event) {
        this.selectedFiles = event.target.files;
    }

    onUpload() {
        this.progress.percentage = 0;

        this.currentFileUpload = this.selectedFiles.item(0);

         this.fichaService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            this.delay(3000).then(any=>{
               this.currentFileUpload = null;
               this.progress.percentage = 0;
            });
          }
        });

        this.selectedFiles = undefined;
    }*/
