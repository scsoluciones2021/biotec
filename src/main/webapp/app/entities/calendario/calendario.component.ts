import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICalendario } from 'app/shared/model/calendario.model';
import { Principal } from 'app/core';
import { CalendarioService } from './calendario.service';

@Component({
    selector: 'jhi-calendario',
    templateUrl: './calendario.component.html'
})
export class CalendarioComponent implements OnInit, OnDestroy {
    calendarios: ICalendario[];
    currentAccount: any;
    eventSubscriber: Subscription;
    events: any[];
    constructor(
        private calendarioService: CalendarioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.calendarioService.query().subscribe(
            (res: HttpResponse<ICalendario[]>) => {
                this.calendarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        /*this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCalendarios();*/
        this.events = [
            {
                "title": "All Day Event",
                "start": "2016-01-01"
            },
            {
                "title": "Long Event",
                "start": "2016-01-07",
                "end": "2016-01-10"
            },
            {
                "title": "Repeating Event",
                "start": "2016-01-09T16:00:00"
            },
            {
                "title": "Repeating Event",
                "start": "2016-01-16T16:00:00"
            },
            {
                "title": "Conference",
                "start": "2016-01-11",
                "end": "2016-01-13"
            }
        ];
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICalendario) {
        return item.id;
    }

    registerChangeInCalendarios() {
        this.eventSubscriber = this.eventManager.subscribe('calendarioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
