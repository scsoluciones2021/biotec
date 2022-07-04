import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';
import { Diagnosticoscie10Service } from './diagnosticoscie10.service';

@Component({
    templateUrl: './diagnosticoscie10-delete-dialog.component.html'
})
export class Diagnosticoscie10DeleteDialogComponent {
    diagnosticoscie10?: IDiagnosticoscie10;

    constructor(
        protected diagnosticoscie10Service: Diagnosticoscie10Service,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    cancel(): void {
        this.activeModal.dismiss();
    }

    confirmDelete(id: number): void {
        this.diagnosticoscie10Service.delete(id).subscribe(() => {
            this.eventManager.broadcast('diagnosticoscie10ListModification');
            this.activeModal.close();
        });
    }
}
