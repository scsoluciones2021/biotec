import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Subscription, empty } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiLanguageService } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TurnoService } from './turno.service';
import { ProfesionalService } from '../profesional';
import { EspecialidadService } from '../especialidad';
import { IProfesional } from '../../shared/model/profesional.model';
import { IEspecialidad } from '../../shared/model/especialidad.model';
import { DatePipe } from '@angular/common';
import { ExcelService } from 'app/shared/util/excel.service';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { TurnoProf } from 'app/shared/model/turno-profesional.model';
import { ITurnoProf } from '../../shared/model/turno-profesional.model';
import { FichaService } from '../ficha/ficha.service';
import { IFicha, Ficha } from '../../shared/model/ficha.model';
import { PacienteService } from '../paciente/paciente.service';
import { IPaciente } from '../../shared/model/paciente.model';
import dayjs = require('dayjs');

@Component({
    selector: 'jhi-turno-profesional',
    templateUrl: './turno-profesional.component.html'
})
export class TurnoProfesionalComponent implements OnInit, OnDestroy {
    private turnoP: ITurnoProf;
    currentAccount: any;
    turnos: ITurno[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    especialidades: any[];
    especialidadesSelecc: IEspecialidad[];
    filtroDni: RegExp = /^\d{7,8}(?:[-\s]\d{4})?$/;
    // Calendario
    es: any;
    // Campos de búsqueda
    busqueda_dni: any;
    busqueda_nombre: any;
    busqueda_apellido: any;
    busqueda_fecha: Date;
    currentSearch: string;
    camposBusqueda: string[];

    data: any[];
    listaturnos: ITurno[];
    usuarioCarga: string;
    profesional: IProfesional;
    fechaBusqueda: any;

    constructor(
        private turnoService: TurnoService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private profesionalService: ProfesionalService,
        private especialidadService: EspecialidadService,
        private fichaService: FichaService,
        private pacienteService: PacienteService,
        private excelService: ExcelService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });

        this.activatedRoute.queryParams.subscribe(params => {
            this.fechaBusqueda = params['fechaBusqueda'];
        });

        // Seteo de variable de búsqueda
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        const espSel = new Array();
        let fech = '';
        const datePipe = new DatePipe('en-US');
        if (this.especialidadesSelecc !== undefined) {
            this.especialidadesSelecc.forEach(function(element) {
                espSel.push(element.id);
            });
        }

        fech = datePipe.transform(this.busqueda_fecha, 'yyyy-MM-dd');

        this.turnoP = new TurnoProf();
        this.turnoP.fecha = dayjs(fech);
        this.turnoP.profesional = this.profesional.id;
        this.turnoP.retorno = 'turno-profesional';
        this.turnoP.especialidades = this.especialidadesSelecc;
        this.turnoP.estado = 2;

