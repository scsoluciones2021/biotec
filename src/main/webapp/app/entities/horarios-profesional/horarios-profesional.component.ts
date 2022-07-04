import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { HorariosProfesionalService } from './horarios-profesional.service';

@Component({
    selector: 'jhi-horarios-profesional',
    templateUrl: './horarios-profesional.component.html'
})
export class HorariosProfesionalComponent implements OnInit, OnDestroy {
    currentAccount: any;
    horariosProfesionals: IHorariosProfesional[];
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
    busquedaNombre: string;
    busquedaEspecialidad: string;
    camposBusqueda: string[];
    // Fin variables para búsqueda

    constructor(
        private horariosProfesionalService: HorariosProfesionalService,
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
        // Para búsqueda
        this.camposBusqueda = [this.busquedaNombre, this.busquedaEspecialidad];

        if (this.busquedaNombre || this.busquedaEspecialidad) {
            this.horariosProfesionalService
                .searchHorarios({
                    page: this.page - 1,
                    query: this.camposBusqueda,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IHorariosProfesional[]>) => {
                        console.log(res.body);
                        this.paginateHorariosProfesionals(res.body, res.headers);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para búsqueda

        this.horariosProfesionalService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IHorariosProfesional[]>) => this.paginateHorariosProfesionals(res.body, res.headers),
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
        this.router.navigate(['/horarios-profesional'], {
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
        // Variables de búsqueda
        this.busquedaNombre = '';
        this.busquedaEspecialidad = '';

        this.router.navigate([
            '/horarios-profesional',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        // Variables de búsqueda
        this.busquedaNombre = '';
        this.busquedaEspecialidad = '';

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHorariosProfesionals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHorariosProfesional) {
        return item.id;
    }

    registerChangeInHorariosProfesionals() {
        this.eventSubscriber = this.eventManager.subscribe('horariosProfesionalListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateHorariosProfesionals(data: IHorariosProfesional[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.horariosProfesionals = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // Métodos para búsqueda
    search(nom, esp) {
        if (!nom && !esp) {
            return this.clear();
        }
        this.page = 0;

        this.busquedaNombre = nom;
        this.busquedaEspecialidad = esp;
        this.camposBusqueda = [this.busquedaNombre, this.busquedaEspecialidad];

        this.router.navigate([
            '/horarios-profesional',
            {
                search: this.camposBusqueda,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    // Fin métodos para búsqueda
}
