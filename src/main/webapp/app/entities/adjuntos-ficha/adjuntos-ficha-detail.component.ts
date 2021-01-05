import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';

@Component({
    selector: 'jhi-adjuntos-ficha-detail',
    templateUrl: './adjuntos-ficha-detail.component.html'
})
export class AdjuntosFichaDetailComponent implements OnInit {
    adjuntos_ficha: IAdjuntosFicha;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adjuntos_ficha }) => {
            this.adjuntos_ficha = adjuntos_ficha;
        });
    }

    previousState() {
        window.history.back();
    }
}