        this.turnoService
            .busquedaMedico({
                page: this.page - 1,
                size: this.itemsPerPage,
                query: this.turnoP,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITurno[]>) => this.paginateTurnos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        return;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/turno-profesional'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/turno-profesional',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        // Calendario en español
        this.es = {
            firstDayOfWeek: 0,
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
        // Fin calendario en español
        // Seteamos campos de búsqueda
        this.busqueda_fecha = new Date();

        this.principal.hasAuthority('ROLE_MEDICO').then(userRole => {
            if (userRole) {
                this.principal.identity().then(account => {
                    this.currentAccount = account;
                    // this.roles = this.currentAccount.authorities;
                    this.profesionalService.buscarPorUsuario(this.currentAccount.id).subscribe(
                        (res: HttpResponse<IProfesional>) => {
                            this.profesional = res.body;
                            this.especialidadesSelecc = new Array();
                            this.especialidades = this.profesional.especialidads;
                            this.profesional.especialidads.forEach(esp => {
                                this.especialidadesSelecc.push(esp);
                            });
                            this.loadAll();
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                });
            }
        });

        this.registerChangeInTurnos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITurno) {
        return item.id;
    }

    registerChangeInTurnos() {
        this.eventSubscriber = this.eventManager.subscribe('turnoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateTurnos(data: ITurno[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.turnos = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // Métodos agregados

    public limpiarCampos() {
        this.especialidadesSelecc = undefined;
        this.busqueda_fecha = undefined;
    }

    // Métodos para búsqueda
    search() {
        this.loadAll();
    }

    refresh() {
        this.loadAll();
    }

    exportAsXLSX(): void {
        const espSel = new Array();
        const profSel = new Array();
        let fech = '';
        const datePipe = new DatePipe('en-US');

        if (this.especialidadesSelecc !== undefined) {
            this.especialidadesSelecc.forEach(function(element) {
                espSel.push(element.id);
            });
        }

        profSel.push(this.profesional.id);

        fech = datePipe.transform(this.busqueda_fecha, 'yyyy-MM-dd');

        this.camposBusqueda = [this.busqueda_apellido, this.busqueda_nombre, this.busqueda_dni, fech, espSel, profSel, this.usuarioCarga];

        this.turnoService
            .busquedaConFiltrosImp({
                query: this.camposBusqueda
            })
            .subscribe(
                (res: HttpResponse<ITurno[]>) => {
                    this.listaturnos = res.body;
                    this.data = JSON.parse(JSON.stringify(this.listaturnos));
                    this.data.forEach(el => {
                        delete el.idHorario;
                        delete el.tipoPaciente;
                        delete el.tur_esp_relId;
                        delete el.tur_obs_relId;
                        delete el.tur_prof_relId;
                    });
                    this.excelService.exportAsExcelFile(this.data, 'listado');
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    // 1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado", 6:"No Presentado"
    // Función para colocar el turno en estado "En atención" y crear la consulta
    atender(turnoAatender: ITurno) {
        turnoAatender.dia = dayjs(turnoAatender.dia);
        turnoAatender.hora = dayjs(turnoAatender.hora, 'HH:mm');
        this.turnoService.cambiarEstado(turnoAatender.id, 3).subscribe();
        // Agregamos skipLocationChange: true para que no se vean los parámetros en la url
        const navigationExtras: NavigationExtras = {
            queryParams: {
                nroPaciente: turnoAatender.dni,
                fechaBusqueda: turnoAatender.dia,
                idTurno: turnoAatender.id,
                idProfesional: turnoAatender.tur_prof_relId,
                idEspecialidad: turnoAatender.tur_esp_relId
            },
            skipLocationChange: true
        };
        // Obtenemos los datos del paciente
        this.pacienteService.buscarPacienteXDNI(turnoAatender.dni).subscribe((respac: HttpResponse<IPaciente>) => {
            // Chequeamos si el paciente tiene ficha. Si no tiene ficha, le creamos una
            this.fichaService.existeFichaIdPac(respac.body.id).subscribe((data: HttpResponse<any>) => {
                if (data.body === 0) {
                    let nuevaFicha: IFicha;
                    nuevaFicha = new Ficha();
                    nuevaFicha.especialidadId = turnoAatender.tur_esp_relId;
                    nuevaFicha.especialidadNombreEspecialidad = turnoAatender.tur_esp_relNombreEspecialidad;
                    nuevaFicha.fechaIngreso = dayjs(turnoAatender.dia);
                    nuevaFicha.pacienteId = respac.body.id;
                    nuevaFicha.pacienteNombrePaciente = turnoAatender.nombre;
                    nuevaFicha.profesionalId = turnoAatender.tur_prof_relId;
                    nuevaFicha.profesionalNombreProfesional = turnoAatender.tur_prof_relNombreProfesional;

                    this.fichaService.create(nuevaFicha).subscribe(respuesta => {
                        console.log('se crea la ficha');
                    });
                }
            });
        });

        // Revisamos si no hay ya una consulta abierta para la combinación turno-paciente-profesional-especialidad
        this.turnoService.tieneConsulta(turnoAatender.id).subscribe(
            (res: HttpResponse<any>) => {
                if (res.body === 0) {
                    this.router.navigate(['/consulta/new', { previousUrl: '/turno-profesional' }], navigationExtras);
                } else {
                    this.router.navigate(['/consulta/' + res.body + '/edit', { previousUrl: '/turno-profesional' }], navigationExtras);
                }
            }
            // (res: HttpResponse<void>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.router.navigate(['/consulta/new'], navigationExtras)
        );
    }

    // Cambiamos el turno a "No Presentado"
    noPresentado(turnoNoPresentado: number) {
        this.turnoService.cambiarEstado(turnoNoPresentado, 6).subscribe();
    }
}
