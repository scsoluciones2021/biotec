import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBebida } from 'app/shared/model/bebida.model';

@Component({
    selector: 'jhi-bebida-detail',
    templateUrl: './bebida-detail.component.html'
})
export class BebidaDetailComponent implements OnInit {
    bebida: IBebida;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bebida }) => {
            this.bebida = bebida;
        });
    }

    previousState() {
        window.history.back();
    }
}
