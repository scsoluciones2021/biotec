import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConstantes } from 'app/shared/model/constantes.model';
import { ConstantesService } from './constantes.service';

@Component({
    selector: 'jhi-constantes-delete-dialog',
    templateUrl: './constantes-delete-dialog.component.html'
})
export class ConstantesDeleteDialogComponent {
    constantes: IConstantes;

    constructor(private constantesService: ConstantesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.constantesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'constantesListModification',
                content: 'Deleted an constantes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-constantes-delete-popup',
    template: ''
})
export class ConstantesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ constantes }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConstantesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.constantes = constantes;
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
