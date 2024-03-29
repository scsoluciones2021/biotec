import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITituloShort } from 'app/shared/model/titulo-short.model';

type EntityResponseType = HttpResponse<ITituloShort>;
type EntityArrayResponseType = HttpResponse<ITituloShort[]>;

@Injectable({ providedIn: 'root' })
export class TituloShortService {
    private resourceUrl = SERVER_API_URL + 'api/titulo-shorts';

    constructor(private http: HttpClient) {}

    create(tituloShort: ITituloShort): Observable<EntityResponseType> {
        return this.http.post<ITituloShort>(this.resourceUrl, tituloShort, { observe: 'response' });
    }

    update(tituloShort: ITituloShort): Observable<EntityResponseType> {
        return this.http.put<ITituloShort>(this.resourceUrl, tituloShort, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITituloShort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITituloShort[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
