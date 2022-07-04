import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';

@Component({
    selector: 'jhi-subgruposcie10-detail',
    templateUrl: './subgruposcie10-detail.component.html'
})
export class Subgruposcie10DetailComponent implements OnInit {
    subgruposcie10: ISubgruposcie10 | null = null;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ subgruposcie10 }) => (this.subgruposcie10 = subgruposcie10));
    }

    previousState(): void {
        window.history.back();
    }
}
