import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';
import { IUser, UserService } from 'app/core';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
    selector: 'jhi-personal-update',
    templateUrl: './personal-update.component.html'
})
export class PersonalUpdateComponent implements OnInit {
    private _personal: IPersonal;
    isSaving: boolean;

    users: IUser[];

    empresas: IEmpresa[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private personalService: PersonalService,
        private userService: UserService,
        private empresaService: EmpresaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personal }) => {
            this.personal = personal;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personal.id !== undefined) {
            this.subscribeToSaveResponse(this.personalService.update(this.personal));
        } else {
            this.subscribeToSaveResponse(this.personalService.create(this.personal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPersonal>>) {
        result.subscribe((res: HttpResponse<IPersonal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
    get personal() {
        return this._personal;
    }

    set personal(personal: IPersonal) {
        this._personal = personal;
    }
}
