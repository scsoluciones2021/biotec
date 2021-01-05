import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProfesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from './profesional.service';
import { IUser, UserService } from 'app/core';
import { ITituloShort } from 'app/shared/model/titulo-short.model';
import { TituloShortService } from 'app/entities/titulo-short';
import { IObraSocial, ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';
import { IEspecialidad } from 'app/shared/model/especialidad.model';
import { EspecialidadService } from 'app/entities/especialidad';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';
import { CodigoPostal } from '../../shared/model/codigo-postal.model';
import { Especialidad } from '../../shared/model/especialidad.model';
import { IDiagnostico } from '../../shared/model/diagnostico.model';

@Component({
    selector: 'jhi-profesional-update',
    templateUrl: './profesional-update.component.html'
})
export class ProfesionalUpdateComponent implements OnInit {
    private _profesional: IProfesional;
    isSaving: boolean;

    users: IUser[];

    titulos: ITituloShort[];

    imagenUrl = '/content/images/default_doc.png';

    // Variables autocomplete
    codigoPostal: any;
    cpPNG: any[];
    filteredCPSingle: any[];

    // autocomplete especialidad
    especialidads: IEspecialidad[];
    espPNG: any[];
    texts: IEspecialidad[] = [];
    especialidad: Especialidad;
    // fin autocomplete

    // autocomplete obra social
    obrasocials: IObraSocial[];
    obsPNG: any[];
    obrasocial: ObraSocial;
    // fin autocomplete

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private profesionalService: ProfesionalService,
        private userService: UserService,
        private tituloShortService: TituloShortService,
        private obraSocialService: ObraSocialService,
        private especialidadService: EspecialidadService,
        private codigoPostalService: CodigoPostalService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ profesional }) => {
            this.profesional = profesional;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tituloShortService.query({ filter: 'profesional-is-null' }).subscribe(
            (res: HttpResponse<ITituloShort[]>) => {
                if (!this.profesional.tituloId) {
                    this.titulos = res.body;
                } else {
                    this.tituloShortService.find(this.profesional.tituloId).subscribe(
                        (subRes: HttpResponse<ITituloShort>) => {
                            this.titulos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
       /* this.obraSocialService.query().subscribe(
            (res: HttpResponse<IObraSocial[]>) => {
                this.obrasocials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
         this.especialidadService.query().subscribe(
            (res: HttpResponse<IEspecialidad[]>) => {
                this.especialidads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/
        /* this.codigoPostalService.query().subscribe(
            (res: HttpResponse<ICodigoPostal[]>) => {
                this.codigopostals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/

        if (this.profesional.codigoPostalId) {
            this.codigoPostal = new CodigoPostal();
            this.codigoPostal.id = this.profesional.codigoPostalId;
            this.codigoPostal.nombreCiudad = this.profesional.codigoPostalNombreCiudad;
        }

        if (this.profesional.especialidads) {
            this.especialidads = this.profesional.especialidads;
        }

        if (this.profesional.obrasocials) {
            this.obrasocials = this.profesional.obrasocials;
        }

    }

    buscarEspecialidad(event) {
        const query = event.query;
        this.especialidadService.buscarEspecialidad(query).subscribe(
            (res: HttpResponse<IEspecialidad[]>) => {
                this.especialidads = this.filterEspecialidad(query, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterEspecialidad(query, espPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < espPNG.length; i++) {
            const espec = espPNG[i];
            if (espec.nombreEspecialidad.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(espec);
            }
        }
        return filtered;
    }
    /* onKeyUp(event: KeyboardEvent) {
        if (event.key == "Enter") {
         let tokenInput = event.srcElement as any;
         if (tokenInput.value) {
             console.log(tokenInput.value);
          this.texts.push(tokenInput.value);
          tokenInput.value = "";
         }
        }
      }*/

    seleccionEspecialidad(event) {
        this.especialidad = new Especialidad();
        this.especialidad.id = event.id;
        this.especialidad.nombreEspecialidad = event.nombreEspecialidad;
        this.especialidad.codigoEspecilidad = event.codigoEspecialidad;
        if (!this.profesional.especialidads) {
            this.profesional.especialidads = new Array();
            this.profesional.especialidads.push(this.especialidad);
        }
    }

    // Obra social
    buscarObraSocial(event) {
        const query = event.query;
        this.obraSocialService.buscarObraSocial(query).subscribe(
            (res: HttpResponse<IObraSocial[]>) => {
                this.obrasocials = this.filterObraSocial(query, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterObraSocial(query, obsPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < obsPNG.length; i++) {
            const obs = obsPNG[i];
            if (obs.nombreObraSocial.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(obs);
            }
        }
        return filtered;
    }

    seleccionObraSocial(event) {
        this.obrasocial = new ObraSocial();
        this.obrasocial.id = event.id;
        this.obrasocial.nombreObraSocial = event.nombreObraSocial;
        this.obrasocial.codigoObraSocial = event.codigoObraSocial;
        if (!this.profesional.obrasocials) {
            this.profesional.obrasocials = new Array();
            this.profesional.obrasocials.push(this.obrasocial);
        }
    }
    // fin obra social

    // PRIMENG
    // Código Postal
    filterCPSingle(event) {
        const query = event.query;
        this.codigoPostalService.buscarCP(query).subscribe(
            (res: HttpResponse<ICodigoPostal[]>) => {
               this.filteredCPSingle = this.filterCP(query, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterCP(query, cpPNG: any[]): any[] {
        const filtered: any[] = [];
        for (let i = 0; i < cpPNG.length; i++) {
            const codigoPostal = cpPNG[i];
            if (codigoPostal.nombreCiudad.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(codigoPostal);
            }
        }
        return filtered;
    }

    seleccionCP(event) {
        this.profesional.codigoPostalId = event.id;
        this.profesional.codigoPostalNombreCiudad = event.nombreCiudad;
    }

    borrarCP() {
        this.profesional.codigoPostalId = null;
        this.profesional.codigoPostalNombreCiudad = null;
    }
    // Fin primeng

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.profesional, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.profesional.id !== undefined) {
            this.subscribeToSaveResponse(this.profesionalService.update(this.profesional));
        } else {
            this.subscribeToSaveResponse(this.profesionalService.create(this.profesional));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProfesional>>) {
        result.subscribe((res: HttpResponse<IProfesional>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackTituloShortById(index: number, item: ITituloShort) {
        return item.id;
    }

    trackObraSocialById(index: number, item: IObraSocial) {
        return item.id;
    }

    trackEspecialidadById(index: number, item: IEspecialidad) {
        return item.id;
    }

    trackCodigoPostalById(index: number, item: ICodigoPostal) {
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
    get profesional() {
        return this._profesional;
    }

    set profesional(profesional: IProfesional) {
        this._profesional = profesional;
    }
}
