import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { Principal } from 'app/core';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';

@Component({
    selector: 'jhi-antecedentes-personales',
    templateUrl: './antecedentes-personales.component.html'
})
export class AntecedentesPersonalesComponent implements OnInit, OnDestroy {
    antecedentesPersonales: IAntecedentesPersonales[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private antecedentesPersonalesService: AntecedentesPersonalesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.antecedentesPersonalesService.query().subscribe(
            (res: HttpResponse<IAntecedentesPersonales[]>) => {
                this.antecedentesPersonales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAntecedentesPersonales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAntecedentesPersonales) {
        return item.id;
    }

    registerChangeInAntecedentesPersonales() {
        this.eventSubscriber = this.eventManager.subscribe('antecedentesPersonalesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
