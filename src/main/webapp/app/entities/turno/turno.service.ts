import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurno } from 'app/shared/model/turno.model';
import { ITurnoProf } from '../../shared/model/turno-profesional.model';

type EntityResponseType = HttpResponse<ITurno>;
type EntityArrayResponseType = HttpResponse<ITurno[]>;

@Injectable({ providedIn: 'root' })
export class TurnoService {
    private resourceUrl = SERVER_API_URL + 'api/turnos';

    constructor(private http: HttpClient) {}

    create(turno: ITurno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turno);

        return this.http
            .post<ITurno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(turno: ITurno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(turno);
        return this.http
            .put<ITurno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITurno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITurno[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(turno: ITurno): ITurno {
        const copy: ITurno = Object.assign({}, turno, {
            dia: turno.dia != null && turno.dia.isValid() ? turno.dia.format(DATE_FORMAT) : null,
            hora: turno.hora != null && turno.hora.isValid() ? turno.hora.format('YYYY-MM-DDTHH:mm') : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dia = res.body.dia != null ? moment(res.body.dia) : null;
        res.body.hora = res.body.hora != null ? moment(res.body.hora) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((turno: ITurno) => {
            turno.dia = turno.dia != null ? moment(turno.dia) : null;
            turno.hora = turno.hora != null ? moment(turno.hora) : null;
        });
        return res;
    }

    // Agregados

    public horariosOcupadosProf(idProf?: any, idEsp?: any, dia?: any): Observable<any> {
        const options = new HttpParams()
            .set('idProf', idProf)
            .set('idEsp', idEsp)
            .set('dia', dia);
        return this.http.get<any[]>(`${this.resourceUrl}/ocup-prof`, { params: options, observe: 'response' });
    }

    busquedaConFiltros(req?: any): Observable<EntityArrayResponseType> {
        if (req.query[6] === 'undefined') {
            req.query[6] = -1;
        }

        const options = new HttpParams()
            .set('query', req.query)
            .set('espSel', req.query[4])
            .set('profSel', req.query[5]);

        return this.http.get<ITurno[]>(`${this.resourceUrl}/busqueda-filtros`, { params: options, observe: 'response' });
    }

    busquedaConFiltrosImp(req?: any): Observable<EntityArrayResponseType> {
        if (req.query[6] === undefined) {
            req.query[6] = -1;
        }
        const options = new HttpParams()
            .set('query', req.query)
            .set('espSel', req.query[4])
            .set('profSel', req.query[5]);

        return this.http.get<ITurno[]>(`${this.resourceUrl}/impresion`, { params: options, observe: 'response' });
    }

    cambiarEstado(id: number, estado: number): Observable<HttpResponse<any>> {
        const options = new HttpParams().set('id', id.toString()).set('estado', estado.toString());
        return this.http.get<any>(`${this.resourceUrl}/modificar-estado`, { params: options, observe: 'response' });
    }

    busquedaMedico(req?: any): Observable<EntityArrayResponseType> {
        const turnoProf: ITurnoProf = req['query'];
        const copy = this.convertDateFromClientP(turnoProf);
        return this.http.post<ITurno[]>(`${this.resourceUrl}/busqueda-medico`, copy, { observe: 'response' });
    }

    private convertDateFromClientP(turno: ITurnoProf): ITurnoProf {
        const copy: ITurnoProf = Object.assign({}, turno, {
            fecha: turno.fecha != null && turno.fecha.isValid() ? turno.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }
}
