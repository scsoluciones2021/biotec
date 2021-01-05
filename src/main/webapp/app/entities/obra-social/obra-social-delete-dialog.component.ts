import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';

@Component({
    selector: 'jhi-obra-social-delete-dialog',
    templateUrl: './obra-social-delete-dialog.component.html'
})
export class ObraSocialDeleteDialogComponent {
    obraSocial: IObraSocial;

    constructor(private obraSocialService: ObraSocialService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.obraSocialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'obraSocialListModification',
                content: 'Deleted an obraSocial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-obra-social-delete-popup',
    template: ''
})
export class ObraSocialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obraSocial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ObraSocialDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.obraSocial = obraSocial;
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
