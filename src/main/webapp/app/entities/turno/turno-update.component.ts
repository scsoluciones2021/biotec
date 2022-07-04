import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITurno, Turno } from 'app/shared/model/turno.model';
import { TurnoService } from './turno.service';
import { IEspecialidad } from 'app/shared/model/especialidad.model';
import { EspecialidadService } from 'app/entities/especialidad';
import { IProfesional, IProfesionalTurno } from 'app/shared/model/profesional.model';
import { ProfesionalService } from 'app/entities/profesional';
// Agregados
import { HorariosProfesionalService } from '../horarios-profesional/horarios-profesional.service';
import { IHorariosProfesional } from '../../shared/model/horarios-profesional.model';
import { Message } from 'primeng/components/common/message';
import { DatePipe } from '@angular/common';
// import {MessageService} from 'primeng/api';
import * as dayjs from 'dayjs';
import { PacienteService } from '../paciente';
import { IPaciente } from 'app/shared/model/paciente.model';
import { IObraSocial, ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from '../obra-social';
import { IDetalleHorarios } from '../../shared/model/detalle-horarios.model';
import { DetalleHorariosService } from '../detalle-horarios/detalle-horarios.service';
import { Principal } from '../../core/auth/principal.service';
import { UserService } from '../../core/user/user.service';
import { PagoConsultaUpdateComponent } from '../pago-consulta/pago-consulta-update.component';
import { BloqueosService } from '../bloqueos';

@Component({
    selector: 'jhi-turno-update',
    templateUrl: './turno-update.component.html'
})
export class TurnoUpdateComponent implements OnInit {
    private _turno: ITurno;

    isSaving: boolean;

    especialidads: IEspecialidad[];
    profesionales: IProfesional[];
    turnosXhorario: number;
    profesional: IProfesionalTurno;
    diaDp: any;
    horaDp: any;
    // filtros
    // filtroDni: RegExp = /^\d{7,8}(?:[-\s]\d{4})?$/;
    // calendario
    date14: Date;
    minDateValue: Date;
    maxDateValue: Date;
    habilitarCampos: Boolean = false;
    diasSinTurnos: any[] = [];
    mostrar: Boolean = false;
    es: any;
    horariosXdia: any;
    mostrarDias: Boolean = false;
    turnosSeleccionados: string[] = [];
    mostrarHorarios: Boolean = false;
    cars: any[];
    turnosDelDia: any;
    fechaSeleccionada: Date;
    idHorario: any;
    profesionalSelec: IProfesional;
    // Mensajes
    msgs: Message[] = [];
    msgsOK: Message[] = [];
    // Búsqueda de paciente
    pacienteExistente: any = 0;
    // autocomplete obra social
    obrasocials: IObraSocial[];
    obsPNG: any[];
    obrasocial: ObraSocial;
    obraSocSelecc: IObraSocial[];
    // fin autocomplete

    franjaHoraria: string;
    intervaloArreglo: number[];
    dia: number;
    diasNoValidos: any[];
    horariosSeleccionados: any;
    account: any;

    @ViewChild(PagoConsultaUpdateComponent) pagoCon: PagoConsultaUpdateComponent;

    constructor(
        private jhiAlertService: JhiAlertService,
        private turnoService: TurnoService,
        private especialidadService: EspecialidadService,
        private profesionalService: ProfesionalService,
        private horariosService: HorariosProfesionalService,
        private detalleHorariosService: DetalleHorariosService,
        private pacienteService: PacienteService,
        private obraSocialService: ObraSocialService,
        private principal: Principal,
        private bloqueosService: BloqueosService,
        private userService: UserService // private messageService: MessageService,
    ) {}

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
        this.minDateValue = new Date();
        this.maxDateValue = dayjs()
            .add(5, 'M')
            .endOf('month')
            .toDate();
        // Fin calendario en español
        this.isSaving = false;
        this.turno = new Turno();

        this.profesionalService.buscarTodosArray().subscribe(
            (res: HttpResponse<IProfesionalTurno[]>) => {
                this.profesionales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.obraSocialService.buscarOSTodas().subscribe(
            (res: HttpResponse<IObraSocial[]>) => {
                this.obrasocials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        // Cargamos todos los campos necesarios para el turno
        this.turno.tur_prof_relId = this.profesional.id;
        this.turno.tur_prof_relNombreProfesional = this.profesional.nombreProfesional;
        this.turno.dia = dayjs(this.fechaSeleccionada);
        this.turno.idHorario = this.idHorario;
        const horariosSeleccionados = this.obtenerHorariosSeleccionados(this.turnosSeleccionados);
        const min = horariosSeleccionados.sort()[0];
        const arrmin = min.split(':');
        this.fechaSeleccionada.setHours(parseInt(arrmin[0], 0));
        this.fechaSeleccionada.setMinutes(parseInt(arrmin[1], 0));
        this.turno.hora = dayjs(this.fechaSeleccionada, 'HH:mm');
        this.turno.tipoPaciente = this.pacienteExistente;
        this.turno.horariosOcupados = horariosSeleccionados.join(';');
        this.turno.estado = 1; //1:"Otorgado", 2:"Presentado", 3:"En Atención", 4:"Finalizado", 5:"Cancelado"
        const obs = new ObraSocial();
        // Debe permitir seleccionar sólo una obra social de las que tiene
        this.obraSocSelecc.forEach(function(element) {
            obs.id = element.id;
            obs.nombreObraSocial = element.nombreObraSocial;
        });
        this.turno.tur_obs_relId = obs.id;
        this.turno.tur_obs_relNombreObraSocial = obs.nombreObraSocial;

        const idEsp = this.turno.tur_esp_relId;
        const arrayIdEspProf = this.profesional.idsEspecialidades.split('|');
        const arrayNomEspProf = this.profesional.nombreEspecialidades.split('|');
        const posEsp = arrayIdEspProf.indexOf(idEsp.toString());
        this.turno.tur_esp_relNombreEspecialidad = arrayNomEspProf[posEsp];
        this.turno.usuarioCarga = 0;
        this.principal.identity().then(account => {
            // we assign this account to declared variable account for using outside this method scope
            //this.account = account.id;     // account of current logged in user
            this.turno.usuarioCarga = account.id;
            if (this.turno.id !== undefined) {
                this.subscribeToSaveResponse(this.turnoService.update(this.turno));
            } else {
                this.subscribeToSaveResponse(this.turnoService.create(this.turno));
            }
        });
    }
    private obtenerHorariosSeleccionados(turnosSeleccionados: string[]) {
        const hs = [];
        turnosSeleccionados.forEach(function(element, index) {
            const ele = element.split('-');
            hs[index] = ele[2].concat('-', ele[3]);
        });
        return hs;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITurno>>) {
        result.subscribe((res: HttpResponse<ITurno>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(res) {
        this.isSaving = false;
        this.pagoCon.pagoconsulta.pagoturnoId = res.body.id;
        this.pagoCon.save();
        this.msgsOK.push({ severity: 'success', summary: 'Información', detail: 'Se ha registrado correctamente el turno.' });
        this.limpiarCampos();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEspecialidadById(index: number, item: IEspecialidad) {
        return item.id;
    }

    trackProfesionalById(index: number, item: IProfesional) {
        return item.id;
    }
    get turno() {
        return this._turno;
    }

    set turno(turno: ITurno) {
        this._turno = turno;
    }

    // Métodos agregados

    // Selección del profesional
    public seleccion(event) {
        // console.log(this.profesionalSelec);
        this.msgs = [];
        const idProfesional = event.value[0].id;
        this.profesional = event.value[0];
        this.mostrar = false;
        this.mostrarDias = false;
        this.mostrarHorarios = false;
        this.especialidads = null;
        this.turno.tur_esp_relId = null;
        // Buscamos las especialidades del profesional
        this.especialidadService.espeProfesional(idProfesional).subscribe(
            (res: HttpResponse<IEspecialidad[]>) => {
                if (res.body === undefined || res.body.length === 0) {
                    this.hide();
                    this.mostrar = false;
                    this.limpiarCampos();
                    this.msgs.push({
                        severity: 'warn',
                        summary: 'Advertencia',
                        detail: 'El profesional seleccionado no tiene especialidades cargadas.'
                    });
                } else {
                    this.especialidads = res.body;
                    this.mostrar = true;
                    this.turno.tur_esp_relId = this.especialidads[0].id;
                    this.seleccionEspecialidad(event);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    // Función para ocultar el mensaje
    hide() {
        this.msgs = [];
    }

    // Función para cuando selecciona la especialidad
    public seleccionEspecialidad(event) {
        this.mostrarDias = true;

        this.horariosService
            .horarioProfesional(this.profesional.id, this.turno.tur_esp_relId)
            .subscribe((resH: HttpResponse<IHorariosProfesional[]>) => {
                if (resH.body === undefined || resH.body.length === 0) {
                    this.hide();
                    this.mostrar = false;
                    this.mostrarDias = false;
                    this.mostrarHorarios = false;
                    this.limpiarCampos();
                    this.msgs.push({
                        severity: 'warn',
                        summary: 'Advertencia',
                        detail: 'El profesional seleccionado no tiene horarios asignados.'
                    });
                } else {
                    this.diasNoValidos = new Array();
                    this.horariosXdia = new Array();
                    this.detalleHorariosService.buscarXHorario({ idHorario: resH.body[0].id }).subscribe(
                        (detH: HttpResponse<IDetalleHorarios[]>) => {
                            // Buscamos los horarios para ese profesional para esa especialidad
                            if (detH.body.length > 0) {
                                this.mostrar = true;

                                // Para deshabilitar los días que no tiene turnos el profesional
                                // Ej: this.diasSinTurnos=[0,6];
                                // recorro todos los detalles de horario del profesional
                                for (let i = 0; i < detH.body.length; i++) {
                                    const dhorario = detH.body[i];
                                    // Si la frecuencia no es de 7 días, puede ser 15, 22, 29
                                    // desde la fecha de inicio de la agenda debo buscar
                                    // el primer lunes (si atiende los lunes por ej) activarlo
                                    // y de ahí en adelante ir desactivando si es cada 15 el 7
                                    // si es cada 22 el 7 y el 15
                                    // si es cada 29, el 7, 15 y 22

                                    this.idHorario = dhorario.id;
                                    const sinTurno = [];
                                    for (const name in dhorario) {
                                        switch (name) {
                                            case 'lunes':
                                                dhorario.lunes ? 0 : (sinTurno[1] = 1);
                                                if (dhorario.lunes) {
                                                    this.rangoHorarioXdia(1, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 1, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'martes':
                                                dhorario.martes ? 0 : (sinTurno[2] = 2);
                                                if (dhorario.martes) {
                                                    this.rangoHorarioXdia(2, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 2, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'miercoles':
                                                dhorario.miercoles ? 0 : (sinTurno[3] = 3);
                                                if (dhorario.miercoles) {
                                                    this.rangoHorarioXdia(3, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 3, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'jueves':
                                                dhorario.jueves ? 0 : (sinTurno[4] = 4);
                                                if (dhorario.jueves) {
                                                    this.rangoHorarioXdia(4, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 4, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'viernes':
                                                dhorario.viernes ? 0 : (sinTurno[5] = 5);
                                                if (dhorario.viernes) {
                                                    this.rangoHorarioXdia(5, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 5, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'sabado':
                                                dhorario.sabado ? 0 : (sinTurno[6] = 6);
                                                if (dhorario.sabado) {
                                                    this.rangoHorarioXdia(6, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 6, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                            case 'domingo':
                                                dhorario.domingo ? 0 : (sinTurno[0] = 0);
                                                if (dhorario.domingo) {
                                                    this.rangoHorarioXdia(0, dhorario);
                                                    if (dhorario.frecuencia > 7) {
                                                        this.diasFrecuenciaDesactivar(resH.body[0].fechaDesde, 0, dhorario.frecuencia);
                                                    }
                                                }
                                                break;
                                        }

                                        // Revisar cómo hacerlo por detalle...
                                        this.turnosXhorario = dhorario.cantidadPacientes;
                                    }

                                    if (this.diasSinTurnos.length) {
                                        this.diasSinTurnos = sinTurno.filter(x => this.diasSinTurnos.includes(x));
                                    } else {
                                        this.diasSinTurnos = sinTurno;
                                    }
                                }
                            } else {
                                this.mostrarHorarios = false;
                                this.limpiarCampos();
                                this.msgs.push({
                                    severity: 'warn',
                                    summary: 'Advertencia',
                                    detail: 'El profesional seleccionado no tiene horarios asignados para esa especialidad.'
                                });
                            }
                            // Acá es para filtar los feriados y vacaciones por ej
                            // Se debe poner 1 día más del que se quiere bloquear
                            // this.diasNoValidos = [new Date('2019-05-20')];
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                }

                this.diasNoValidos = [];

                var fechaHoy = new Date().toISOString().split('T')[0];

                this.bloqueosService.obtenerBloqueosDesde(fechaHoy).subscribe((res: HttpResponse<any[]>) => {
                    if (res.body.length > 0) {
                        var bloq = res.body;
                        for (let i = 0; i < bloq.length; i++) {
                            const elemento = bloq[i];
                            var desde = dayjs(elemento.fechaDesde);
                            var hasta = dayjs(elemento.fechaHasta);
                            var resultado = this.diasEntreFechas(desde, hasta);

                            this.diasNoValidos = this.diasNoValidos.concat(resultado);
                        }
                    }
                });
                // console.log(res.body);
            }),
            (res: HttpErrorResponse) => this.onError(res.message);
    }

    public diasEntreFechas(desde, hasta) {
        var dia_actual = desde.add(1, 'days');
        hasta = hasta.add(1, 'days');
        var fechas = [];
        while (dia_actual.isSameOrBefore(hasta)) {
            fechas.push(new Date(dia_actual.format('YYYY-MM-DD')));
            dia_actual.add(1, 'days');
        }
        return fechas;
    }

    public seleccionDia(event) {
        const anio = event.getFullYear();
        const mes = event.getMonth();
        const dia = event.getDate(); // día seleccionado

        this.fechaSeleccionada = new Date(anio, mes, dia);
        this.mostrarHorarios = true;
        // armo el select con los horarios
        this.turnosDelDia = this.horariosXdia[event.getDay()];
        // al seleccionar un día habilitar los campos
        this.habilitarCampos = true;

        // buscamos los horarios ocupados del profesional
        this.turnoService
            .horariosOcupadosProf(
                this.profesional.id,
                this.turno.tur_esp_relId,
                anio + '-' + ('0' + (mes + 1)).slice(-2) + '-' + ('0' + dia).slice(-2)
            )
            .subscribe(
                (res: HttpResponse<any[]>) => {
                    const ocupado = res.body;

                    let arreglo = this.turnosDelDia;
                    const horariosOcup = [];
                    ocupado.forEach(horarios => {
                        const nuevoElem = horarios.split(';');
                        nuevoElem.forEach(e => {
                            horariosOcup.push(e);
                        });
                    });
                    horariosOcup.sort();
                    // creo un arreglo contando por cada horario ocupado ctos horarios hay
                    // ej: 09:00-1:1, 17:00-3:2 y así...
                    const contadorHorariosOcup = horariosOcup.reduce((contadorHorariosOcup, hor) => {
                        contadorHorariosOcup[hor] = (contadorHorariosOcup[hor] || 0) + 1;
                        return contadorHorariosOcup;
                    }, {});

                    // Me quedo con los horarios que no debo dejar tomar por estar ocupados
                    let horXturnoD = new Array(); // new Set();
                    const cho = Object.keys(contadorHorariosOcup);

                    cho.forEach(hc => {
                        const phc = hc.split('-')[1];
                        const sphc = contadorHorariosOcup[hc];

                        // Si está todo ocupado
                        if (phc == sphc) {
                            // horXturnoD.add(hc.split('-')[0]);
                            horXturnoD.push(hc.split('-')[0]);
                        }
                    });
                    // const todos = horXturnoD.values();

                    // for (let ele of todos) {
                    horXturnoD.forEach(function(ele) {
                        const obj = { label: ele, value: ele };
                        arreglo = arreglo.filter((item: { label: any }) => {
                            return item.label !== obj.label; // && item.value !== obj.value
                        });
                    });
                    this.turnosDelDia = arreglo;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        // día de la semana del 0 (domingo) al 6 (lunes)
        // const dia = event.getDay();
    }

    // Si el paciente pide dos horarios consecutivos ==> tomar menor horario de inicio y mayor horario de fin para armar el turno y quitar los dos turnos del combo

    public buscarExistenciaPaciente(event) {
        this.pacienteExistente = 0;
        this.pacienteService.buscarPacienteXDNI(event).subscribe(
            (res: HttpResponse<IPaciente>) => {
                this.pacienteExistente = 1;
                // completar los campos del paciente si existe, si no existe dejarlos en blanco
                this.turno.apellido = res.body.apellidoPaciente;
                this.turno.nombre = res.body.nombrePaciente;
                this.turno.email = res.body.emailPaciente;
                this.turno.telefono = res.body.telefonoPaciente;
                this.obraSocSelecc = res.body.obrasocials;
                //  this.turno.tur_obs_relId = res.body.pacienteObraSocialId;
                //  this.turno.tur_obs_relNombreObraSocial = res.body.pacienteObraSocialNombreObraSocial;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    public limpiarCampos() {
        this.turno.dni = '';
        this.turno.apellido = '';
        this.turno.nombre = '';
        this.turno.telefono = '';
        this.turno.email = '';
        this.fechaSeleccionada = null;
        this.profesional = null;
        this.date14 = null;
        this.profesionalSelec = null;
        this.mostrar = false;
        this.mostrarDias = false;
        this.mostrarHorarios = false;
        this.turnosSeleccionados = null;
        this.habilitarCampos = false;
        this.isSaving = false;
    }

    horarioAtencion(fecha: any) {
        this.franjaHoraria = '';
        this.dia = 0;
        this.dia = new Date(fecha.year, fecha.month, fecha.day).getDay();
        const horarios = this.horariosXdia[this.dia];
        const firstItem = horarios[0];
        const lastItem = horarios[horarios.length - 1];

        this.franjaHoraria = firstItem.label.concat(' - ', lastItem.label);
    }

    // Sólo permito selección de horarios consecutivos.
    seleccionHorarios(event) {
        const valores = event.value + '';
        // el elemento seleccionado con el elemento que ya existe deben tener un intervalo de diferencia (hacia abajo o hacia arriba)

        if (this.turnosSeleccionados.length > 1) {
            // ver si selecciona 2 consecutivos y después el siguiente es anterior al primero
            // ej: 9.10-9.15 y después 9.05 debería dejarlo (la condición actual no lo deja 23/05/19)
            const seleccionados = valores.split(',');
            const ultimoTurnoIngresado = seleccionados[seleccionados.length - 1].split('-');

            const penultimoTurnoIngresado = seleccionados[seleccionados.length - 2].split('-');

            //Obtengo los identificadores de los horarios
            const idHorarioUlt = ultimoTurnoIngresado[3];
            const idHorarioPen = penultimoTurnoIngresado[3];

            // Si el intervalo no corresponde al mismo identificador de horario no puedo dejarlo seleccionar
            if (idHorarioUlt != idHorarioPen) {
                this.turnosSeleccionados.pop();
            }

            // Ordernar los horarios
            const horariosOrdenados = seleccionados.sort();

            // verifico que sean consecutivos
            const primerTurno = horariosOrdenados.shift();
            let primerPosicion = parseInt(primerTurno.split('-')[0]);
            seleccionados.forEach(turno => {
                const siguientePosicion = turno.split('-')[0];

                if (primerPosicion + 1 != parseInt(siguientePosicion)) {
                    this.turnosSeleccionados.pop();
                    return;
                }
                primerPosicion = primerPosicion + 1;
            });
        }
    }

    private getNextDayOfWeek(date, dayOfWeek) {
        const resultDate = new Date(date);
        resultDate.setDate(date.getDate() + (7 + dayOfWeek - date.getDay() + 1) % 7 - 1);
        return resultDate;
    }

    private rangoHorarioXdia(dia, dhorario) {
        // en función del intervalo, tengo la cantidad de turnos para un día
        // guardo en un arreglo por día, un arreglo con los turnos
        const datePipe = new DatePipe('en-US');

        const turnos = [];
        // convierto en date para poder hacer la suma
        const ddesde = new Date(new Date().toDateString() + ' ' + dhorario.horaDesde);
        const dhasta = new Date(new Date().toDateString() + ' ' + dhorario.horaHasta);
        const ddhasta = datePipe.transform(dhasta, 'HH:mm');
        let z = ddesde;
        let x = 0;
        if (this.horariosXdia[dia] !== undefined) {
            x = this.horariosXdia[dia].length;
        }
        turnos.push({
            label: datePipe.transform(z, 'HH:mm'),
            value: x + '-' + dhorario.intervalo + '-' + datePipe.transform(z, 'HH:mm') + '-' + dhorario.cantidadPacientes
        });
        // horarios del turno
        while (datePipe.transform(z, 'HH:mm') <= ddhasta) {
            const interv = dhorario.intervalo;
            z = new Date(z.getTime() + interv * 60000);
            x++;
            // turnos.push({ label: datePipe.transform(z, 'HH:mm'), value: datePipe.transform(z, 'HH:mm') });
            turnos.push({
                label: datePipe.transform(z, 'HH:mm'),
                value: x + '-' + interv + '-' + datePipe.transform(z, 'HH:mm') + '-' + dhorario.cantidadPacientes
            });
        }

        if (this.horariosXdia[dia] !== undefined) {
            this.horariosXdia[dia] = this.horariosXdia[dia].concat(turnos);
        } else {
            this.horariosXdia[dia] = turnos;
        }
    }

    /**
     * Función en donde marcamos los días que deben desactivarse en función de la
     * frecuencia seleccionada.
     *
     * @param fechaDesdeDetalle
     * @param nroDiaDeLaSemana
     * @param frecuencia
     */
    private diasFrecuenciaDesactivar(fechaDesdeDetalle, nroDiaDeLaSemana: number, frecuencia: number) {
        const proximoDia = dayjs(this.getNextDayOfWeek(fechaDesdeDetalle.toDate(), nroDiaDeLaSemana));
        let fechaInicio = fechaDesdeDetalle;
        let frecDesactiva = [7];
        if (frecuencia === 22) {
            frecDesactiva = [7, 15];
        }
        const ultimoDia = dayjs()
            .add(5, 'M')
            .endOf('month');

        while (fechaInicio.isBefore(ultimoDia)) {
            for (let p = 0; p < frecDesactiva.length; p++) {
                if (fechaInicio === fechaDesdeDetalle) {
                    fechaInicio = proximoDia.add(frecDesactiva[p], 'd');
                } else {
                    fechaInicio = fechaInicio.add(frecDesactiva[p] * 2, 'd');
                }
                const fm = fechaInicio.toDate();
                const mes = fm.getMonth() + 1;

                if (fechaInicio.isBefore(ultimoDia)) {
                    this.diasNoValidos.push(fechaInicio.toDate());
                } else {
                    break;
                }
            }
        }
    }
}
