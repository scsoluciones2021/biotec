import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBloqueos } from 'app/shared/model/bloqueos.model';

@Component({
    selector: 'jhi-bloqueos-detail',
    templateUrl: './bloqueos-detail.component.html'
})
export class BloqueosDetailComponent implements OnInit {
    bloqueos: IBloqueos;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bloqueos }) => {
            this.bloqueos = bloqueos;
        });
    }

    previousState() {
        window.history.back();
    }
}
