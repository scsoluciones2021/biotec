import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegimen } from 'app/shared/model/regimen.model';

@Component({
    selector: 'jhi-regimen-detail',
    templateUrl: './regimen-detail.component.html'
})
export class RegimenDetailComponent implements OnInit {
    regimen: IRegimen;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regimen }) => {
            this.regimen = regimen;
        });
    }

    previousState() {
        window.history.back();
    }
}
