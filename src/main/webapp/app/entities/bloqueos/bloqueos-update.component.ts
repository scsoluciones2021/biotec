import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBloqueos } from 'app/shared/model/bloqueos.model';
import { BloqueosService } from './bloqueos.service';

@Component({
    selector: 'jhi-bloqueos-update',
    templateUrl: './bloqueos-update.component.html'
})
export class BloqueosUpdateComponent implements OnInit {
    private _bloqueos: IBloqueos;
    isSaving: boolean;
    fechaDesdeDp: any;
    fechaHastaDp: any;
    horaDesde: string;
    horaHasta: string;

    constructor(private bloqueosService: BloqueosService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bloqueos }) => {
            this.bloqueos = bloqueos;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.bloqueos.horaDesde = moment(this.horaDesde, DATE_TIME_FORMAT);
        this.bloqueos.horaHasta = moment(this.horaHasta, DATE_TIME_FORMAT);
        if (this.bloqueos.id !== undefined) {
            this.subscribeToSaveResponse(this.bloqueosService.update(this.bloqueos));
        } else {
            this.subscribeToSaveResponse(this.bloqueosService.create(this.bloqueos));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBloqueos>>) {
        result.subscribe((res: HttpResponse<IBloqueos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get bloqueos() {
        return this._bloqueos;
    }

    set bloqueos(bloqueos: IBloqueos) {
        this._bloqueos = bloqueos;
        this.horaDesde = moment(bloqueos.horaDesde).format(DATE_TIME_FORMAT);
        this.horaHasta = moment(bloqueos.horaHasta).format(DATE_TIME_FORMAT);
    }
}
