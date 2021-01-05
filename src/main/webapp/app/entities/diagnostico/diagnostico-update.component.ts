import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from './diagnostico.service';

@Component({
    selector: 'jhi-diagnostico-update',
    templateUrl: './diagnostico-update.component.html'
})
export class DiagnosticoUpdateComponent implements OnInit {
    private _diagnostico: IDiagnostico;
    isSaving: boolean;

    constructor(private diagnosticoService: DiagnosticoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ diagnostico }) => {
            this.diagnostico = diagnostico;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.diagnostico.id !== undefined) {
            this.subscribeToSaveResponse(this.diagnosticoService.update(this.diagnostico));
        } else {
            this.subscribeToSaveResponse(this.diagnosticoService.create(this.diagnostico));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDiagnostico>>) {
        result.subscribe((res: HttpResponse<IDiagnostico>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get diagnostico() {
        return this._diagnostico;
    }

    set diagnostico(diagnostico: IDiagnostico) {
        this._diagnostico = diagnostico;
    }
}
