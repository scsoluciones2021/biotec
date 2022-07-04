import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IConsulta } from 'app/shared/model/consulta.model';
import { ConsultaService } from './consulta.service';
import { IUser, UserService } from 'app/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as dayjs from 'dayjs';
@Component({
    selector: 'jhi-consulta-modal',
    templateUrl: './consulta-modal.component.html'
})
export class ConsultaModalComponent implements OnInit {
    // Variables para recibir los datos del padre
    @Input() consulta: IConsulta;
    myForm: FormGroup;

    isSaving: boolean;

    users: IUser[];

    // Si se puede editar según la fecha
    habilitarBotones: boolean;
    visibilidad: String;
    // Si el campo es solo lectura o está activo según el toogle
    soloLectura: boolean;

    constructor(
        private jhiAlertService: JhiAlertService,
        private consultaService: ConsultaService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute,
        private activeModal: NgbActiveModal,
        private formBuilder: FormBuilder
    ) {
        this.createForm();
    }

    private createForm() {
        this.myForm = this.formBuilder.group({
            // idPac: 0,
            // pacienteNombre: ''
        });
    }

    // Escuchamos el valor devuelto por el componente toogle
    cambiaValor(evento) {
        this.soloLectura = !evento;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    closeModal() {
        this.activeModal.close('Modal Closed');
    }

    ngOnInit() {
        this.isSaving = false;
        this.soloLectura = true;
        this.habilitarBotones = false;
        this.visibilidad = 'hidden';
        // A la fecha de la consulta le agregamos tres días
        // el máximo de tiempo permitido para modificar la consulta es 48hs
        let fc = this.consulta.fechaConsulta;
        let fconclone = fc.clone().add(3, 'days');
        const fa = dayjs();
        let diferencia = fconclone.diff(fa, 'days');
        const diasComparacion: number = 3;

        if (diferencia < 0) {
            diferencia = diferencia * -1;
        }
        if (diferencia < diasComparacion) {
            this.habilitarBotones = true;
            this.visibilidad = 'initial';
        }

        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    guardarConsulta() {
        this.isSaving = true;
        this.subscribeToSaveResponse(this.consultaService.update(this.consulta));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConsulta>>) {
        result.subscribe((res: HttpResponse<IConsulta>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(consulta: IConsulta) {
        this.isSaving = false;

        this.activeModal.close(this.myForm.value);
    }
    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
