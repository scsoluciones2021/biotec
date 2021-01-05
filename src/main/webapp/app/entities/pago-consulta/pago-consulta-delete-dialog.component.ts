import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPagoConsulta } from 'app/shared/model/pago-consulta.model';
import { PagoConsultaService } from './pago-consulta.service';

@Component({
    selector: 'jhi-pago-consulta-delete-dialog',
    templateUrl: './pago-consulta-delete-dialog.component.html'
})
export class PagoConsultaDeleteDialogComponent {
    pagoConsulta: IPagoConsulta;

    constructor(
        protected pagoConsultaService: PagoConsultaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pagoConsultaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pagoConsultaListModification',
                content: 'Deleted an pagoConsulta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pago-consulta-delete-popup',
    template: ''
})
export class PagoConsultaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagoConsulta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PagoConsultaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pagoConsulta = pagoConsulta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/pago-consulta', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/pago-consulta', { outlets: { popup: null } }]);
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
