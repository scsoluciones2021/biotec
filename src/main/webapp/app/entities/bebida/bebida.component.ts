import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBebida } from 'app/shared/model/bebida.model';
import { Principal } from 'app/core';
import { BebidaService } from './bebida.service';

@Component({
    selector: 'jhi-bebida',
    templateUrl: './bebida.component.html'
})
export class BebidaComponent implements OnInit, OnDestroy {
    bebidas: IBebida[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bebidaService: BebidaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bebidaService.query().subscribe(
            (res: HttpResponse<IBebida[]>) => {
                this.bebidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBebidas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBebida) {
        return item.id;
    }

    registerChangeInBebidas() {
        this.eventSubscriber = this.eventManager.subscribe('bebidaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
