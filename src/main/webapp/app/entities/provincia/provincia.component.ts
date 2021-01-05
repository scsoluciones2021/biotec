import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProvincia } from 'app/shared/model/provincia.model';
import { Principal } from 'app/core';
import { ProvinciaService } from './provincia.service';

@Component({
    selector: 'jhi-provincia',
    templateUrl: './provincia.component.html'
})
export class ProvinciaComponent implements OnInit, OnDestroy {
    provincias: IProvincia[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private provinciaService: ProvinciaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.provinciaService.query().subscribe(
            (res: HttpResponse<IProvincia[]>) => {
                this.provincias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProvincias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProvincia) {
        return item.id;
    }

    registerChangeInProvincias() {
        this.eventSubscriber = this.eventManager.subscribe('provinciaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
