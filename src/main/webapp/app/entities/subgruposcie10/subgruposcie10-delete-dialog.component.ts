import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';
import { Subgruposcie10Service } from './subgruposcie10.service';

@Component({
    templateUrl: './subgruposcie10-delete-dialog.component.html'
})
export class Subgruposcie10DeleteDialogComponent {
    subgruposcie10?: ISubgruposcie10;

    constructor(
        protected subgruposcie10Service: Subgruposcie10Service,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmDelete(id: number): void {
        this.subgruposcie10Service.delete(id).subscribe(() => {
            this.eventManager.broadcast('subgruposcie10ListModification');
            this.activeModal.close();
        });
    }
}
