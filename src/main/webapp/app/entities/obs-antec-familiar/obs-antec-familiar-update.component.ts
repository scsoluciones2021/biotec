import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';
import { ObsAntecFamiliarService } from './obs-antec-familiar.service';
import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares';

@Component({
    selector: 'jhi-obs-antec-familiar-update',
    templateUrl: './obs-antec-familiar-update.component.html'
})
export class ObsAntecFamiliarUpdateComponent implements OnInit {
    private _obsAntecFamiliar: IObsAntecFamiliar;
    isSaving: boolean;

    antecedentesfamiliares: IAntecedentesFamiliares[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private obsAntecFamiliarService: ObsAntecFamiliarService,
        private antecedentesFamiliaresService: AntecedentesFamiliaresService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ obsAntecFamiliar }) => {
            this.obsAntecFamiliar = obsAntecFamiliar;
        });
        this.antecedentesFamiliaresService.query().subscribe(
            (res: HttpResponse<IAntecedentesFamiliares[]>) => {
                this.antecedentesfamiliares = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.obsAntecFamiliar.id !== undefined) {
            this.subscribeToSaveResponse(this.obsAntecFamiliarService.update(this.obsAntecFamiliar));
        } else {
            this.subscribeToSaveResponse(this.obsAntecFamiliarService.create(this.obsAntecFamiliar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IObsAntecFamiliar>>) {
        result.subscribe((res: HttpResponse<IObsAntecFamiliar>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAntecedentesFamiliaresById(index: number, item: IAntecedentesFamiliares) {
        return item.id;
    }
    get obsAntecFamiliar() {
        return this._obsAntecFamiliar;
    }

    set obsAntecFamiliar(obsAntecFamiliar: IObsAntecFamiliar) {
        this._obsAntecFamiliar = obsAntecFamiliar;
    }
}
