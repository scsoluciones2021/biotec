import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgrupadorOS } from 'app/shared/model/agrupador-os.model';
import { AgrupadorOSService } from './agrupador-os.service';

@Component({
    selector: 'jhi-agrupador-os-delete-dialog',
    templateUrl: './agrupador-os-delete-dialog.component.html'
})
export class AgrupadorOSDeleteDialogComponent {
    agrupadorOS: IAgrupadorOS;

    constructor(
        private agrupadorOSService: AgrupadorOSService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agrupadorOSService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'agrupadorOSListModification',
                content: 'Deleted an agrupadorOS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agrupador-os-delete-popup',
    template: ''
})
export class AgrupadorOSDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ agrupadorOS }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AgrupadorOSDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.agrupadorOS = agrupadorOS;
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
