import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAlergia } from 'app/shared/model/alergia.model';
import { Principal } from 'app/core';
import { AlergiaService } from './alergia.service';

@Component({
    selector: 'jhi-alergia',
    templateUrl: './alergia.component.html'
})
export class AlergiaComponent implements OnInit, OnDestroy {
    alergias: IAlergia[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private alergiaService: AlergiaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.alergiaService.query().subscribe(
            (res: HttpResponse<IAlergia[]>) => {
                this.alergias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAlergias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAlergia) {
        return item.id;
    }

    registerChangeInAlergias() {
        this.eventSubscriber = this.eventManager.subscribe('alergiaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
