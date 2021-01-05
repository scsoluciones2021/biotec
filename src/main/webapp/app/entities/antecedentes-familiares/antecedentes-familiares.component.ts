import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { Principal } from 'app/core';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';

@Component({
    selector: 'jhi-antecedentes-familiares',
    templateUrl: './antecedentes-familiares.component.html'
})
export class AntecedentesFamiliaresComponent implements OnInit, OnDestroy {
    antecedentesFamiliares: IAntecedentesFamiliares[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private antecedentesFamiliaresService: AntecedentesFamiliaresService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.antecedentesFamiliaresService.query().subscribe(
            (res: HttpResponse<IAntecedentesFamiliares[]>) => {
                this.antecedentesFamiliares = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAntecedentesFamiliares();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAntecedentesFamiliares) {
        return item.id;
    }

    registerChangeInAntecedentesFamiliares() {
        this.eventSubscriber = this.eventManager.subscribe('antecedentesFamiliaresListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
