import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBebida } from 'app/shared/model/bebida.model';
import { BebidaService } from './bebida.service';

@Component({
    selector: 'jhi-bebida-update',
    templateUrl: './bebida-update.component.html'
})
export class BebidaUpdateComponent implements OnInit {
    private _bebida: IBebida;
    isSaving: boolean;

    constructor(private bebidaService: BebidaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bebida }) => {
            this.bebida = bebida;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bebida.id !== undefined) {
            this.subscribeToSaveResponse(this.bebidaService.update(this.bebida));
        } else {
            this.subscribeToSaveResponse(this.bebidaService.create(this.bebida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBebida>>) {
        result.subscribe((res: HttpResponse<IBebida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get bebida() {
        return this._bebida;
    }

    set bebida(bebida: IBebida) {
        this._bebida = bebida;
    }
}
