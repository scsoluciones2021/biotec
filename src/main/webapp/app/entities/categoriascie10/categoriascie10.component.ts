import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Categoriascie10Service } from './categoriascie10.service';
import { Categoriascie10DeleteDialogComponent } from './categoriascie10-delete-dialog.component';

@Component({
    selector: 'jhi-categoriascie10',
    templateUrl: './categoriascie10.component.html'
})
export class Categoriascie10Component implements OnInit, OnDestroy {
    categoriascie10s?: ICategoriascie10[];
    eventSubscriber?: Subscription;
    totalItems = 0;
    itemsPerPage = ITEMS_PER_PAGE;
    page!: number;
    predicate!: string;
    ascending!: boolean;
    ngbPaginationPage = 1;

    constructor(
        protected categoriascie10Service: Categoriascie10Service,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal,
        private jhiAlertService: JhiAlertService
    ) {}

    loadPage(page?: number, dontNavigate?: boolean): void {
        const pageToLoad: number = page || this.page || 1;

        // this.categoriascie10Service
        //     .query({
        //         page: pageToLoad - 1,
        //         size: this.itemsPerPage,
        //         sort: this.sort(),
        //     })
        //     .subscribe(
        //         (res: HttpResponse<ICategoriascie10[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        //         () => this.onError()
        //     );
        this.categoriascie10Service.query().subscribe(
            (res: HttpResponse<ICategoriascie10[]>) => {
                this.categoriascie10s = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit(): void {
        // this.handleNavigation();
        this.loadPage();
        this.registerChangeInCategoriascie10s();
    }

    // protected handleNavigation(): void {
    //     combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
    //         const page = params.get('page');
    //         const pageNumber = page !== null ? +page : 1;
    //         const sort = (params.get('sort') ?? data['defaultSort']).split(',');
    //         const predicate = sort[0];
    //         const ascending = sort[1] === 'asc';
    //         if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
    //             this.predicate = predicate;
    //             this.ascending = ascending;
    //             this.loadPage(pageNumber, true);
    //         }
    //     }).subscribe();
    // }

    ngOnDestroy(): void {
        if (this.eventSubscriber) {
            this.eventManager.destroy(this.eventSubscriber);
        }
    }

    trackId(index: number, item: ICategoriascie10): number {
        // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
        return item.id!;
    }

    registerChangeInCategoriascie10s(): void {
        this.eventSubscriber = this.eventManager.subscribe('categoriascie10ListModification', () => this.loadPage());
    }

    delete(categoriascie10: ICategoriascie10): void {
        const modalRef = this.modalService.open(Categoriascie10DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.categoriascie10 = categoriascie10;
    }

    sort(): string[] {
        const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onSuccess(data: ICategoriascie10[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
        this.totalItems = Number(headers.get('X-Total-Count'));
        this.page = page;
        if (navigate) {
            this.router.navigate(['/categoriascie10'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
                }
            });
        }
        this.categoriascie10s = data || [];
        // this.ngbPaginationPage = this.page;
    }

    // protected onError(): void {
    //     this.ngbPaginationPage = this.page ?? 1;
    // }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
