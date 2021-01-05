import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';

@Component({
    selector: 'jhi-antecedentes-personales-delete-dialog',
    templateUrl: './antecedentes-personales-delete-dialog.component.html'
})
export class AntecedentesPersonalesDeleteDialogComponent {
    antecedentesPersonales: IAntecedentesPersonales;

    constructor(
        private antecedentesPersonalesService: AntecedentesPersonalesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.antecedentesPersonalesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'antecedentesPersonalesListModification',
                content: 'Deleted an antecedentesPersonales'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-antecedentes-personales-delete-popup',
    template: ''
})
export class AntecedentesPersonalesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ antecedentesPersonales }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AntecedentesPersonalesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.antecedentesPersonales = antecedentesPersonales;
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
