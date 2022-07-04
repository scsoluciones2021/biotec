import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

type EntityResponseType = HttpResponse<IDiagnosticoscie10>;
type EntityArrayResponseType = HttpResponse<IDiagnosticoscie10[]>;

@Injectable({ providedIn: 'root' })
export class Diagnosticoscie10Service {
    public resourceUrl = SERVER_API_URL + 'api/diagnosticoscie10';

    constructor(protected http: HttpClient) {}

    create(diagnosticoscie10: IDiagnosticoscie10): Observable<EntityResponseType> {
        return this.http.post<IDiagnosticoscie10>(this.resourceUrl, diagnosticoscie10, { observe: 'response' });
    }

    update(diagnosticoscie10: IDiagnosticoscie10): Observable<EntityResponseType> {
        return this.http.put<IDiagnosticoscie10>(this.resourceUrl, diagnosticoscie10, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDiagnosticoscie10>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDiagnosticoscie10[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
