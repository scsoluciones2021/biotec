import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEjercicio } from 'app/shared/model/ejercicio.model';
import { EjercicioService } from './ejercicio.service';

@Component({
    selector: 'jhi-ejercicio-update',
    templateUrl: './ejercicio-update.component.html'
})
export class EjercicioUpdateComponent implements OnInit {
    private _ejercicio: IEjercicio;
    isSaving: boolean;

    constructor(private ejercicioService: EjercicioService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ejercicio }) => {
            this.ejercicio = ejercicio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ejercicio.id !== undefined) {
            this.subscribeToSaveResponse(this.ejercicioService.update(this.ejercicio));
        } else {
            this.subscribeToSaveResponse(this.ejercicioService.create(this.ejercicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEjercicio>>) {
        result.subscribe((res: HttpResponse<IEjercicio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get ejercicio() {
        return this._ejercicio;
    }

    set ejercicio(ejercicio: IEjercicio) {
        this._ejercicio = ejercicio;
    }
}
