import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnfermedad } from 'app/shared/model/enfermedad.model';
import { EnfermedadService } from './enfermedad.service';

@Component({
    selector: 'jhi-enfermedad-delete-dialog',
    templateUrl: './enfermedad-delete-dialog.component.html'
})
export class EnfermedadDeleteDialogComponent {
    enfermedad: IEnfermedad;

    constructor(private enfermedadService: EnfermedadService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enfermedadService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enfermedadListModification',
                content: 'Deleted an enfermedad'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enfermedad-delete-popup',
    template: ''
})
export class EnfermedadDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enfermedad }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnfermedadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.enfermedad = enfermedad;
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
