import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';

@Component({
    selector: 'jhi-codigo-postal-detail',
    templateUrl: './codigo-postal-detail.component.html'
})
export class CodigoPostalDetailComponent implements OnInit {
    codigoPostal: ICodigoPostal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ codigoPostal }) => {
            this.codigoPostal = codigoPostal;
        });
    }

    previousState() {
        window.history.back();
    }
}
