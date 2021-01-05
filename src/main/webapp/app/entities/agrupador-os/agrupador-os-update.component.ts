import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAgrupadorOS } from 'app/shared/model/agrupador-os.model';
import { AgrupadorOSService } from './agrupador-os.service';

@Component({
    selector: 'jhi-agrupador-os-update',
    templateUrl: './agrupador-os-update.component.html'
})
export class AgrupadorOSUpdateComponent implements OnInit {
    private _agrupadorOS: IAgrupadorOS;
    isSaving: boolean;

    constructor(private agrupadorOSService: AgrupadorOSService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agrupadorOS }) => {
            this.agrupadorOS = agrupadorOS;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.agrupadorOS.id !== undefined) {
            this.subscribeToSaveResponse(this.agrupadorOSService.update(this.agrupadorOS));
        } else {
            this.subscribeToSaveResponse(this.agrupadorOSService.create(this.agrupadorOS));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAgrupadorOS>>) {
        result.subscribe((res: HttpResponse<IAgrupadorOS>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get agrupadorOS() {
        return this._agrupadorOS;
    }

    set agrupadorOS(agrupadorOS: IAgrupadorOS) {
        this._agrupadorOS = agrupadorOS;
    }
}
