import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';
import { IEnfermedad } from 'app/shared/model/enfermedad.model';
import { EnfermedadService } from 'app/entities/enfermedad';
import { IAlergia } from 'app/shared/model/alergia.model';
import { AlergiaService } from 'app/entities/alergia';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { IntoleranciaService } from 'app/entities/intolerancia';
import { IRegimen } from 'app/shared/model/regimen.model';
import { RegimenService } from 'app/entities/regimen';
import { IEjercicio } from 'app/shared/model/ejercicio.model';
import { EjercicioService } from 'app/entities/ejercicio';
import { IBebida } from 'app/shared/model/bebida.model';
import { BebidaService } from 'app/entities/bebida';

@Component({
    selector: 'jhi-antecedentes-personales-update',
    templateUrl: './antecedentes-personales-update.component.html'
})
export class AntecedentesPersonalesUpdateComponent implements OnInit {
    private _antecedentesPersonales: IAntecedentesPersonales;
    isSaving: boolean;

    enfermedads: IEnfermedad[];

    alergias: IAlergia[];

    intolerancias: IIntolerancia[];

    regimen: IRegimen[];

    ejercicios: IEjercicio[];

    bebidas: IBebida[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private antecedentesPersonalesService: AntecedentesPersonalesService,
        private enfermedadService: EnfermedadService,
        private alergiaService: AlergiaService,
        private intoleranciaService: IntoleranciaService,
        private regimenService: RegimenService,
        private ejercicioService: EjercicioService,
        private bebidaService: BebidaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ antecedentesPersonales }) => {
            this.antecedentesPersonales = antecedentesPersonales;
        });
        this.enfermedadService.query().subscribe(
            (res: HttpResponse<IEnfermedad[]>) => {
                this.enfermedads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.alergiaService.query().subscribe(
            (res: HttpResponse<IAlergia[]>) => {
                this.alergias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.intoleranciaService.query().subscribe(
            (res: HttpResponse<IIntolerancia[]>) => {
                this.intolerancias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.regimenService.query().subscribe(
            (res: HttpResponse<IRegimen[]>) => {
                this.regimen = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ejercicioService.query().subscribe(
            (res: HttpResponse<IEjercicio[]>) => {
                this.ejercicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bebidaService.query().subscribe(
            (res: HttpResponse<IBebida[]>) => {
                this.bebidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.antecedentesPersonales.id !== undefined) {
            this.subscribeToSaveResponse(this.antecedentesPersonalesService.update(this.antecedentesPersonales));
        } else {
            this.subscribeToSaveResponse(this.antecedentesPersonalesService.create(this.antecedentesPersonales));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAntecedentesPersonales>>) {
        result.subscribe(
            (res: HttpResponse<IAntecedentesPersonales>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackEnfermedadById(index: number, item: IEnfermedad) {
        return item.id;
    }

    trackAlergiaById(index: number, item: IAlergia) {
        return item.id;
    }

    trackIntoleranciaById(index: number, item: IIntolerancia) {
        return item.id;
    }

    trackRegimenById(index: number, item: IRegimen) {
        return item.id;
    }

    trackEjercicioById(index: number, item: IEjercicio) {
        return item.id;
    }

    trackBebidaById(index: number, item: IBebida) {
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
    get antecedentesPersonales() {
        return this._antecedentesPersonales;
    }

    set antecedentesPersonales(antecedentesPersonales: IAntecedentesPersonales) {
        this._antecedentesPersonales = antecedentesPersonales;
    }
}
