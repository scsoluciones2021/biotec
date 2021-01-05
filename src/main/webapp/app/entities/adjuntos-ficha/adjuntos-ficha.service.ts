import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';

type EntityResponseType = HttpResponse<IAdjuntosFicha>;
type EntityArrayResponseType = HttpResponse<IAdjuntosFicha[]>;

@Injectable({ providedIn: 'root' })
export class AdjuntosFichaService {
    private resourceUrl = SERVER_API_URL + 'api/adjuntos-fichas';

    constructor(private http: HttpClient) {}

    create(adjuntos_ficha: IAdjuntosFicha): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(adjuntos_ficha);
        return this.http
            .post<IAdjuntosFicha>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(adjuntos_ficha: IAdjuntosFicha): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(adjuntos_ficha);
        return this.http
            .put<IAdjuntosFicha>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAdjuntosFicha>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAdjuntosFicha[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(adjuntos_ficha: IAdjuntosFicha): IAdjuntosFicha {
        const copy: IAdjuntosFicha = Object.assign({}, adjuntos_ficha, {
            fecha: adjuntos_ficha.fecha != null && adjuntos_ficha.fecha.isValid() ? adjuntos_ficha.fecha.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((adjuntos_ficha: IAdjuntosFicha) => {
            adjuntos_ficha.fecha = adjuntos_ficha.fecha != null ? moment(adjuntos_ficha.fecha) : null;
        });
        return res;
    }
}
