import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';
import { ObsAntecPersonalService } from './obs-antec-personal.service';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales';

@Component({
    selector: 'jhi-obs-antec-personal-update',
    templateUrl: './obs-antec-personal-update.component.html'
})
export class ObsAntecPersonalUpdateComponent implements OnInit {
    private _obsAntecPersonal: IObsAntecPersonal;
    isSaving: boolean;

    antecedentespersonales: IAntecedentesPersonales[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private obsAntecPersonalService: ObsAntecPersonalService,
        private antecedentesPersonalesService: AntecedentesPersonalesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ obsAntecPersonal }) => {
            this.obsAntecPersonal = obsAntecPersonal;
        });
        this.antecedentesPersonalesService.query().subscribe(
            (res: HttpResponse<IAntecedentesPersonales[]>) => {
                this.antecedentespersonales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.obsAntecPersonal.id !== undefined) {
            this.subscribeToSaveResponse(this.obsAntecPersonalService.update(this.obsAntecPersonal));
        } else {
            this.subscribeToSaveResponse(this.obsAntecPersonalService.create(this.obsAntecPersonal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IObsAntecPersonal>>) {
        result.subscribe((res: HttpResponse<IObsAntecPersonal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAntecedentesPersonalesById(index: number, item: IAntecedentesPersonales) {
        return item.id;
    }
    get obsAntecPersonal() {
        return this._obsAntecPersonal;
    }

    set obsAntecPersonal(obsAntecPersonal: IObsAntecPersonal) {
        this._obsAntecPersonal = obsAntecPersonal;
    }
}
