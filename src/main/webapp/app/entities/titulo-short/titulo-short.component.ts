import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITituloShort } from 'app/shared/model/titulo-short.model';
import { Principal } from 'app/core';
import { TituloShortService } from './titulo-short.service';

@Component({
    selector: 'jhi-titulo-short',
    templateUrl: './titulo-short.component.html'
})
export class TituloShortComponent implements OnInit, OnDestroy {
    tituloShorts: ITituloShort[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tituloShortService: TituloShortService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tituloShortService.query().subscribe(
            (res: HttpResponse<ITituloShort[]>) => {
                this.tituloShorts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTituloShorts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITituloShort) {
        return item.id;
    }

    registerChangeInTituloShorts() {
        this.eventSubscriber = this.eventManager.subscribe('tituloShortListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
