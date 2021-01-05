import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamiliar } from 'app/shared/model/familiar.model';

@Component({
    selector: 'jhi-familiar-detail',
    templateUrl: './familiar-detail.component.html'
})
export class FamiliarDetailComponent implements OnInit {
    familiar: IFamiliar;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familiar }) => {
            this.familiar = familiar;
        });
    }

    previousState() {
        window.history.back();
    }
}
