import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

@Component({
    selector: 'jhi-antecedentes-familiares-detail',
    templateUrl: './antecedentes-familiares-detail.component.html'
})
export class AntecedentesFamiliaresDetailComponent implements OnInit {
    antecedentesFamiliares: IAntecedentesFamiliares;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ antecedentesFamiliares }) => {
            this.antecedentesFamiliares = antecedentesFamiliares;
        });
    }

    previousState() {
        window.history.back();
    }
}
