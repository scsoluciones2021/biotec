import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';

@Component({
    selector: 'jhi-obs-antec-familiar-detail',
    templateUrl: './obs-antec-familiar-detail.component.html'
})
export class ObsAntecFamiliarDetailComponent implements OnInit {
    obsAntecFamiliar: IObsAntecFamiliar;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ obsAntecFamiliar }) => {
            this.obsAntecFamiliar = obsAntecFamiliar;
        });
    }

    previousState() {
        window.history.back();
    }
}
