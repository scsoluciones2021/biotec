import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConstantes } from 'app/shared/model/constantes.model';
import { ConstantesService } from './constantes.service';

@Component({
    selector: 'jhi-constantes-update',
    templateUrl: './constantes-update.component.html'
})
export class ConstantesUpdateComponent implements OnInit {
    private _constantes: IConstantes;
    isSaving: boolean;

    constructor(private constantesService: ConstantesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ constantes }) => {
            this.constantes = constantes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.constantes.id !== undefined) {
            this.subscribeToSaveResponse(this.constantesService.update(this.constantes));
        } else {
            this.subscribeToSaveResponse(this.constantesService.create(this.constantes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConstantes>>) {
        result.subscribe((res: HttpResponse<IConstantes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get constantes() {
        return this._constantes;
    }

    set constantes(constantes: IConstantes) {
        this._constantes = constantes;
    }
}
