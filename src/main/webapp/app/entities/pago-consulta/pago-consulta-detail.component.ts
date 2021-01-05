import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPagoConsulta } from 'app/shared/model/pago-consulta.model';

@Component({
    selector: 'jhi-pago-consulta-detail',
    templateUrl: './pago-consulta-detail.component.html'
})
export class PagoConsultaDetailComponent implements OnInit {
    pagoConsulta: IPagoConsulta;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagoConsulta }) => {
            this.pagoConsulta = pagoConsulta;
        });
    }

    previousState() {
        window.history.back();
    }
}
