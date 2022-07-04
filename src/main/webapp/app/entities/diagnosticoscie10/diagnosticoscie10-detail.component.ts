import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

@Component({
    selector: 'jhi-diagnosticoscie10-detail',
    templateUrl: './diagnosticoscie10-detail.component.html'
})
export class Diagnosticoscie10DetailComponent implements OnInit {
    diagnosticoscie10: IDiagnosticoscie10 | null = null;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ diagnosticoscie10 }) => (this.diagnosticoscie10 = diagnosticoscie10));
    }

    previousState(): void {
        window.history.back();
    }
}
