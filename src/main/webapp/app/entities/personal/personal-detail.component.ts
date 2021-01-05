import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonal } from 'app/shared/model/personal.model';

@Component({
    selector: 'jhi-personal-detail',
    templateUrl: './personal-detail.component.html'
})
export class PersonalDetailComponent implements OnInit {
    personal: IPersonal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ personal }) => {
            this.personal = personal;
        });
    }

    previousState() {
        window.history.back();
    }
}
