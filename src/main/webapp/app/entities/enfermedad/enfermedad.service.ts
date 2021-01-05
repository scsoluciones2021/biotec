import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnfermedad } from 'app/shared/model/enfermedad.model';

type EntityResponseType = HttpResponse<IEnfermedad>;
type EntityArrayResponseType = HttpResponse<IEnfermedad[]>;

@Injectable({ providedIn: 'root' })
export class EnfermedadService {
    private resourceUrl = SERVER_API_URL + 'api/enfermedads';

    constructor(private http: HttpClient) {}

    create(enfermedad: IEnfermedad): Observable<EntityResponseType> {
        return this.http.post<IEnfermedad>(this.resourceUrl, enfermedad, { observe: 'response' });
    }

    update(enfermedad: IEnfermedad): Observable<EntityResponseType> {
        return this.http.put<IEnfermedad>(this.resourceUrl, enfermedad, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnfermedad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnfermedad[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
