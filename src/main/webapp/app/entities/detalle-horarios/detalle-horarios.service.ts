import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';

type EntityResponseType = HttpResponse<IDetalleHorarios>;
type EntityArrayResponseType = HttpResponse<IDetalleHorarios[]>;

@Injectable({ providedIn: 'root' })
export class DetalleHorariosService {
    private resourceUrl = SERVER_API_URL + 'api/detalle-horarios';

    constructor(private http: HttpClient) {}

    create(detalleHorarios: IDetalleHorarios): Observable<EntityResponseType> {
        return this.http.post<IDetalleHorarios>(this.resourceUrl, detalleHorarios, { observe: 'response' });
    }

    update(detalleHorarios: IDetalleHorarios): Observable<EntityResponseType> {
        return this.http.put<IDetalleHorarios>(this.resourceUrl, detalleHorarios, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDetalleHorarios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleHorarios[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    buscarXHorario(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleHorarios[]>(`${this.resourceUrl}/buscarXHorario`, { params: options, observe: 'response' });
    }
}
