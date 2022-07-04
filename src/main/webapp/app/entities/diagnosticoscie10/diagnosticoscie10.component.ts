import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Diagnosticoscie10Service } from './diagnosticoscie10.service';
import { Diagnosticoscie10DeleteDialogComponent } from './diagnosticoscie10-delete-dialog.component';

@Component({
    selector: 'jhi-diagnosticoscie10',
    templateUrl: './diagnosticoscie10.component.html'
})
export class Diagnosticoscie10Component implements OnInit, OnDestroy {
    diagnosticoscie10s?: IDiagnosticoscie10[];
    eventSubscriber?: Subscription;
    totalItems = 0;
    itemsPerPage = ITEMS_PER_PAGE;
    page!: number;
    predicate!: string;
    ascending!: boolean;
    ngbPaginationPage = 1;

    constructor(
        protected diagnosticoscie10Service: Diagnosticoscie10Service,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal,
        private jhiAlertService: JhiAlertService
    ) {}

    loadPage(page?: number, dontNavigate?: boolean): void {
        //const pageToLoad: number = page || this.page || 1;

        // this.diagnosticoscie10Service
        //     .query({
        //         page: pageToLoad - 1,
        //         size: this.itemsPerPage,
        //         sort: this.sort(),
        //     })
        //     .subscribe(
        //         (res: HttpResponse<IDiagnosticoscie10[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        //         () => this.onError()
        //     );

        this.diagnosticoscie10Service.query().subscribe(
            (res: HttpResponse<IDiagnosticoscie10[]>) => {
                this.diagnosticoscie10s = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit(): void {
        // this.handleNavigation();
        this.loadPage();
        this.registerChangeInDiagnosticoscie10s();
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

    trackId(index: number, item: IDiagnosticoscie10): number {
        // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
        return item.id!;
    }

    registerChangeInDiagnosticoscie10s(): void {
        this.eventSubscriber = this.eventManager.subscribe('diagnosticoscie10ListModification', () => this.loadPage());
    }

    delete(diagnosticoscie10: IDiagnosticoscie10): void {
        const modalRef = this.modalService.open(Diagnosticoscie10DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.diagnosticoscie10 = diagnosticoscie10;
    }

    sort(): string[] {
        const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onSuccess(data: IDiagnosticoscie10[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
        this.totalItems = Number(headers.get('X-Total-Count'));
        this.page = page;
        if (navigate) {
            this.router.navigate(['/diagnosticoscie10'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
                }
            });
        }
        this.diagnosticoscie10s = data || [];
        // this.ngbPaginationPage = this.page;
    }

    // protected onError(): void {
    //     this.ngbPaginationPage = this.page ?? 1;
    // }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
