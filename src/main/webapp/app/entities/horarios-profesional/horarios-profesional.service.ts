import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as dayjs from 'dayjs';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';

type EntityResponseType = HttpResponse<IHorariosProfesional>;
type EntityArrayResponseType = HttpResponse<IHorariosProfesional[]>;

@Injectable({ providedIn: 'root' })
export class HorariosProfesionalService {
    private resourceUrl = SERVER_API_URL + 'api/horarios-profesionals';

    constructor(private http: HttpClient) {}

    create(horariosProfesional: IHorariosProfesional): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(horariosProfesional);
        return this.http
            .post<IHorariosProfesional>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(horariosProfesional: IHorariosProfesional): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(horariosProfesional);
        return this.http
            .put<IHorariosProfesional>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHorariosProfesional>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHorariosProfesional[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(horariosProfesional: IHorariosProfesional): IHorariosProfesional {
        const copy: IHorariosProfesional = Object.assign({}, horariosProfesional, {
            fechaDesde:
                horariosProfesional.fechaDesde != null && horariosProfesional.fechaDesde.isValid()
                    ? horariosProfesional.fechaDesde.format(DATE_FORMAT)
                    : null,
            fechaHasta:
                horariosProfesional.fechaHasta != null && horariosProfesional.fechaHasta.isValid()
                    ? horariosProfesional.fechaHasta.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaDesde = res.body.fechaDesde != null ? dayjs(res.body.fechaDesde) : null;
        res.body.fechaHasta = res.body.fechaHasta != null ? dayjs(res.body.fechaHasta) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((horariosProfesional: IHorariosProfesional) => {
            horariosProfesional.fechaDesde = horariosProfesional.fechaDesde != null ? dayjs(horariosProfesional.fechaDesde) : null;
            horariosProfesional.fechaHasta = horariosProfesional.fechaHasta != null ? dayjs(horariosProfesional.fechaHasta) : null;
        });
        return res;
    }

    // Métodos adicionales
    // Búsqueda de horarios por profesional
    horarioProfesional(idProfesional: number, idEspecialidad: number): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('idProfesional', idProfesional.toString()).set('idEspecialidad', idEspecialidad.toString());
        return this.http
            .get<IHorariosProfesional[]>(`${this.resourceUrl}/hor-profesional`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    searchHorarios(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('nombre', req.query[0]).set('especialidad', req.query[1]);

        return this.http.get<IHorariosProfesional[]>(`${this.resourceUrl}/busqueda-general`, { params: options, observe: 'response' });
    }
}
