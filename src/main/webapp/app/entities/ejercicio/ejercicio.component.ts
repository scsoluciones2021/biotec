import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEjercicio } from 'app/shared/model/ejercicio.model';
import { Principal } from 'app/core';
import { EjercicioService } from './ejercicio.service';

@Component({
    selector: 'jhi-ejercicio',
    templateUrl: './ejercicio.component.html'
})
export class EjercicioComponent implements OnInit, OnDestroy {
    ejercicios: IEjercicio[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ejercicioService: EjercicioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ejercicioService.query().subscribe(
            (res: HttpResponse<IEjercicio[]>) => {
                this.ejercicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEjercicios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEjercicio) {
        return item.id;
    }

    registerChangeInEjercicios() {
        this.eventSubscriber = this.eventManager.subscribe('ejercicioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
