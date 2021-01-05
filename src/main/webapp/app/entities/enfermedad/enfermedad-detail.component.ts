import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnfermedad } from 'app/shared/model/enfermedad.model';

@Component({
    selector: 'jhi-enfermedad-detail',
    templateUrl: './enfermedad-detail.component.html'
})
export class EnfermedadDetailComponent implements OnInit {
    enfermedad: IEnfermedad;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enfermedad }) => {
            this.enfermedad = enfermedad;
        });
    }

    previousState() {
        window.history.back();
    }
}
