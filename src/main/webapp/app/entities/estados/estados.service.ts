import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstados } from 'app/shared/model/estados.model';

type EntityResponseType = HttpResponse<IEstados>;
type EntityArrayResponseType = HttpResponse<IEstados[]>;

@Injectable({ providedIn: 'root' })
export class EstadosService {
    private resourceUrl = SERVER_API_URL + 'api/estados';

    constructor(private http: HttpClient) {}

    create(estados: IEstados): Observable<EntityResponseType> {
        return this.http.post<IEstados>(this.resourceUrl, estados, { observe: 'response' });
    }

    update(estados: IEstados): Observable<EntityResponseType> {
        return this.http.put<IEstados>(this.resourceUrl, estados, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstados>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstados[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
