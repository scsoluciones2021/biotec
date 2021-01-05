import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { Principal } from 'app/core';
import { IntoleranciaService } from './intolerancia.service';

@Component({
    selector: 'jhi-intolerancia',
    templateUrl: './intolerancia.component.html'
})
export class IntoleranciaComponent implements OnInit, OnDestroy {
    intolerancias: IIntolerancia[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private intoleranciaService: IntoleranciaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.intoleranciaService.query().subscribe(
            (res: HttpResponse<IIntolerancia[]>) => {
                this.intolerancias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIntolerancias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIntolerancia) {
        return item.id;
    }

    registerChangeInIntolerancias() {
        this.eventSubscriber = this.eventManager.subscribe('intoleranciaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
