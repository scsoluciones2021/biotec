import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';

type EntityResponseType = HttpResponse<ICodigoPostal>;
type EntityArrayResponseType = HttpResponse<ICodigoPostal[]>;

@Injectable({ providedIn: 'root' })
export class CodigoPostalService {
    private resourceUrl = SERVER_API_URL + 'api/codigo-postals';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/codigo-postals';
    private resourceSearchUrl2 = SERVER_API_URL + 'api/_search/codigo-postals/buscarIndividual';

    constructor(private http: HttpClient) {}

    create(codigoPostal: ICodigoPostal): Observable<EntityResponseType> {
        return this.http.post<ICodigoPostal>(this.resourceUrl, codigoPostal, { observe: 'response' });
    }

    update(codigoPostal: ICodigoPostal): Observable<EntityResponseType> {
        return this.http.put<ICodigoPostal>(this.resourceUrl, codigoPostal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICodigoPostal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICodigoPostal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICodigoPostal[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }

     public buscarCP(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('cpStr', req);
        return this.http.get<ICodigoPostal[]>(this.resourceSearchUrl2, { params: options, observe: 'response' });
    }

    public buscarCPProvincia(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('idProvincia', req);
        return this.http.get<ICodigoPostal[]>(`${this.resourceUrl}/Provincia`, { params: options, observe: 'response' });
    }
}
