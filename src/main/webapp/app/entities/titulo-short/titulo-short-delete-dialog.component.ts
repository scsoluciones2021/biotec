import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITituloShort } from 'app/shared/model/titulo-short.model';
import { TituloShortService } from './titulo-short.service';

@Component({
    selector: 'jhi-titulo-short-delete-dialog',
    templateUrl: './titulo-short-delete-dialog.component.html'
})
export class TituloShortDeleteDialogComponent {
    tituloShort: ITituloShort;

    constructor(
        private tituloShortService: TituloShortService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tituloShortService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tituloShortListModification',
                content: 'Deleted an tituloShort'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-titulo-short-delete-popup',
    template: ''
})
export class TituloShortDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tituloShort }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TituloShortDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tituloShort = tituloShort;
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
