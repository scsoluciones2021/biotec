import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEjercicio } from 'app/shared/model/ejercicio.model';
import { EjercicioService } from './ejercicio.service';

@Component({
    selector: 'jhi-ejercicio-delete-dialog',
    templateUrl: './ejercicio-delete-dialog.component.html'
})
export class EjercicioDeleteDialogComponent {
    ejercicio: IEjercicio;

    constructor(private ejercicioService: EjercicioService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ejercicioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ejercicioListModification',
                content: 'Deleted an ejercicio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ejercicio-delete-popup',
    template: ''
})
export class EjercicioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ejercicio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EjercicioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ejercicio = ejercicio;
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
