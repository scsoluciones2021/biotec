import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalendario } from 'app/shared/model/calendario.model';

type EntityResponseType = HttpResponse<ICalendario>;
type EntityArrayResponseType = HttpResponse<ICalendario[]>;

@Injectable({ providedIn: 'root' })
export class CalendarioService {
    private resourceUrl = SERVER_API_URL + 'api/calendarios';

    constructor(private http: HttpClient) {}

    create(calendario: ICalendario): Observable<EntityResponseType> {
        return this.http.post<ICalendario>(this.resourceUrl, calendario, { observe: 'response' });
    }

    update(calendario: ICalendario): Observable<EntityResponseType> {
        return this.http.put<ICalendario>(this.resourceUrl, calendario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICalendario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICalendario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
