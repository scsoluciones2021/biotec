import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils, JhiLanguageService } from 'ng-jhipster';

import { IProfesional } from 'app/shared/model/profesional.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProfesionalService } from './profesional.service';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

@Component({
    selector: 'jhi-profesional',
    templateUrl: './profesional.component.html'
})
export class ProfesionalComponent implements OnInit, OnDestroy {
    currentAccount: any;
    profesionals: IProfesional[];
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

    // Variables para búsqueda
    currentSearch: string;
    busquedaA: string;
    busquedaN: string;
    // Fin variables para búsqueda

    constructor(
        private profesionalService: ProfesionalService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
        private eventManager: JhiEventManager,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });

        // Seteo de variable de búsqueda
        this.busquedaA =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchA']
                ? this.activatedRoute.snapshot.params['searchA']
                : '';
        this.busquedaN =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchN']
                ? this.activatedRoute.snapshot.params['searchN']
                : '';
    }

    loadAll() {
        // Para búsqueda
        const camposBusqueda = [this.busquedaA, this.busquedaN];

        if (this.busquedaA || this.busquedaN) {
            this.profesionalService
                .searchProfesional({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IProfesional[]>) => this.paginateProfesionals(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para búsqueda

        this.profesionalService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProfesional[]>) => this.paginateProfesionals(res.body, res.headers),
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
        this.router.navigate(['/profesional'], {
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

        // Variable de búsqueda
        this.busquedaA = '';
        this.busquedaN = '';

        this.router.navigate([
            '/profesional',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        // Variable de búsqueda
        this.busquedaA = '';
        this.busquedaN = '';

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProfesionals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProfesional) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInProfesionals() {
        this.eventSubscriber = this.eventManager.subscribe('profesionalListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateProfesionals(data: IProfesional[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.profesionals = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // Métodos para búsqueda
    search(prApe, prNom) {
        if (!prApe && !prNom) {
            return this.clear();
        }
        this.page = 0;
        this.busquedaA = prApe;
        this.busquedaN = prNom;
        this.currentSearch = prApe + ', ' + prNom;
        this.router.navigate([
            '/profesional',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    // Fin métodos para búsqueda
}
