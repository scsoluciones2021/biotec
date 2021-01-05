import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';
import { ObsAntecFamiliarService } from './obs-antec-familiar.service';

@Component({
    selector: 'jhi-obs-antec-familiar-delete-dialog',
    templateUrl: './obs-antec-familiar-delete-dialog.component.html'
})
export class ObsAntecFamiliarDeleteDialogComponent {
    obsAntecFamiliar: IObsAntecFamiliar;

    constructor(
        private obsAntecFamiliarService: ObsAntecFamiliarService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.obsAntecFamiliarService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'obsAntecFamiliarListModification',
                content: 'Deleted an obsAntecFamiliar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-obs-antec-familiar-delete-popup',
    template: ''
})
export class ObsAntecFamiliarDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obsAntecFamiliar }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ObsAntecFamiliarDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.obsAntecFamiliar = obsAntecFamiliar;
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
