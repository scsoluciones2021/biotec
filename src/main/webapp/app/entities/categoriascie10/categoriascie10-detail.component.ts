import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';

@Component({
    selector: 'jhi-categoriascie10-detail',
    templateUrl: './categoriascie10-detail.component.html'
})
export class Categoriascie10DetailComponent implements OnInit {
    categoriascie10: ICategoriascie10 | null = null;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ categoriascie10 }) => (this.categoriascie10 = categoriascie10));
    }

    previousState(): void {
        window.history.back();
    }
}
