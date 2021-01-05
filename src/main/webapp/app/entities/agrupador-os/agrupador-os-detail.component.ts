import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgrupadorOS } from 'app/shared/model/agrupador-os.model';

@Component({
    selector: 'jhi-agrupador-os-detail',
    templateUrl: './agrupador-os-detail.component.html'
})
export class AgrupadorOSDetailComponent implements OnInit {
    agrupadorOS: IAgrupadorOS;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ agrupadorOS }) => {
            this.agrupadorOS = agrupadorOS;
        });
    }

    previousState() {
        window.history.back();
    }
}
