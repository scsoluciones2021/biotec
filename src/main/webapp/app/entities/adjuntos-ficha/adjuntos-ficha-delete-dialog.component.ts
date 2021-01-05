import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';
import { AdjuntosFichaService } from './adjuntos-ficha.service';

@Component({
    selector: 'jhi-adjuntos-ficha-delete-dialog',
    templateUrl: './adjuntos-ficha-delete-dialog.component.html'
})
export class AdjuntosFichaDeleteDialogComponent {
    adjuntos_ficha: IAdjuntosFicha;

    constructor(
        private adjuntos_fichaService: AdjuntosFichaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adjuntos_fichaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'adjuntos_fichaListModification',
                content: 'Deleted an adjuntos_ficha'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adjuntos-ficha-delete-popup',
    template: ''
})
export class AdjuntosFichaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adjuntos_ficha }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdjuntosFichaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.adjuntos_ficha = adjuntos_ficha;
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
