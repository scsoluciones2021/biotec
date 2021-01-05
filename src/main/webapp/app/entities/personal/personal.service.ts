import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonal } from 'app/shared/model/personal.model';

type EntityResponseType = HttpResponse<IPersonal>;
type EntityArrayResponseType = HttpResponse<IPersonal[]>;

@Injectable({ providedIn: 'root' })
export class PersonalService {
    private resourceUrl = SERVER_API_URL + 'api/personals';

    constructor(private http: HttpClient) {}

    create(personal: IPersonal): Observable<EntityResponseType> {
        return this.http.post<IPersonal>(this.resourceUrl, personal, { observe: 'response' });
    }

    update(personal: IPersonal): Observable<EntityResponseType> {
        return this.http.put<IPersonal>(this.resourceUrl, personal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPersonal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPersonal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
