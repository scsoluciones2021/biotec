import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';

@Component({
    selector: 'jhi-antecedentes-familiares-delete-dialog',
    templateUrl: './antecedentes-familiares-delete-dialog.component.html'
})
export class AntecedentesFamiliaresDeleteDialogComponent {
    antecedentesFamiliares: IAntecedentesFamiliares;

    constructor(
        private antecedentesFamiliaresService: AntecedentesFamiliaresService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.antecedentesFamiliaresService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'antecedentesFamiliaresListModification',
                content: 'Deleted an antecedentesFamiliares'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-antecedentes-familiares-delete-popup',
    template: ''
})
export class AntecedentesFamiliaresDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ antecedentesFamiliares }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AntecedentesFamiliaresDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.antecedentesFamiliares = antecedentesFamiliares;
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
