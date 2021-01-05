import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnfermedad } from 'app/shared/model/enfermedad.model';
import { EnfermedadService } from './enfermedad.service';

@Component({
    selector: 'jhi-enfermedad-update',
    templateUrl: './enfermedad-update.component.html'
})
export class EnfermedadUpdateComponent implements OnInit {
    private _enfermedad: IEnfermedad;
    isSaving: boolean;

    constructor(private enfermedadService: EnfermedadService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enfermedad }) => {
            this.enfermedad = enfermedad;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enfermedad.id !== undefined) {
            this.subscribeToSaveResponse(this.enfermedadService.update(this.enfermedad));
        } else {
            this.subscribeToSaveResponse(this.enfermedadService.create(this.enfermedad));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnfermedad>>) {
        result.subscribe((res: HttpResponse<IEnfermedad>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get enfermedad() {
        return this._enfermedad;
    }

    set enfermedad(enfermedad: IEnfermedad) {
        this._enfermedad = enfermedad;
    }
}
