import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { IntoleranciaService } from './intolerancia.service';

@Component({
    selector: 'jhi-intolerancia-update',
    templateUrl: './intolerancia-update.component.html'
})
export class IntoleranciaUpdateComponent implements OnInit {
    private _intolerancia: IIntolerancia;
    isSaving: boolean;

    constructor(private intoleranciaService: IntoleranciaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ intolerancia }) => {
            this.intolerancia = intolerancia;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.intolerancia.id !== undefined) {
            this.subscribeToSaveResponse(this.intoleranciaService.update(this.intolerancia));
        } else {
            this.subscribeToSaveResponse(this.intoleranciaService.create(this.intolerancia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIntolerancia>>) {
        result.subscribe((res: HttpResponse<IIntolerancia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get intolerancia() {
        return this._intolerancia;
    }

    set intolerancia(intolerancia: IIntolerancia) {
        this._intolerancia = intolerancia;
    }
}
