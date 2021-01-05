import { Component, OnInit, OnDestroy, ChangeDetectionStrategy, TemplateRef, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse, HttpParams } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IFicha } from 'app/shared/model/ficha.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { FichaService } from './ficha.service';
import { EspecialidadService } from '../especialidad';
import { IEspecialidad } from 'app/shared/model/especialidad.model';
import { ProfesionalService } from '../profesional/profesional.service';
import { IProfesional, IProfesionalTurno } from 'app/shared/model/profesional.model';
import { allowPreviousPlayerStylesMerge } from '@angular/animations/browser/src/util';

@Component({
    selector: 'jhi-ficha',
    templateUrl: './ficha.component.html'
})
export class FichaComponent implements OnInit, OnDestroy {
    currentAccount: any;
    fichas: IFicha[];
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
    especialidades: any[];
    especialidadesSelecc: IEspecialidad[];
    profesional: IProfesional;
    profesionales: any[];
    profesionalesSelecc: IProfesional[];
    roles: any;
    busqueda_apellido: any;
    busqueda_nombre: any;
    busqueda_especialidades: any[];
    busqueda_profesionales: any[];

    constructor(
        private fichaService: FichaService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private especialidadService: EspecialidadService,
        private profesionalService: ProfesionalService
    ) // private modal: NgbModal
    {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.fichaService
            .busquedaConFiltros({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                query: [this.busqueda_apellido, this.busqueda_nombre, this.busqueda_profesionales, this.busqueda_especialidades]
            })
            .subscribe(
                (res: HttpResponse<IFicha[]>) => {
                    this.paginateFichas(res.body, res.headers);
                },
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
        this.router.navigate(['/ficha'], {
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
        this.router.navigate([
            '/ficha',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.busqueda_especialidades = new Array();
        this.busqueda_profesionales = new Array();

        this.principal.hasAuthority('ROLE_MEDICO').then(userRole => {
            if (userRole) {
                this.principal.identity().then(account => {
                    this.currentAccount = account;
                    this.roles = this.currentAccount.authorities;
                    this.profesionalService.buscarPorUsuario(this.currentAccount.id).subscribe(
                        (res: HttpResponse<IProfesional>) => {
                            this.profesional = res.body;
                            this.especialidadesSelecc = new Array();
                            this.especialidades = this.profesional.especialidads;
                            this.especialidadesSelecc.forEach(especialidad => {
                                this.busqueda_especialidades.push(especialidad.id);
                            });
                            this.busqueda_profesionales.push(this.profesional.id);
                            this.loadAll();
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                });
            } else {
                this.especialidadService.query().subscribe(
                    (res: HttpResponse<IEspecialidad[]>) => {
                        this.especialidades = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
                this.profesionalService.buscarTodosArray().subscribe(
                    (res: HttpResponse<IProfesionalTurno[]>) => {
                        this.profesionales = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
                this.loadAll();
            }
        });

        this.registerChangeInFichas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFicha) {
        return item.id;
    }

    registerChangeInFichas() {
        this.eventSubscriber = this.eventManager.subscribe('fichaListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateFichas(data: IFicha[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.fichas = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    public search(prApe, prNom) {
        if (
            !prApe &&
            !prNom &&
            this.especialidadesSelecc != undefined &&
            this.especialidadesSelecc == [] &&
            this.profesionalesSelecc != undefined &&
            this.profesionalesSelecc == []
        ) {
            return this.clear();
        }
        if (this.especialidadesSelecc != undefined) {
            this.busqueda_especialidades = new Array();
            this.especialidadesSelecc.forEach(especialidad => {
                this.busqueda_especialidades.push(especialidad.id);
            });
        }
        if (this.profesionalesSelecc != undefined) {
            this.busqueda_profesionales = new Array();
            this.profesionalesSelecc.forEach(profesionales => {
                this.busqueda_profesionales.push(profesionales.id);
            });
        }
        this.page = 0;
        this.busqueda_apellido = prApe;
        this.busqueda_nombre = prNom;
        console.log(this.busqueda_especialidades);
        console.log(this.busqueda_apellido);
        console.log(this.busqueda_nombre);
        console.log(this.busqueda_profesionales);

        this.loadAll();
    }
}
