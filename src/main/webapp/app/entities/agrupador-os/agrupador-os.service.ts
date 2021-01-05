import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgrupadorOS } from 'app/shared/model/agrupador-os.model';

type EntityResponseType = HttpResponse<IAgrupadorOS>;
type EntityArrayResponseType = HttpResponse<IAgrupadorOS[]>;

@Injectable({ providedIn: 'root' })
export class AgrupadorOSService {
    private resourceUrl = SERVER_API_URL + 'api/agrupador-os';

    constructor(private http: HttpClient) {}

    create(agrupadorOS: IAgrupadorOS): Observable<EntityResponseType> {
        return this.http.post<IAgrupadorOS>(this.resourceUrl, agrupadorOS, { observe: 'response' });
    }

    update(agrupadorOS: IAgrupadorOS): Observable<EntityResponseType> {
        return this.http.put<IAgrupadorOS>(this.resourceUrl, agrupadorOS, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAgrupadorOS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAgrupadorOS[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
