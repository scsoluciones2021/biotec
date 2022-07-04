import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Gruposcie10Service } from './gruposcie10.service';
import { Gruposcie10DeleteDialogComponent } from './gruposcie10-delete-dialog.component';

@Component({
    selector: 'jhi-gruposcie10',
    templateUrl: './gruposcie10.component.html'
})
export class Gruposcie10Component implements OnInit, OnDestroy {
    gruposcie10s?: IGruposcie10[];
    eventSubscriber?: Subscription;
    totalItems = 0;
    itemsPerPage = ITEMS_PER_PAGE;
    page!: number;
    predicate!: string;
    ascending!: boolean;
    ngbPaginationPage = 1;

    constructor(
        protected gruposcie10Service: Gruposcie10Service,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal,
        private jhiAlertService: JhiAlertService
    ) {}

    loadPage(page?: number, dontNavigate?: boolean): void {
        const pageToLoad: number = page || this.page || 1;

        // this.gruposcie10Service
        //     .query({
        //         page: pageToLoad - 1,
        //         size: this.itemsPerPage,
        //         sort: this.sort(),
        //     })
        //     .subscribe(
        //         (res: HttpResponse<IGruposcie10[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        //         () => this.onError()
        //     );

        this.gruposcie10Service.query().subscribe(
            (res: HttpResponse<IGruposcie10[]>) => {
                this.gruposcie10s = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit(): void {
        this.loadPage();
        this.registerChangeInGruposcie10s();
    }

    // protected handleNavigation(): void {
    //     combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
    //         const page = params.get('page');
    //         const pageNumber = page !== null ? +page : 1;
    //         // const sort = (params.get('sort') ?? data['defaultSort']).split(',');
    //         // const predicate = sort[0];
    //         // const ascending = sort[1] === 'asc';
    //         // if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
    //         //     this.predicate = predicate;
    //         //     this.ascending = ascending;
    //             this.loadPage(pageNumber, true);
    //         // }
    //     }).subscribe();
    // }

    ngOnDestroy(): void {
        if (this.eventSubscriber) {
            this.eventManager.destroy(this.eventSubscriber);
        }
    }

    trackId(index: number, item: IGruposcie10): number {
        // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
        return item.id!;
    }

    registerChangeInGruposcie10s(): void {
        this.eventSubscriber = this.eventManager.subscribe('gruposcie10ListModification', () => this.loadPage());
    }

    delete(gruposcie10: IGruposcie10): void {
        const modalRef = this.modalService.open(Gruposcie10DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.gruposcie10 = gruposcie10;
    }

    sort(): string[] {
        const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onSuccess(data: IGruposcie10[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
        this.totalItems = Number(headers.get('X-Total-Count'));
        this.page = page;
        if (navigate) {
            this.router.navigate(['/gruposcie10'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
                }
            });
        }
        this.gruposcie10s = data || [];
        // this.ngbPaginationPage = this.page;
    }

    // protected onError(): void {
    //     this.ngbPaginationPage = this.page ?? 1;
    // }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
