import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegimen } from 'app/shared/model/regimen.model';

type EntityResponseType = HttpResponse<IRegimen>;
type EntityArrayResponseType = HttpResponse<IRegimen[]>;

@Injectable({ providedIn: 'root' })
export class RegimenService {
    private resourceUrl = SERVER_API_URL + 'api/regimen';

    constructor(private http: HttpClient) {}

    create(regimen: IRegimen): Observable<EntityResponseType> {
        return this.http.post<IRegimen>(this.resourceUrl, regimen, { observe: 'response' });
    }

    update(regimen: IRegimen): Observable<EntityResponseType> {
        return this.http.put<IRegimen>(this.resourceUrl, regimen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegimen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegimen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
