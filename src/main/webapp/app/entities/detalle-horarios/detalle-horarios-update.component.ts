import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosService } from './detalle-horarios.service';

@Component({
    selector: 'jhi-detalle-horarios-update',
    templateUrl: './detalle-horarios-update.component.html'
})
export class DetalleHorariosUpdateComponent implements OnInit {
    private _detalleHorarios: IDetalleHorarios;
    isSaving: boolean;

    constructor(private detalleHorariosService: DetalleHorariosService, 
        private activatedRoute: ActivatedRoute,
        private router: Router) {}

    ngOnInit() {
        let detalleHorarios = localStorage.getItem("editDetalleId");
       /* if(!detalleId) {
          alert("Invalid action.")
          this.router.navigate(['horarios-profesional-update']);
          return;
        } */
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detalleHorarios }) => {
            this.detalleHorarios = detalleHorarios;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detalleHorarios.id !== undefined) {
            this.subscribeToSaveResponse(this.detalleHorariosService.update(this.detalleHorarios));
        } else {
            this.subscribeToSaveResponse(this.detalleHorariosService.create(this.detalleHorarios));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleHorarios>>) {
        result.subscribe((res: HttpResponse<IDetalleHorarios>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get detalleHorarios() {
        return this._detalleHorarios;
    }

    set detalleHorarios(detalleHorarios: IDetalleHorarios) {
        this._detalleHorarios = detalleHorarios;
    }
}
