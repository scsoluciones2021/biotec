import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { Principal } from 'app/core';
import { EmpresaService } from './empresa.service';

@Component({
    selector: 'jhi-empresa',
    templateUrl: './empresa.component.html'
})
export class EmpresaComponent implements OnInit, OnDestroy {
    empresas: IEmpresa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private empresaService: EmpresaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmpresas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmpresa) {
        return item.id;
    }

    registerChangeInEmpresas() {
        this.eventSubscriber = this.eventManager.subscribe('empresaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
