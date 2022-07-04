import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';

type EntityResponseType = HttpResponse<IGruposcie10>;
type EntityArrayResponseType = HttpResponse<IGruposcie10[]>;

@Injectable({ providedIn: 'root' })
export class Gruposcie10Service {
    public resourceUrl = SERVER_API_URL + 'api/gruposcie10';

    constructor(protected http: HttpClient) {}

    create(gruposcie10: IGruposcie10): Observable<EntityResponseType> {
        return this.http.post<IGruposcie10>(this.resourceUrl, gruposcie10, { observe: 'response' });
    }

    update(gruposcie10: IGruposcie10): Observable<EntityResponseType> {
        return this.http.put<IGruposcie10>(this.resourceUrl, gruposcie10, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGruposcie10>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGruposcie10[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
