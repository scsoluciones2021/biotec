import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

type EntityResponseType = HttpResponse<IIntolerancia>;
type EntityArrayResponseType = HttpResponse<IIntolerancia[]>;

@Injectable({ providedIn: 'root' })
export class IntoleranciaService {
    private resourceUrl = SERVER_API_URL + 'api/intolerancias';

    constructor(private http: HttpClient) {}

    create(intolerancia: IIntolerancia): Observable<EntityResponseType> {
        return this.http.post<IIntolerancia>(this.resourceUrl, intolerancia, { observe: 'response' });
    }

    update(intolerancia: IIntolerancia): Observable<EntityResponseType> {
        return this.http.put<IIntolerancia>(this.resourceUrl, intolerancia, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIntolerancia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIntolerancia[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
