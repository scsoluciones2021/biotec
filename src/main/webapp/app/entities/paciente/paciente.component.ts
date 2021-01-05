import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PacienteService } from './paciente.service';

@Component({
    selector: 'jhi-paciente',
    templateUrl: './paciente.component.html'
})
export class PacienteComponent implements OnInit, OnDestroy {
    currentAccount: any;
    pacientes: IPaciente[];
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
     busquedaApellido: string;
     busquedaNombre: string;
     busquedaDNI: string;
     camposBusqueda: string[];
     // Fin variables para búsqueda

    constructor(
        private pacienteService: PacienteService,
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

        // Seteo de variables de búsqueda
        // Ver de usar el camposBusqueda
        this.busquedaApellido =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchA']
                ? this.activatedRoute.snapshot.params['searchA']
                : '';
        this.busquedaNombre =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchN']
                ? this.activatedRoute.snapshot.params['searchN']
                : '';
        this.busquedaDNI =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchD']
                ? this.activatedRoute.snapshot.params['searchD']
                : '';
        // Fin seteo variables de búsqueda
    }

    loadAll() {
        // Para búsqueda        
        this.camposBusqueda = [this.busquedaApellido, this.busquedaNombre, this.busquedaDNI];
        
        if (this.busquedaApellido || this.busquedaNombre || this.busquedaDNI) {
            this.pacienteService
                .searchPaciente({
                    page: this.page - 1,
                    query: this.camposBusqueda,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IPaciente[]>) => {
                        console.log(res.body);
                        this.paginatePacientes(res.body, res.headers)
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para búsqueda

        this.pacienteService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IPaciente[]>) => this.paginatePacientes(res.body, res.headers),
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
        this.router.navigate(['/paciente'], {
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
        this.busquedaApellido = '';
        this.busquedaNombre = '';
        this.busquedaDNI = '';

        this.router.navigate([
            '/paciente',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        // Variables de búsqueda
        this.busquedaApellido = '';
        this.busquedaNombre = '';
        this.busquedaDNI = '';

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPacientes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPaciente) {
        return item.id;
    }

    registerChangeInPacientes() {
        this.eventSubscriber = this.eventManager.subscribe('pacienteListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginatePacientes(data: IPaciente[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.pacientes = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // Métodos para búsqueda
    search(ape, nom, dni) {
        if (!ape && !nom && !dni) {
            return this.clear();
        }
        this.page = 0;
    
        this.busquedaApellido = ape;
        this.busquedaNombre = nom;
        this.busquedaDNI = dni;
        this.camposBusqueda = [this.busquedaApellido, this.busquedaNombre, this.busquedaDNI];

        this.router.navigate([
            '/paciente',
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
