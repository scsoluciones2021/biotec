import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegimen } from 'app/shared/model/regimen.model';
import { RegimenService } from './regimen.service';

@Component({
    selector: 'jhi-regimen-delete-dialog',
    templateUrl: './regimen-delete-dialog.component.html'
})
export class RegimenDeleteDialogComponent {
    regimen: IRegimen;

    constructor(private regimenService: RegimenService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regimenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'regimenListModification',
                content: 'Deleted an regimen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-regimen-delete-popup',
    template: ''
})
export class RegimenDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RegimenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.regimen = regimen;
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
