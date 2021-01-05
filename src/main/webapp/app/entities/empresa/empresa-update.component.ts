import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';

@Component({
    selector: 'jhi-empresa-update',
    templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
    private _empresa: IEmpresa;
    isSaving: boolean;

    codigopostals: ICodigoPostal[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private empresaService: EmpresaService,
        private codigoPostalService: CodigoPostalService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empresa }) => {
            this.empresa = empresa;
        });
        this.codigoPostalService.query().subscribe(
            (res: HttpResponse<ICodigoPostal[]>) => {
                this.codigopostals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.empresa.id !== undefined) {
            this.subscribeToSaveResponse(this.empresaService.update(this.empresa));
        } else {
            this.subscribeToSaveResponse(this.empresaService.create(this.empresa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
        result.subscribe((res: HttpResponse<IEmpresa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCodigoPostalById(index: number, item: ICodigoPostal) {
        return item.id;
    }
    get empresa() {
        return this._empresa;
    }

    set empresa(empresa: IEmpresa) {
        this._empresa = empresa;
    }
}
