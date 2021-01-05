import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConstantes } from 'app/shared/model/constantes.model';

@Component({
    selector: 'jhi-constantes-detail',
    templateUrl: './constantes-detail.component.html'
})
export class ConstantesDetailComponent implements OnInit {
    constantes: IConstantes;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ constantes }) => {
            this.constantes = constantes;
        });
    }

    previousState() {
        window.history.back();
    }
}
