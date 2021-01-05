import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from './codigo-postal.service';

@Component({
    selector: 'jhi-codigo-postal-delete-dialog',
    templateUrl: './codigo-postal-delete-dialog.component.html'
})
export class CodigoPostalDeleteDialogComponent {
    codigoPostal: ICodigoPostal;

    constructor(
        private codigoPostalService: CodigoPostalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.codigoPostalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'codigoPostalListModification',
                content: 'Deleted an codigoPostal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-codigo-postal-delete-popup',
    template: ''
})
export class CodigoPostalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ codigoPostal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CodigoPostalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.codigoPostal = codigoPostal;
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
