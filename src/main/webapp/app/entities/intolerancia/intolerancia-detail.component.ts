import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntolerancia } from 'app/shared/model/intolerancia.model';

@Component({
    selector: 'jhi-intolerancia-detail',
    templateUrl: './intolerancia-detail.component.html'
})
export class IntoleranciaDetailComponent implements OnInit {
    intolerancia: IIntolerancia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ intolerancia }) => {
            this.intolerancia = intolerancia;
        });
    }

    previousState() {
        window.history.back();
    }
}
