import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as dayjs from 'dayjs';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';

type EntityResponseType = HttpResponse<IObsAntecFamiliar>;
type EntityArrayResponseType = HttpResponse<IObsAntecFamiliar[]>;

@Injectable({ providedIn: 'root' })
export class ObsAntecFamiliarService {
    private resourceUrl = SERVER_API_URL + 'api/obs-antec-familiars';

    constructor(private http: HttpClient) {}

    create(obsAntecFamiliar: IObsAntecFamiliar): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obsAntecFamiliar);
        return this.http
            .post<IObsAntecFamiliar>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(obsAntecFamiliar: IObsAntecFamiliar): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obsAntecFamiliar);
        return this.http
            .put<IObsAntecFamiliar>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IObsAntecFamiliar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IObsAntecFamiliar[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(obsAntecFamiliar: IObsAntecFamiliar): IObsAntecFamiliar {
        const copy: IObsAntecFamiliar = Object.assign({}, obsAntecFamiliar, {
            fecha: obsAntecFamiliar.fecha != null && obsAntecFamiliar.fecha.isValid() ? obsAntecFamiliar.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? dayjs(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((obsAntecFamiliar: IObsAntecFamiliar) => {
            obsAntecFamiliar.fecha = obsAntecFamiliar.fecha != null ? dayjs(obsAntecFamiliar.fecha) : null;
        });
        return res;
    }
}
