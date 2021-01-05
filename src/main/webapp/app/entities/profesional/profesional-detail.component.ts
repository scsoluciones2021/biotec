import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProfesional } from 'app/shared/model/profesional.model';

@Component({
    selector: 'jhi-profesional-detail',
    templateUrl: './profesional-detail.component.html'
})
export class ProfesionalDetailComponent implements OnInit {
    profesional: IProfesional;

    imagenUrl = '/content/images/default_doc.png';

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profesional }) => {
            this.profesional = profesional;
            console.log(this.profesional.especialidads);
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
