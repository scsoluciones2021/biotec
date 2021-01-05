import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProvincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from './provincia.service';

@Component({
    selector: 'jhi-provincia-update',
    templateUrl: './provincia-update.component.html'
})
export class ProvinciaUpdateComponent implements OnInit {
    private _provincia: IProvincia;
    isSaving: boolean;

    constructor(private provinciaService: ProvinciaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ provincia }) => {
            this.provincia = provincia;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.provincia.id !== undefined) {
            this.subscribeToSaveResponse(this.provinciaService.update(this.provincia));
        } else {
            this.subscribeToSaveResponse(this.provinciaService.create(this.provincia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProvincia>>) {
        result.subscribe((res: HttpResponse<IProvincia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get provincia() {
        return this._provincia;
    }

    set provincia(provincia: IProvincia) {
        this._provincia = provincia;
    }
}
