import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBloqueos } from 'app/shared/model/bloqueos.model';

type EntityResponseType = HttpResponse<IBloqueos>;
type EntityArrayResponseType = HttpResponse<IBloqueos[]>;

@Injectable({ providedIn: 'root' })
export class BloqueosService {
    private resourceUrl = SERVER_API_URL + 'api/bloqueos';

    constructor(private http: HttpClient) {}

    create(bloqueos: IBloqueos): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bloqueos);
        return this.http
            .post<IBloqueos>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bloqueos: IBloqueos): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bloqueos);
        return this.http
            .put<IBloqueos>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBloqueos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBloqueos[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(bloqueos: IBloqueos): IBloqueos {
        const copy: IBloqueos = Object.assign({}, bloqueos, {
            fechaDesde: bloqueos.fechaDesde != null && bloqueos.fechaDesde.isValid() ? bloqueos.fechaDesde.format(DATE_FORMAT) : null,
            fechaHasta: bloqueos.fechaHasta != null && bloqueos.fechaHasta.isValid() ? bloqueos.fechaHasta.format(DATE_FORMAT) : null,
            horaDesde: bloqueos.horaDesde != null && bloqueos.horaDesde.isValid() ? bloqueos.horaDesde.toJSON() : null,
            horaHasta: bloqueos.horaHasta != null && bloqueos.horaHasta.isValid() ? bloqueos.horaHasta.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaDesde = res.body.fechaDesde != null ? moment(res.body.fechaDesde) : null;
        res.body.fechaHasta = res.body.fechaHasta != null ? moment(res.body.fechaHasta) : null;
        res.body.horaDesde = res.body.horaDesde != null ? moment(res.body.horaDesde) : null;
        res.body.horaHasta = res.body.horaHasta != null ? moment(res.body.horaHasta) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((bloqueos: IBloqueos) => {
            bloqueos.fechaDesde = bloqueos.fechaDesde != null ? moment(bloqueos.fechaDesde) : null;
            bloqueos.fechaHasta = bloqueos.fechaHasta != null ? moment(bloqueos.fechaHasta) : null;
            bloqueos.horaDesde = bloqueos.horaDesde != null ? moment(bloqueos.horaDesde) : null;
            bloqueos.horaHasta = bloqueos.horaHasta != null ? moment(bloqueos.horaHasta) : null;
        });
        return res;
    }
}
