import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAlergia } from 'app/shared/model/alergia.model';
import { AlergiaService } from './alergia.service';

@Component({
    selector: 'jhi-alergia-update',
    templateUrl: './alergia-update.component.html'
})
export class AlergiaUpdateComponent implements OnInit {
    private _alergia: IAlergia;
    isSaving: boolean;

    constructor(private alergiaService: AlergiaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ alergia }) => {
            this.alergia = alergia;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.alergia.id !== undefined) {
            this.subscribeToSaveResponse(this.alergiaService.update(this.alergia));
        } else {
            this.subscribeToSaveResponse(this.alergiaService.create(this.alergia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlergia>>) {
        result.subscribe((res: HttpResponse<IAlergia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get alergia() {
        return this._alergia;
    }

    set alergia(alergia: IAlergia) {
        this._alergia = alergia;
    }
}
