import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';
import { IUser, UserService } from 'app/core';
import { ICodigoPostal, CodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as moment from 'moment';
import { IProvincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from '../provincia';

@Component({
    selector: 'jhi-paciente-modal',
    templateUrl: './paciente-modal.component.html'
})
export class PacienteModalComponent implements OnInit {
    @Input() id: number;
    @Input() paciente: IPaciente;
    myForm: FormGroup;

    // private _paciente: IPaciente;
    isSaving: boolean;

    users: IUser[];

    // Localidad
    locCompleta: Boolean = false;
    idProvinciaSelec: any;
    localidadesProvincia: ICodigoPostal[];

    // Variables autocomplete
    codigoPostal: any;
    cpPNG: any[];
    filteredCPSingle: any[];

    // fin autocomplete
    // Calendario
    es: any;
    fechaNac: Date;
    maxDateValue: Date;
    meses: number;

    // multiselect obra social
    obraSocSelecc: IObraSocial[];
    obrasocials: IObraSocial[];

    provincias: IProvincia[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pacienteService: PacienteService,
        private obraSocialService: ObraSocialService,
        private userService: UserService,
        private codigoPostalService: CodigoPostalService,
        private provinciaService: ProvinciaService,
        private activatedRoute: ActivatedRoute,
        private activeModal: NgbActiveModal,
        private formBuilder: FormBuilder
    ) {
        this.createForm();
    }

    private createForm() {
        this.myForm = this.formBuilder.group({
            idPac: 0,
            pacienteNombre: ''
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    closeModal() {
        this.activeModal.close('Modal Closed');
    }

    // PRIMENG
    // Código Postal
    filterCPSingle(event) {
        const query = event.query;
        this.codigoPostalService.buscarCP(query).subscribe(
            (res: HttpResponse<ICodigoPostal[]>) => {
                this.filteredCPSingle = this.filterCP(query, res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filterCP(query, cpPNG: any[]): any[] {
        const filtered: any[] = [];

        for (let i = 0; i < cpPNG.length; i++) {
            const codigoPostal = cpPNG[i];
            if (codigoPostal.nombreCiudad.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(codigoPostal);
            }
        }
        return filtered;
    }

    seleccionCP(event) {
        this.codigoPostal = event;
        this.paciente.codigoPostalId = event.id;
        this.paciente.codigoPostalNombreCiudad = event.nombreCiudad;
    }

    borrarCP() {
        this.codigoPostal = null;
        this.paciente.codigoPostalId = null;
        this.paciente.codigoPostalNombreCiudad = null;
    }
    // Fin primeng

    ngOnInit() {
        this.isSaving = false;

        // Calendario en español
        this.es = {
            firstDayOfWeek: 1,
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
            dayNamesMin: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
            monthNames: [
                'Enero',
                'Febrero',
                'Marzo',
                'Abril',
                'Mayo',
                'Junio',
                'Julio',
                'Agosto',
                'Septiembre',
                'Octubre',
                'Noviembre',
                'Diciembre'
            ],
            monthNamesShort: ['En', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            today: 'Hoy',
            clear: 'Borrar',
            dateFormat: 'dd/mm/yy'
        };

        this.maxDateValue = new Date();
        console.log(this.paciente);
        // Fin calendario en español
        if (this.paciente.fechaNacimiento != null) {
            this.fechaNac = moment(this.paciente.fechaNacimiento).toDate();
            this.paciente.edad = moment().diff(this.paciente.fechaNacimiento, 'years');
            this.meses = moment().diff(this.paciente.fechaNacimiento, 'months');
        } else {
            this.paciente.edad = 0;
            this.meses = 0;
        }

        this.obraSocialService.buscarOSTodas().subscribe(
            (res: HttpResponse<IObraSocial[]>) => {
                this.obrasocials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.provinciaService.query().subscribe(
            (res: HttpResponse<IProvincia[]>) => {
                this.provincias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        if (this.paciente.codigoPostalId) {
            this.codigoPostal = new CodigoPostal();
            this.codigoPostal.id = this.paciente.codigoPostalId;
            this.codigoPostal.nombreCiudad = this.paciente.codigoPostalNombreCiudad;
        }

        if (this.paciente.obrasocials !== undefined) {
            this.obraSocSelecc = this.paciente.obrasocials;
        }

        if (this.paciente.provinciaId) {
            this.locCompleta = true;
        }
    }

    guardarPaciente() {
        this.isSaving = true;
        this.paciente.fechaNacimiento = moment(this.fechaNac);
        this.paciente.edad = moment().diff(this.paciente.fechaNacimiento, 'years');

        if (this.paciente.id !== undefined) {
            this.subscribeToSaveResponse(this.pacienteService.update(this.paciente));
        } else {
            this.paciente.usuarioId = 5;
            this.subscribeToSaveResponse(this.pacienteService.create(this.paciente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>) {
        result.subscribe((res: HttpResponse<IPaciente>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(paciente: IPaciente) {
        this.isSaving = false;

        this.myForm.controls['idPac'].setValue(paciente['id']);
        // this.myForm.controls['pacienteNombre'].setValue(this.paciente.apellidoPaciente + "," +this.paciente.nombrePaciente);
        this.myForm.controls['pacienteNombre'].setValue(this.paciente.nombrePaciente);

        this.activeModal.close(this.myForm.value);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackObraSocialById(index: number, item: IObraSocial) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCodigoPostalById(index: number, item: ICodigoPostal) {
        return item.id;
    }

    public seleccionDia(event) {
        const bdate = new Date(event);
        this.paciente.edad = moment().diff(bdate, 'years'); // Math.floor((timeDiff / (1000 * 3600 * 24)) / 365);
    }

    // Selección de provincia
    public seleccionProvincia(event) {
        this.idProvinciaSelec = event.value;
        this.locCompleta = true;
        this.codigoPostalService.buscarCPProvincia(this.idProvinciaSelec).subscribe(
            (res: HttpResponse<ICodigoPostal[]>) => {
                this.filteredCPSingle = res.body;
                this.localidadesProvincia = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}
