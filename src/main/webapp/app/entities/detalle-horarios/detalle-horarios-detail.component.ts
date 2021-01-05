import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';

@Component({
    selector: 'jhi-detalle-horarios-detail',
    templateUrl: './detalle-horarios-detail.component.html'
})
export class DetalleHorariosDetailComponent implements OnInit {
    detalleHorarios: IDetalleHorarios;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleHorarios }) => {
            this.detalleHorarios = detalleHorarios;
        });
    }

    previousState() {
        window.history.back();
    }
}
