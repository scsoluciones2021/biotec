import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';

type EntityResponseType = HttpResponse<ISubgruposcie10>;
type EntityArrayResponseType = HttpResponse<ISubgruposcie10[]>;

@Injectable({ providedIn: 'root' })
export class Subgruposcie10Service {
    private resourceUrl = SERVER_API_URL + 'api/subgruposcie10';

    constructor(private http: HttpClient) {}

    create(subgruposcie10: ISubgruposcie10): Observable<EntityResponseType> {
        return this.http.post<ISubgruposcie10>(this.resourceUrl, subgruposcie10, { observe: 'response' });
    }

    update(subgruposcie10: ISubgruposcie10): Observable<EntityResponseType> {
        return this.http.put<ISubgruposcie10>(this.resourceUrl, subgruposcie10, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISubgruposcie10>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISubgruposcie10[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
