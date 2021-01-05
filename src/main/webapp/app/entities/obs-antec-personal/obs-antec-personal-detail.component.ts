import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';

@Component({
    selector: 'jhi-obs-antec-personal-detail',
    templateUrl: './obs-antec-personal-detail.component.html'
})
export class ObsAntecPersonalDetailComponent implements OnInit {
    obsAntecPersonal: IObsAntecPersonal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obsAntecPersonal }) => {
            this.obsAntecPersonal = obsAntecPersonal;
        });
    }

    previousState() {
        window.history.back();
    }
}
