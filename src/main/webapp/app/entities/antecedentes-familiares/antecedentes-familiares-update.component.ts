import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';
import { IFamiliar } from 'app/shared/model/familiar.model';
import { FamiliarService } from 'app/entities/familiar';

@Component({
    selector: 'jhi-antecedentes-familiares-update',
    templateUrl: './antecedentes-familiares-update.component.html'
})
export class AntecedentesFamiliaresUpdateComponent implements OnInit {
    private _antecedentesFamiliares: IAntecedentesFamiliares;
    isSaving: boolean;

    familiars: IFamiliar[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private antecedentesFamiliaresService: AntecedentesFamiliaresService,
        private familiarService: FamiliarService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ antecedentesFamiliares }) => {
            this.antecedentesFamiliares = antecedentesFamiliares;
        });
        this.familiarService.query().subscribe(
            (res: HttpResponse<IFamiliar[]>) => {
                this.familiars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.antecedentesFamiliares.id !== undefined) {
            this.subscribeToSaveResponse(this.antecedentesFamiliaresService.update(this.antecedentesFamiliares));
        } else {
            this.subscribeToSaveResponse(this.antecedentesFamiliaresService.create(this.antecedentesFamiliares));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAntecedentesFamiliares>>) {
        result.subscribe(
            (res: HttpResponse<IAntecedentesFamiliares>) => this.onSaveSuccess(),
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

    trackFamiliarById(index: number, item: IFamiliar) {
        return item.id;
    }
    get antecedentesFamiliares() {
        return this._antecedentesFamiliares;
    }

    set antecedentesFamiliares(antecedentesFamiliares: IAntecedentesFamiliares) {
        this._antecedentesFamiliares = antecedentesFamiliares;
    }
}
