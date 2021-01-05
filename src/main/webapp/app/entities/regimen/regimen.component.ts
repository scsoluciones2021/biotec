import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegimen } from 'app/shared/model/regimen.model';
import { Principal } from 'app/core';
import { RegimenService } from './regimen.service';

@Component({
    selector: 'jhi-regimen',
    templateUrl: './regimen.component.html'
})
export class RegimenComponent implements OnInit, OnDestroy {
    regimen: IRegimen[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private regimenService: RegimenService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.regimenService.query().subscribe(
            (res: HttpResponse<IRegimen[]>) => {
                this.regimen = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRegimen();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegimen) {
        return item.id;
    }

    registerChangeInRegimen() {
        this.eventSubscriber = this.eventManager.subscribe('regimenListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
