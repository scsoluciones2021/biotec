import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from './codigo-postal.service';
import { IProvincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from 'app/entities/provincia';

@Component({
    selector: 'jhi-codigo-postal-update',
    templateUrl: './codigo-postal-update.component.html'
})
export class CodigoPostalUpdateComponent implements OnInit {
    private _codigoPostal: ICodigoPostal;
    isSaving: boolean;

    provincias: IProvincia[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private codigoPostalService: CodigoPostalService,
        private provinciaService: ProvinciaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ codigoPostal }) => {
            this.codigoPostal = codigoPostal;
        });
        this.provinciaService.query().subscribe(
            (res: HttpResponse<IProvincia[]>) => {
                this.provincias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.codigoPostal.id !== undefined) {
            this.subscribeToSaveResponse(this.codigoPostalService.update(this.codigoPostal));
        } else {
            this.subscribeToSaveResponse(this.codigoPostalService.create(this.codigoPostal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICodigoPostal>>) {
        result.subscribe((res: HttpResponse<ICodigoPostal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProvinciaById(index: number, item: IProvincia) {
        return item.id;
    }
    get codigoPostal() {
        return this._codigoPostal;
    }

    set codigoPostal(codigoPostal: ICodigoPostal) {
        this._codigoPostal = codigoPostal;
    }
}
