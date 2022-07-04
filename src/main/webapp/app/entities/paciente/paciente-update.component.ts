import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { IObraSocial, ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';
import { IUser, UserService } from 'app/core';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';
import { CodigoPostal } from '../../shared/model/codigo-postal.model';
import { ProvinciaService } from '../provincia';
import { IProvincia } from 'app/shared/model/provincia.model';
import * as dayjs from 'dayjs';

@Component({
    selector: 'jhi-paciente-update',
    templateUrl: './paciente-update.component.html'
})
export class PacienteUpdateComponent implements OnInit {
    private _paciente: IPaciente;
    isSaving: boolean;

    pacienteobrasocials: IObraSocial[];

    users: IUser[];

    codigopostals: ICodigoPostal[];

    // autocomplete codigo postal
    codigoPostal: any;
    cpPNG: any[];
    filteredCPSingle: any[];
    // multiselect obra social
    obraSocSelecc: IObraSocial[];
    obrasocials: IObraSocial[];
    provincias: IProvincia[];

    // Localidad
    locCompleta: Boolean = false;
    idProvinciaSelec: any;
    localidadesProvincia: ICodigoPostal[];

    // Calendario
    es: any;
    fechaNac: Date;
    maxDateValue: Date;
    meses: number;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pacienteService: PacienteService,
        private obraSocialService: ObraSocialService,
        private userService: UserService,
        private codigoPostalService: CodigoPostalService,
        private provinciaService: ProvinciaService,
        private activatedRoute: ActivatedRoute
    ) {}
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
    // PRIMENG
    // Código Postal

    // filterCPSingle(event) { //esta función es para filtrar por provincia las localidades.
    //     const query = event.query;
    //     console.log(this.localidadesProvincia);
    //     this.filteredCPSingle = this.filterCP(query, this.localidadesProvincia);
    // }
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
        this.paciente.codigoPostalId = event.id;
        this.paciente.codigoPostalNombreCiudad = event.nombreCiudad;
    }

    borrarCP() {
        this.paciente.codigoPostalId = null;
        this.paciente.codigoPostalNombreCiudad = null;
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paciente }) => {
            this.paciente = paciente;
        });
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
        // Fin calendario en español

        if (this.paciente.fechaNacimiento != null) {
            this.fechaNac = dayjs(this.paciente.fechaNacimiento).toDate();
            this.paciente.edad = dayjs().diff(this.paciente.fechaNacimiento, 'years');
            this.meses = dayjs().diff(this.paciente.fechaNacimiento, 'months');
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.paciente.obrasocials = this.obraSocSelecc;
        this.paciente.fechaNacimiento = dayjs(this.fechaNac);

        if (this.paciente.id !== undefined) {
            this.subscribeToSaveResponse(this.pacienteService.update(this.paciente));
        } else {
            this.paciente.usuarioId = 5;
            this.subscribeToSaveResponse(this.pacienteService.create(this.paciente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>) {
        result.subscribe((res: HttpResponse<IPaciente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
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
    trackProvinciaById(index: number, item: IProvincia) {
        return item.id;
    }
    get paciente() {
        return this._paciente;
    }

    set paciente(paciente: IPaciente) {
        this._paciente = paciente;
    }

    public seleccionDia(event) {
        const anio = event.getFullYear();
        const mes = event.getMonth();
        const dia = event.getDate(); // día seleccionado

        const bdate = new Date(event);
        const bdateAnio = new Date(new Date().getFullYear(), mes, dia);
        this.paciente.edad = dayjs().diff(bdate, 'years'); // Math.floor((timeDiff / (1000 * 3600 * 24)) / 365);
        this.meses = dayjs().diff(bdateAnio, 'months'); // Math.floor((timeDiff / (1000 * 3600 * 24)) / 365);
    }
}
