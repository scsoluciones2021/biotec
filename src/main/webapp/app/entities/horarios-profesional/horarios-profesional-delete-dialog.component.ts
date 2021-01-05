import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';
import { HorariosProfesionalService } from './horarios-profesional.service';

@Component({
    selector: 'jhi-horarios-profesional-delete-dialog',
    templateUrl: './horarios-profesional-delete-dialog.component.html'
})
export class HorariosProfesionalDeleteDialogComponent {
    horariosProfesional: IHorariosProfesional;

    constructor(
        private horariosProfesionalService: HorariosProfesionalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.horariosProfesionalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'horariosProfesionalListModification',
                content: 'Deleted an horariosProfesional'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-horarios-profesional-delete-popup',
    template: ''
})
export class HorariosProfesionalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horariosProfesional }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HorariosProfesionalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.horariosProfesional = horariosProfesional;
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
