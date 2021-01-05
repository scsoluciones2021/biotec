import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFamiliar } from 'app/shared/model/familiar.model';
import { FamiliarService } from './familiar.service';

@Component({
    selector: 'jhi-familiar-update',
    templateUrl: './familiar-update.component.html'
})
export class FamiliarUpdateComponent implements OnInit {
    private _familiar: IFamiliar;
    isSaving: boolean;

    constructor(private familiarService: FamiliarService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ familiar }) => {
            this.familiar = familiar;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.familiar.id !== undefined) {
            this.subscribeToSaveResponse(this.familiarService.update(this.familiar));
        } else {
            this.subscribeToSaveResponse(this.familiarService.create(this.familiar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFamiliar>>) {
        result.subscribe((res: HttpResponse<IFamiliar>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get familiar() {
        return this._familiar;
    }

    set familiar(familiar: IFamiliar) {
        this._familiar = familiar;
    }
}
