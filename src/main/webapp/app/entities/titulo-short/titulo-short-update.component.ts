import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITituloShort } from 'app/shared/model/titulo-short.model';
import { TituloShortService } from './titulo-short.service';

@Component({
    selector: 'jhi-titulo-short-update',
    templateUrl: './titulo-short-update.component.html'
})
export class TituloShortUpdateComponent implements OnInit {
    private _tituloShort: ITituloShort;
    isSaving: boolean;

    constructor(private tituloShortService: TituloShortService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tituloShort }) => {
            this.tituloShort = tituloShort;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tituloShort.id !== undefined) {
            this.subscribeToSaveResponse(this.tituloShortService.update(this.tituloShort));
        } else {
            this.subscribeToSaveResponse(this.tituloShortService.create(this.tituloShort));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITituloShort>>) {
        result.subscribe((res: HttpResponse<ITituloShort>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tituloShort() {
        return this._tituloShort;
    }

    set tituloShort(tituloShort: ITituloShort) {
        this._tituloShort = tituloShort;
    }
}
