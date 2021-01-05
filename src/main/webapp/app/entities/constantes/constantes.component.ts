import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConstantes } from 'app/shared/model/constantes.model';
import { Principal } from 'app/core';
import { ConstantesService } from './constantes.service';

@Component({
    selector: 'jhi-constantes',
    templateUrl: './constantes.component.html'
})
export class ConstantesComponent implements OnInit, OnDestroy {
    constantes: IConstantes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private constantesService: ConstantesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.constantesService.query().subscribe(
            (res: HttpResponse<IConstantes[]>) => {
                this.constantes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConstantes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConstantes) {
        return item.id;
    }

    registerChangeInConstantes() {
        this.eventSubscriber = this.eventManager.subscribe('constantesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
