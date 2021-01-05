import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';
import { IProfesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from 'app/entities/profesional';
import { IAgrupadorOS } from '../../shared/model/agrupador-os.model';
import { AgrupadorOSService } from '../agrupador-os/agrupador-os.service';

@Component({
    selector: 'jhi-obra-social-update',
    templateUrl: './obra-social-update.component.html'
})
export class ObraSocialUpdateComponent implements OnInit {
    private _obraSocial: IObraSocial;
    isSaving: boolean;

    agrupadores: IAgrupadorOS[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private obraSocialService: ObraSocialService,
        private agrupadorService: AgrupadorOSService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ obraSocial }) => {
            this.obraSocial = obraSocial;
        });
        this.agrupadorService.query().subscribe(
            (res: HttpResponse<IAgrupadorOS[]>) => {
                this.agrupadores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.obraSocial.id !== undefined) {
            this.subscribeToSaveResponse(this.obraSocialService.update(this.obraSocial));
        } else {
            this.subscribeToSaveResponse(this.obraSocialService.create(this.obraSocial));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IObraSocial>>) {
        result.subscribe((res: HttpResponse<IObraSocial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProfesionalById(index: number, item: IProfesional) {
        return item.id;
    }

    trackAgrupadorById(index: number, item: IAgrupadorOS) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get obraSocial() {
        return this._obraSocial;
    }

    set obraSocial(obraSocial: IObraSocial) {
        this._obraSocial = obraSocial;
    }
}
