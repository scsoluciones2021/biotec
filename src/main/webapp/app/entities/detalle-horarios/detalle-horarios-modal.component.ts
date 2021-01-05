import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDetalleHorarios, DetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosService } from './detalle-horarios.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup } from '@angular/forms';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-detalle-horarios-modal',
    templateUrl: './detalle-horarios-modal.component.html'
})
export class DetalleHorariosModalComponent implements OnInit {
    @Input() idH: number;
    @Input() idDetalleHorarios: number;
    @Input() detalleHorarios: IDetalleHorarios;
    myForm: FormGroup;

    isSaving: boolean;
 

    constructor(private jhiAlertService: JhiAlertService,private detalleHorariosService: DetalleHorariosService, 
        private activatedRoute: ActivatedRoute,
        private router: Router,        
        private activeModal: NgbActiveModal,
        private formBuilder: FormBuilder) {
            this.createForm();
        }

    ngOnInit() {
        // let detalleHorarios = localStorage.getItem("editDetalleId");
        // console.log(this.detalleHorarioPadre[0]);
        
        this.isSaving = false;
        if(this.detalleHorarios != undefined){
            this.detalleHorarios.idHorario = this.idH;
        }
        
       if(this.idDetalleHorarios != undefined){
            this.detalleHorariosService.find(this.idDetalleHorarios).subscribe(
                (data: HttpResponse<IDetalleHorarios>) => {
                    this.detalleHorarios = data.body;
                },
                (data: HttpErrorResponse) => this.onError(data.message)
              )
        }
        

        
    }

    
    private subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleHorarios>>) {
        result.subscribe((res: HttpResponse<IDetalleHorarios>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(detalle: IDetalleHorarios) {
        this.isSaving = false;
        this.myForm.controls['idDet'].setValue(detalle['id']);
       /* this.myForm.controls['horaDesde'].setValue(this.detalle.horaDesde);
        this.myForm.controls['horaHasta'].setValue(this.detalle.horaHasta);
        this.myForm.controls['lunes'].setValue(this.detalle.lunes);
        this.myForm.controls['martes'].setValue(this.detalle.martes);
        this.myForm.controls['martes'].setValue(this.detalle.miercoles);
        this.myForm.controls['martes'].setValue(this.detalle.jueves);
        this.myForm.controls['martes'].setValue(this.detalle.viernes);
        this.myForm.controls['martes'].setValue(this.detalle.sabado);
        this.myForm.controls['martes'].setValue(this.detalle.domingo);*/

        this.activeModal.close(this.myForm.value);
    }

    private onSaveError() {
        this.isSaving = false;
    }
  
    private createForm() {
        this.myForm = this.formBuilder.group({
            idDet: 0
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    closeModal() {
        this.activeModal.close('Modal Closed');
    }

    guardarDetalle() {
        this.isSaving = true;
        this.detalleHorarios.lunes = (this.detalleHorarios.lunes ? 1 : 0);
        this.detalleHorarios.martes = (this.detalleHorarios.martes ? 1 : 0);
        this.detalleHorarios.miercoles = (this.detalleHorarios.miercoles ? 1 : 0);
        this.detalleHorarios.jueves = (this.detalleHorarios.jueves ? 1 : 0);
        this.detalleHorarios.viernes = (this.detalleHorarios.viernes ? 1 : 0);
        this.detalleHorarios.sabado = (this.detalleHorarios.sabado ? 1 : 0);
        this.detalleHorarios.domingo = (this.detalleHorarios.domingo ? 1 : 0);
         if (this.detalleHorarios.id !== undefined) {
            this.subscribeToSaveResponse(this.detalleHorariosService.update(this.detalleHorarios));
        } else {
            this.subscribeToSaveResponse(this.detalleHorariosService.create(this.detalleHorarios));
        } 
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
