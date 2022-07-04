import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';

type EntityResponseType = HttpResponse<ICategoriascie10>;
type EntityArrayResponseType = HttpResponse<ICategoriascie10[]>;

@Injectable({ providedIn: 'root' })
export class Categoriascie10Service {
    public resourceUrl = SERVER_API_URL + 'api/categoriascie10';

    constructor(protected http: HttpClient) {}

    create(categoriascie10: ICategoriascie10): Observable<EntityResponseType> {
        return this.http.post<ICategoriascie10>(this.resourceUrl, categoriascie10, { observe: 'response' });
    }

    update(categoriascie10: ICategoriascie10): Observable<EntityResponseType> {
        return this.http.put<ICategoriascie10>(this.resourceUrl, categoriascie10, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICategoriascie10>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICategoriascie10[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
