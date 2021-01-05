import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonal } from 'app/shared/model/personal.model';
import { Principal } from 'app/core';
import { PersonalService } from './personal.service';

@Component({
    selector: 'jhi-personal',
    templateUrl: './personal.component.html'
})
export class PersonalComponent implements OnInit, OnDestroy {
    personals: IPersonal[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personalService: PersonalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.personalService.query().subscribe(
            (res: HttpResponse<IPersonal[]>) => {
                this.personals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPersonal) {
        return item.id;
    }

    registerChangeInPersonals() {
        this.eventSubscriber = this.eventManager.subscribe('personalListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
