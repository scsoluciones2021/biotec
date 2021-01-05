import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEstados } from 'app/shared/model/estados.model';
import { EstadosService } from './estados.service';

@Component({
    selector: 'jhi-estados-update',
    templateUrl: './estados-update.component.html'
})
export class EstadosUpdateComponent implements OnInit {
    private _estados: IEstados;
    isSaving: boolean;

    constructor(private estadosService: EstadosService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estados }) => {
            this.estados = estados;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estados.id !== undefined) {
            this.subscribeToSaveResponse(this.estadosService.update(this.estados));
        } else {
            this.subscribeToSaveResponse(this.estadosService.create(this.estados));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEstados>>) {
        result.subscribe((res: HttpResponse<IEstados>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get estados() {
        return this._estados;
    }

    set estados(estados: IEstados) {
        this._estados = estados;
    }
}
