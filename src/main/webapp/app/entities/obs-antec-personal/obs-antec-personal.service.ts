import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';

type EntityResponseType = HttpResponse<IObsAntecPersonal>;
type EntityArrayResponseType = HttpResponse<IObsAntecPersonal[]>;

@Injectable({ providedIn: 'root' })
export class ObsAntecPersonalService {
    private resourceUrl = SERVER_API_URL + 'api/obs-antec-personals';

    constructor(private http: HttpClient) {}

    create(obsAntecPersonal: IObsAntecPersonal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obsAntecPersonal);
        return this.http
            .post<IObsAntecPersonal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(obsAntecPersonal: IObsAntecPersonal): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(obsAntecPersonal);
        return this.http
            .put<IObsAntecPersonal>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IObsAntecPersonal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IObsAntecPersonal[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(obsAntecPersonal: IObsAntecPersonal): IObsAntecPersonal {
        const copy: IObsAntecPersonal = Object.assign({}, obsAntecPersonal, {
            fecha: obsAntecPersonal.fecha != null && obsAntecPersonal.fecha.isValid() ? obsAntecPersonal.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((obsAntecPersonal: IObsAntecPersonal) => {
            obsAntecPersonal.fecha = obsAntecPersonal.fecha != null ? moment(obsAntecPersonal.fecha) : null;
        });
        return res;
    }
}
