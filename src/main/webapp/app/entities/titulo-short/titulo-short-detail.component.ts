import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITituloShort } from 'app/shared/model/titulo-short.model';

@Component({
    selector: 'jhi-titulo-short-detail',
    templateUrl: './titulo-short-detail.component.html'
})
export class TituloShortDetailComponent implements OnInit {
    tituloShort: ITituloShort;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tituloShort }) => {
            this.tituloShort = tituloShort;
        });
    }

    previousState() {
        window.history.back();
    }
}
