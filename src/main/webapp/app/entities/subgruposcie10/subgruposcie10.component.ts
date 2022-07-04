import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Subgruposcie10Service } from './subgruposcie10.service';
import { Subgruposcie10DeleteDialogComponent } from './subgruposcie10-delete-dialog.component';

@Component({
    selector: 'jhi-subgruposcie10',
    templateUrl: './subgruposcie10.component.html'
})
export class Subgruposcie10Component implements OnInit, OnDestroy {
    subgruposcie10s?: ISubgruposcie10[];
    eventSubscriber?: Subscription;
    totalItems = 0;
    itemsPerPage = ITEMS_PER_PAGE;
    page!: number;
    predicate!: string;
    ascending!: boolean;
    ngbPaginationPage = 1;

    constructor(
        protected subgruposcie10Service: Subgruposcie10Service,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal,
        private jhiAlertService: JhiAlertService
    ) {}

    loadPage(page?: number, dontNavigate?: boolean): void {
        const pageToLoad: number = page || this.page || 1;

        // this.subgruposcie10Service
        //     .query({
        //         page: pageToLoad - 1,
        //         size: this.itemsPerPage,
        //         sort: this.sort(),
        //     })
        //     .subscribe(
        //         (res: HttpResponse<ISubgruposcie10[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        //         () => this.onError()
        //     );
        this.subgruposcie10Service.query().subscribe(
            (res: HttpResponse<ISubgruposcie10[]>) => {
                this.subgruposcie10s = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit(): void {
        // this.handleNavigation();
        this.loadPage();
        this.registerChangeInSubgruposcie10s();
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

    trackId(index: number, item: ISubgruposcie10): number {
        // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
        return item.id!;
    }

    registerChangeInSubgruposcie10s(): void {
        this.eventSubscriber = this.eventManager.subscribe('subgruposcie10ListModification', () => this.loadPage());
    }

    delete(subgruposcie10: ISubgruposcie10): void {
        const modalRef = this.modalService.open(Subgruposcie10DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.subgruposcie10 = subgruposcie10;
    }

    sort(): string[] {
        const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onSuccess(data: ISubgruposcie10[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
        this.totalItems = Number(headers.get('X-Total-Count'));
        this.page = page;
        if (navigate) {
            this.router.navigate(['/subgruposcie10'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
                }
            });
        }
        this.subgruposcie10s = data || [];
        // this.ngbPaginationPage = this.page;
    }

    // protected onError(): void {
    //     this.ngbPaginationPage = this.page ?? 1;
    // }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
