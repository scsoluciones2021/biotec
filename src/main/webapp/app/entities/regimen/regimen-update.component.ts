import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRegimen } from 'app/shared/model/regimen.model';
import { RegimenService } from './regimen.service';

@Component({
    selector: 'jhi-regimen-update',
    templateUrl: './regimen-update.component.html'
})
export class RegimenUpdateComponent implements OnInit {
    private _regimen: IRegimen;
    isSaving: boolean;

    constructor(private regimenService: RegimenService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regimen }) => {
            this.regimen = regimen;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.regimen.id !== undefined) {
            this.subscribeToSaveResponse(this.regimenService.update(this.regimen));
        } else {
            this.subscribeToSaveResponse(this.regimenService.create(this.regimen));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegimen>>) {
        result.subscribe((res: HttpResponse<IRegimen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get regimen() {
        return this._regimen;
    }

    set regimen(regimen: IRegimen) {
        this._regimen = regimen;
    }
}
