import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBebida } from 'app/shared/model/bebida.model';

type EntityResponseType = HttpResponse<IBebida>;
type EntityArrayResponseType = HttpResponse<IBebida[]>;

@Injectable({ providedIn: 'root' })
export class BebidaService {
    private resourceUrl = SERVER_API_URL + 'api/bebidas';

    constructor(private http: HttpClient) {}

    create(bebida: IBebida): Observable<EntityResponseType> {
        return this.http.post<IBebida>(this.resourceUrl, bebida, { observe: 'response' });
    }

    update(bebida: IBebida): Observable<EntityResponseType> {
        return this.http.put<IBebida>(this.resourceUrl, bebida, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBebida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBebida[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
