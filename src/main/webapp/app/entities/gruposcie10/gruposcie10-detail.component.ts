import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';

@Component({
    selector: 'jhi-gruposcie10-detail',
    templateUrl: './gruposcie10-detail.component.html'
})
export class Gruposcie10DetailComponent implements OnInit {
    gruposcie10: IGruposcie10 | null = null;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ gruposcie10 }) => (this.gruposcie10 = gruposcie10));
    }

    previousState(): void {
        window.history.back();
    }
}
