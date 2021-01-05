import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlergia } from 'app/shared/model/alergia.model';

@Component({
    selector: 'jhi-alergia-detail',
    templateUrl: './alergia-detail.component.html'
})
export class AlergiaDetailComponent implements OnInit {
    alergia: IAlergia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alergia }) => {
            this.alergia = alergia;
        });
    }

    previousState() {
        window.history.back();
    }
}
