import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';
import { Gruposcie10Service } from './gruposcie10.service';

@Component({
    templateUrl: './gruposcie10-delete-dialog.component.html'
})
export class Gruposcie10DeleteDialogComponent {
    gruposcie10?: IGruposcie10;

    constructor(
        protected gruposcie10Service: Gruposcie10Service,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmDelete(id: number): void {
        this.gruposcie10Service.delete(id).subscribe(() => {
            this.eventManager.broadcast('gruposcie10ListModification');
            this.activeModal.close();
        });
    }
}
