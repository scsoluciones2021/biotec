import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPagoConsulta } from 'app/shared/model/pago-consulta.model';

type EntityResponseType = HttpResponse<IPagoConsulta>;
type EntityArrayResponseType = HttpResponse<IPagoConsulta[]>;

@Injectable({ providedIn: 'root' })
export class PagoConsultaService {
    public resourceUrl = SERVER_API_URL + 'api/pago-consultas';

    constructor(protected http: HttpClient) {}

    create(pagoConsulta: IPagoConsulta): Observable<EntityResponseType> {
        return this.http.post<IPagoConsulta>(this.resourceUrl, pagoConsulta, { observe: 'response' });
    }

    update(pagoConsulta: IPagoConsulta): Observable<EntityResponseType> {
        return this.http.put<IPagoConsulta>(this.resourceUrl, pagoConsulta, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPagoConsulta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPagoConsulta[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    buscarFiltros(req?: any): Observable<EntityArrayResponseType> {
        if (req.query[6] === 'undefined') {
            req.query[6] = -1;
        }

        const options = new HttpParams()
            .set('query', req.query)
            .set('espSel', req.query[4])
            .set('profSel', req.query[5]);

        return this.http.get<IPagoConsulta[]>(`${this.resourceUrl}/filtros`, { params: options, observe: 'response' });
    }
}
