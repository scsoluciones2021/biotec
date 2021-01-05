import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEspecialidad } from 'app/shared/model/especialidad.model';

@Component({
    selector: 'jhi-especialidad-detail',
    templateUrl: './especialidad-detail.component.html'
})
export class EspecialidadDetailComponent implements OnInit {
    especialidad: IEspecialidad;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ especialidad }) => {
            this.especialidad = especialidad;
        });
    }

    previousState() {
        window.history.back();
    }
}
