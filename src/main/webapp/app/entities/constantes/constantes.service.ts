import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConstantes } from 'app/shared/model/constantes.model';

type EntityResponseType = HttpResponse<IConstantes>;
type EntityArrayResponseType = HttpResponse<IConstantes[]>;

@Injectable({ providedIn: 'root' })
export class ConstantesService {
    private resourceUrl = SERVER_API_URL + 'api/constantes';

    constructor(private http: HttpClient) {}

    create(constantes: IConstantes): Observable<EntityResponseType> {
        return this.http.post<IConstantes>(this.resourceUrl, constantes, { observe: 'response' });
    }

    update(constantes: IConstantes): Observable<EntityResponseType> {
        return this.http.put<IConstantes>(this.resourceUrl, constantes, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConstantes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConstantes[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
