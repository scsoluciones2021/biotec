import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';
import { AdjuntosFichaService } from './adjuntos-ficha.service';

@Component({
    selector: 'jhi-adjuntos-ficha-update',
    templateUrl: './adjuntos-ficha-update.component.html'
})
export class AdjuntosFichaUpdateComponent implements OnInit {
    private _adjuntos_ficha: IAdjuntosFicha;
    isSaving: boolean;
    fecha: string;

    constructor(private adjuntos_fichaService: AdjuntosFichaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adjuntos_ficha }) => {
            this.adjuntos_ficha = adjuntos_ficha;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.adjuntos_ficha.fecha = moment(this.fecha, DATE_TIME_FORMAT);
        if (this.adjuntos_ficha.id !== undefined) {
            this.subscribeToSaveResponse(this.adjuntos_fichaService.update(this.adjuntos_ficha));
        } else {
            this.subscribeToSaveResponse(this.adjuntos_fichaService.create(this.adjuntos_ficha));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdjuntosFicha>>) {
        result.subscribe((res: HttpResponse<IAdjuntosFicha>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get adjuntos_ficha() {
        return this._adjuntos_ficha;
    }

    set adjuntos_ficha(adjuntos_ficha: IAdjuntosFicha) {
        this._adjuntos_ficha = adjuntos_ficha;
        this.fecha = moment(adjuntos_ficha.fecha).format(DATE_TIME_FORMAT);
    }
}
