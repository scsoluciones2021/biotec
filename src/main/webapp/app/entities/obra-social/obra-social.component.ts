import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IObraSocial } from 'app/shared/model/obra-social.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ObraSocialService } from './obra-social.service';

@Component({
    selector: 'jhi-obra-social',
    templateUrl: './obra-social.component.html'
})
export class ObraSocialComponent implements OnInit, OnDestroy {
    currentAccount: any;
    obraSocials: IObraSocial[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    // Variables para b�squeda
    busquedaNombre: string;
    camposBusqueda: string[];
    // Fin variables para b�squeda

    constructor(
        private obraSocialService: ObraSocialService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        // Para b�squeda
        this.camposBusqueda = [this.busquedaNombre];

        if (this.busquedaNombre) {
            this.obraSocialService
                .searchObraSocial({
                    page: this.page - 1,
                    query: this.camposBusqueda,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IObraSocial[]>) => {
                        console.log(res.body);
                        this.paginateObraSocials(res.body, res.headers);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para b�squeda
        this.obraSocialService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IObraSocial[]>) => this.paginateObraSocials(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/obra-social'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        // Variables de b�squeda
        this.busquedaNombre = '';

        this.router.navigate([
            '/obra-social',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        // Variables de b�squeda
        this.busquedaNombre = '';

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInObraSocials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IObraSocial) {
        return item.id;
    }

    registerChangeInObraSocials() {
        this.eventSubscriber = this.eventManager.subscribe('obraSocialListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateObraSocials(data: IObraSocial[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.obraSocials = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // M�todos para b�squeda
    search(nom) {
        if (!nom) {
            return this.clear();
        }
        this.page = 0;

        this.busquedaNombre = nom;
        this.camposBusqueda = [this.busquedaNombre];

        this.router.navigate([
            '/obra-social',
            {
                search: this.camposBusqueda,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    // Fin m�todos para b�squeda
}
