import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

type EntityResponseType = HttpResponse<IAntecedentesPersonales>;
type EntityArrayResponseType = HttpResponse<IAntecedentesPersonales[]>;

@Injectable({ providedIn: 'root' })
export class AntecedentesPersonalesService {
    private resourceUrl = SERVER_API_URL + 'api/antecedentes-personales';

    constructor(private http: HttpClient) {}

    create(antecedentesPersonales: IAntecedentesPersonales): Observable<EntityResponseType> {
        return this.http.post<IAntecedentesPersonales>(this.resourceUrl, antecedentesPersonales, { observe: 'response' });
    }

    update(antecedentesPersonales: IAntecedentesPersonales): Observable<EntityResponseType> {
        return this.http.put<IAntecedentesPersonales>(this.resourceUrl, antecedentesPersonales, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAntecedentesPersonales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAntecedentesPersonales[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
