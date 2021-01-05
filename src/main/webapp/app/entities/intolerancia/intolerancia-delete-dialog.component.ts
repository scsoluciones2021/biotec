import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { IntoleranciaService } from './intolerancia.service';

@Component({
    selector: 'jhi-intolerancia-delete-dialog',
    templateUrl: './intolerancia-delete-dialog.component.html'
})
export class IntoleranciaDeleteDialogComponent {
    intolerancia: IIntolerancia;

    constructor(
        private intoleranciaService: IntoleranciaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.intoleranciaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'intoleranciaListModification',
                content: 'Deleted an intolerancia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-intolerancia-delete-popup',
    template: ''
})
export class IntoleranciaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ intolerancia }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IntoleranciaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.intolerancia = intolerancia;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
