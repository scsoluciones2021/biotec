import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from './turno.service';

@Component({
    selector: 'jhi-turno-delete-dialog',
    templateUrl: './turno-delete-dialog.component.html'
})
export class TurnoDeleteDialogComponent {
    turno: ITurno;

    constructor(private turnoService: TurnoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    // 1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado"
    confirmDelete(id: number) {
        this.turnoService.cambiarEstado(id, 5).subscribe(response => {
            this.eventManager.broadcast({
                name: 'turnoListModification',
                content: 'Deleted an turno'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-turno-delete-popup',
    template: ''
})
export class TurnoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ turno }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TurnoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.turno = turno;
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
