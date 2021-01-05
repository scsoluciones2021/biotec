import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from './profesional.service';

@Component({
    selector: 'jhi-profesional-delete-dialog',
    templateUrl: './profesional-delete-dialog.component.html'
})
export class ProfesionalDeleteDialogComponent {
    profesional: IProfesional;

    constructor(
        private profesionalService: ProfesionalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profesionalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'profesionalListModification',
                content: 'Deleted an profesional'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profesional-delete-popup',
    template: ''
})
export class ProfesionalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profesional }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProfesionalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.profesional = profesional;
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
