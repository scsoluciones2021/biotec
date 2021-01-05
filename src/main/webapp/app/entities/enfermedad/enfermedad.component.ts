import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEnfermedad } from 'app/shared/model/enfermedad.model';
import { Principal } from 'app/core';
import { EnfermedadService } from './enfermedad.service';

@Component({
    selector: 'jhi-enfermedad',
    templateUrl: './enfermedad.component.html'
})
export class EnfermedadComponent implements OnInit, OnDestroy {
    enfermedads: IEnfermedad[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private enfermedadService: EnfermedadService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.enfermedadService.query().subscribe(
            (res: HttpResponse<IEnfermedad[]>) => {
                this.enfermedads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEnfermedads();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEnfermedad) {
        return item.id;
    }

    registerChangeInEnfermedads() {
        this.eventSubscriber = this.eventManager.subscribe('enfermedadListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
