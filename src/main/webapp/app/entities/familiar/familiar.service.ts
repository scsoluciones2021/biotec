import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFamiliar } from 'app/shared/model/familiar.model';

type EntityResponseType = HttpResponse<IFamiliar>;
type EntityArrayResponseType = HttpResponse<IFamiliar[]>;

@Injectable({ providedIn: 'root' })
export class FamiliarService {
    private resourceUrl = SERVER_API_URL + 'api/familiars';

    constructor(private http: HttpClient) {}

    create(familiar: IFamiliar): Observable<EntityResponseType> {
        return this.http.post<IFamiliar>(this.resourceUrl, familiar, { observe: 'response' });
    }

    update(familiar: IFamiliar): Observable<EntityResponseType> {
        return this.http.put<IFamiliar>(this.resourceUrl, familiar, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFamiliar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFamiliar[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
