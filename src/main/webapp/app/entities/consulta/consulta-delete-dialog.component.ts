import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from './consulta.service';

@Component({
    selector: 'jhi-consulta-delete-dialog',
    templateUrl: './consulta-delete-dialog.component.html'
})
export class ConsultaDeleteDialogComponent {
    consulta: IConsulta;

    constructor(private consultaService: ConsultaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consultaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'consultaListModification',
                content: 'Deleted an consulta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consulta-delete-popup',
    template: ''
})
export class ConsultaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ consulta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConsultaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.consulta = consulta;
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
