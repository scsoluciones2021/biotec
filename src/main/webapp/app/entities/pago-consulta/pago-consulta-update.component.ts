import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPagoConsulta, PagoConsulta } from 'app/shared/model/pago-consulta.model';
import { PagoConsultaService } from './pago-consulta.service';
import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from 'app/entities/turno';

@Component({
    selector: 'jhi-pago-consulta-update',
    templateUrl: './pago-consulta-update.component.html'
})
export class PagoConsultaUpdateComponent implements OnInit {
    private _pagoconsulta: IPagoConsulta;
    isSaving: boolean;

    turnos: ITurno[];

    /*editForm = this.fb.group({
        id: [],
        monto: [],
        tipo: [],
        cupones: [],
        pagoturnoId: []
    });*/

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pagoConsultaService: PagoConsultaService,
        protected turnoService: TurnoService,
        protected activatedRoute: ActivatedRoute
    ) // private fb: FormBuilder
    {}

    ngOnInit() {
        this.isSaving = false;
        this._pagoconsulta = new PagoConsulta();
        this.turnoService
            .query()
            .pipe(filter((mayBeOk: HttpResponse<ITurno[]>) => mayBeOk.ok), map((response: HttpResponse<ITurno[]>) => response.body))
            .subscribe((res: ITurno[]) => (this.turnos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    /* updateForm(pagoConsulta: IPagoConsulta) {
        this.editForm.patchValue({
            id: pagoConsulta.id,
            monto: pagoConsulta.monto,
            tipo: pagoConsulta.tipo,
            cupones: pagoConsulta.cupones,
            pagoturnoId: pagoConsulta.pagoturnoId
        });
    }*/

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        // const pagoConsulta = this.createFromForm();
        if (this.pagoconsulta.id !== undefined) {
            this.subscribeToSaveResponse(this.pagoConsultaService.update(this.pagoconsulta));
        } else {
            this.subscribeToSaveResponse(this.pagoConsultaService.create(this.pagoconsulta));
        }
    }

    /*   private createFromForm(): IPagoConsulta {
        return {
            ...new PagoConsulta(),
            id: this.editForm.get(['id']).value,
            monto: this.editForm.get(['monto']).value,
            tipo: this.editForm.get(['tipo']).value,
            cupones: this.editForm.get(['cupones']).value,
            pagoturnoId: this.editForm.get(['pagoturnoId']).value
        };
    }
*/
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagoConsulta>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        // this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTurnoById(index: number, item: ITurno) {
        return item.id;
    }

    get pagoconsulta() {
        return this._pagoconsulta;
    }

    set pagoconsulta(pagoconsulta: IPagoConsulta) {
        this._pagoconsulta = pagoconsulta;
    }
}
