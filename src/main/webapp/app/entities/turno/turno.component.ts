import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, of } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiLanguageService } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TurnoService } from './turno.service';
import { ProfesionalService } from '../profesional';
import { EspecialidadService } from '../especialidad';
import { IProfesional, IProfesionalTurno } from '../../shared/model/profesional.model';
import { IEspecialidad } from '../../shared/model/especialidad.model';
import { HorariosProfesionalService } from '../horarios-profesional/horarios-profesional.service';
import { DatePipe } from '@angular/common';
import { ExcelService } from 'app/shared/util/excel.service';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PacienteModalComponent } from '../paciente';
import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from '../paciente/paciente.service';
import { PagoConsultaService } from '../pago-consulta/pago-consulta.service';
import { IPagoConsulta } from 'app/shared/model/pago-consulta.model';

@Component({
    selector: 'jhi-turno',
    templateUrl: './turno.component.html'
})
export class TurnoComponent implements OnInit, OnDestroy {
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
    profesionales: any[];
    profesionalesSelecc: IProfesional[];
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

    paciente: Paciente = new Paciente();
    modalRef: NgbModalRef;

    data: any[];
    listaturnos: ITurno[];
    usuarioCarga: string;
    listaPagos: IPagoConsulta[];
    data2: any;

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
        private horariosService: HorariosProfesionalService,
        private excelService: ExcelService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private modalService: NgbModal,
        private pacienteService: PacienteService,
        private pagoService: PagoConsultaService
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

        // Seteo de variable de búsqueda
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        // Para búsqueda
        if (this.currentSearch) {
            this.turnoService
                .query({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IProfesional[]>) => this.paginateTurnos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para búsqueda  con filtros
        if (
            this.busqueda_apellido ||
            this.busqueda_nombre ||
            this.busqueda_dni ||
            this.busqueda_fecha ||
            this.especialidadesSelecc !== undefined ||
            this.profesionalesSelecc !== undefined
        ) {
            const espSel = new Array();
            const profSel = new Array();
            let fech = '';
            const datePipe = new DatePipe('en-US');

            if (this.especialidadesSelecc !== undefined) {
                this.especialidadesSelecc.forEach(function(element) {
                    espSel.push(element.id);
                });
            }
            if (this.profesionalesSelecc !== undefined) {
                this.profesionalesSelecc.forEach(function(element) {
                    profSel.push(element.id);
                });
            }

            fech = datePipe.transform(this.busqueda_fecha, 'yyyy-MM-dd');

            this.camposBusqueda = [
                this.busqueda_apellido,
                this.busqueda_nombre,
                this.busqueda_dni,
                fech,
                espSel,
                profSel,
                'undefined',
                'turnos'
            ];

            this.turnoService
                .busquedaConFiltros({
                    page: this.page - 1,
                    query: this.camposBusqueda,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ITurno[]>) => this.paginateTurnos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        // Para búsqueda
        this.turnoService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                // query: this.busqueda_fecha,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITurno[]>) => this.paginateTurnos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/turno'], {
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
        // Variable de búsqueda
        this.camposBusqueda = null;
        this.router.navigate([
            '/turno',
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

        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.usuarioCarga = this.currentAccount.id;
        });
        this.registerChangeInTurnos();

        this.profesionalService.buscarTodosArray().subscribe(
            (res: HttpResponse<IProfesionalTurno[]>) => {
                this.profesionales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.especialidadService.query().subscribe(
            (res: HttpResponse<IEspecialidad[]>) => {
                this.especialidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.busqueda_dni = undefined;
        this.busqueda_apellido = undefined;
        this.busqueda_nombre = undefined;
        this.especialidadesSelecc = undefined;
        this.profesionalesSelecc = undefined;
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
        if (this.profesionalesSelecc !== undefined) {
            this.profesionalesSelecc.forEach(function(element) {
                profSel.push(element.id);
            });
        }

        fech = datePipe.transform(this.busqueda_fecha, 'yyyy-MM-dd');

        this.camposBusqueda = [this.busqueda_apellido, this.busqueda_nombre, this.busqueda_dni, fech, espSel, profSel];

        this.turnoService
            .busquedaConFiltrosImp({
                query: this.camposBusqueda
            })
            .subscribe(
                (res: HttpResponse<ITurno[]>) => {
                    this.listaturnos = res.body;
                    console.log(this.listaturnos);
                    this.data = JSON.parse(
                        JSON.stringify(this.listaturnos, [
                            'id',
                            'dia',
                            'hora',
                            'apellido',
                            'nombre',
                            'dni',
                            'hora',
                            'tur_obs_relNombreObraSocial'
                        ])
                    );
                    console.log(this.data);

                    this.pagoService
                        .buscarFiltros({
                            query: this.camposBusqueda
                        })
                        .subscribe((res2: HttpResponse<IPagoConsulta[]>) => {
                            this.listaPagos = res2.body;
                            // this.data2 = JSON.parse(JSON.stringify(this.listaPagos, ["NoOfVisits","age","name"]));
                            this.data2 = JSON.parse(JSON.stringify(this.listaPagos));
                            this.data.forEach(el => {
                                this.data2.forEach(pago => {
                                    if (pago.pagoturnoId == el.id) {
                                        el.monto = pago.monto;
                                        el.tipo = pago.tipo;
                                        el.cupones = pago.cupones;
                                        return;
                                    }
                                });
                                delete el.id;
                                delete el.idHorario;
                                delete el.tipoPaciente;
                                delete el.tur_esp_relId;
                                delete el.tur_obs_relId;
                                delete el.tur_prof_relId;
                                delete el.usuarioCarga;
                                delete el.email;
                                delete el.horariosOcupados;
                                delete el.estado;
                                delete el.tur_esp_relNombreEspecialidad;
                                delete el.cupones;
                            });
                            this.excelService.exportAsExcelFile(this.data, 'listado');
                        });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    // Actualizamos el estado a "presentado" para que pueda atenderlo el médico.
    //1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado"
    confirmarTurno(idTurno: number, dni: number) {
        this.pacienteService.buscarPacienteXDNI(dni).subscribe(
            (res: HttpResponse<IPaciente>) => {
                this.paciente = res.body;
                this.openFormModal(idTurno);
            },
            (res: HttpErrorResponse) => {
                this.onError(res.message);
            }
        );
    }

    // Modal para crear un nuevo paciente
    openFormModal(idTurno) {
        this.modalRef = this.modalService.open(PacienteModalComponent as Component, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.id = idTurno;
        this.modalRef.componentInstance.paciente = this.paciente;

        // al volver
        this.modalRef.result
            .then(result => {
                this.turnoService.cambiarEstado(idTurno, 2).subscribe(response => {
                    this.loadAll();
                });
            })
            .catch(error => {
                console.log(error);
            });
    }
}
