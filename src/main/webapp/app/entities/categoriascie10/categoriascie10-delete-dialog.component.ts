import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';
import { Categoriascie10Service } from './categoriascie10.service';

@Component({
    templateUrl: './categoriascie10-delete-dialog.component.html'
})
export class Categoriascie10DeleteDialogComponent {
    categoriascie10?: ICategoriascie10;

    constructor(
        protected categoriascie10Service: Categoriascie10Service,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmDelete(id: number): void {
        this.categoriascie10Service.delete(id).subscribe(() => {
            this.eventManager.broadcast('categoriascie10ListModification');
            this.activeModal.close();
        });
    }
}
