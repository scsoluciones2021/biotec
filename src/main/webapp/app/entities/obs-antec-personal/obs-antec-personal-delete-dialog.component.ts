import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';
import { ObsAntecPersonalService } from './obs-antec-personal.service';

@Component({
    selector: 'jhi-obs-antec-personal-delete-dialog',
    templateUrl: './obs-antec-personal-delete-dialog.component.html'
})
export class ObsAntecPersonalDeleteDialogComponent {
    obsAntecPersonal: IObsAntecPersonal;

    constructor(
        private obsAntecPersonalService: ObsAntecPersonalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.obsAntecPersonalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'obsAntecPersonalListModification',
                content: 'Deleted an obsAntecPersonal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-obs-antec-personal-delete-popup',
    template: ''
})
export class ObsAntecPersonalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obsAntecPersonal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ObsAntecPersonalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.obsAntecPersonal = obsAntecPersonal;
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
