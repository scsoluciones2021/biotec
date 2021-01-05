import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFamiliar } from 'app/shared/model/familiar.model';
import { Principal } from 'app/core';
import { FamiliarService } from './familiar.service';

@Component({
    selector: 'jhi-familiar',
    templateUrl: './familiar.component.html'
})
export class FamiliarComponent implements OnInit, OnDestroy {
    familiars: IFamiliar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private familiarService: FamiliarService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.familiarService.query().subscribe(
            (res: HttpResponse<IFamiliar[]>) => {
                this.familiars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFamiliars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFamiliar) {
        return item.id;
    }

    registerChangeInFamiliars() {
        this.eventSubscriber = this.eventManager.subscribe('familiarListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
