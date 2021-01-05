import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamiliar } from 'app/shared/model/familiar.model';
import { FamiliarService } from './familiar.service';

@Component({
    selector: 'jhi-familiar-delete-dialog',
    templateUrl: './familiar-delete-dialog.component.html'
})
export class FamiliarDeleteDialogComponent {
    familiar: IFamiliar;

    constructor(private familiarService: FamiliarService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.familiarService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'familiarListModification',
                content: 'Deleted an familiar'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-familiar-delete-popup',
    template: ''
})
export class FamiliarDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familiar }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FamiliarDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.familiar = familiar;
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
