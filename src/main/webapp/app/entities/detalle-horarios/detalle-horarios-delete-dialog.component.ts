import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosService } from './detalle-horarios.service';

@Component({
    selector: 'jhi-detalle-horarios-delete-dialog',
    templateUrl: './detalle-horarios-delete-dialog.component.html'
})
export class DetalleHorariosDeleteDialogComponent {
    detalleHorarios: IDetalleHorarios;

    constructor(
        private detalleHorariosService: DetalleHorariosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleHorariosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'detalleHorariosListModification',
                content: 'Deleted an detalleHorarios'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detalle-horarios-delete-popup',
    template: ''
})
export class DetalleHorariosDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleHorarios }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DetalleHorariosDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.detalleHorarios = detalleHorarios;
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
