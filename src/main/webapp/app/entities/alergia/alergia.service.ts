import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlergia } from 'app/shared/model/alergia.model';

type EntityResponseType = HttpResponse<IAlergia>;
type EntityArrayResponseType = HttpResponse<IAlergia[]>;

@Injectable({ providedIn: 'root' })
export class AlergiaService {
    private resourceUrl = SERVER_API_URL + 'api/alergias';

    constructor(private http: HttpClient) {}

    create(alergia: IAlergia): Observable<EntityResponseType> {
        return this.http.post<IAlergia>(this.resourceUrl, alergia, { observe: 'response' });
    }

    update(alergia: IAlergia): Observable<EntityResponseType> {
        return this.http.put<IAlergia>(this.resourceUrl, alergia, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAlergia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAlergia[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
