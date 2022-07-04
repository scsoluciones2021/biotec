import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpRequest, HttpEvent, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as dayjs from 'dayjs';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, tap } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFicha } from 'app/shared/model/ficha.model';

type EntityResponseType = HttpResponse<IFicha>;
type EntityArrayResponseType = HttpResponse<IFicha[]>;

@Injectable({ providedIn: 'root' })
export class FichaService {
    private resourceUrl = SERVER_API_URL + 'api/fichas';
    // private resourceSearchUrl = SERVER_API_URL + 'api/_search/fichas';
    constructor(private http: HttpClient) {}

    create(ficha: IFicha): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ficha);
        return this.http
            .post<IFicha>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(ficha: IFicha): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ficha);
        return this.http
            .put<IFicha>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFicha>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFicha[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(ficha: IFicha): IFicha {
        const copy: IFicha = Object.assign({}, ficha, {
            fechaIngreso: ficha.fechaIngreso != null && ficha.fechaIngreso.isValid() ? ficha.fechaIngreso.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaIngreso = res.body.fechaIngreso != null ? dayjs(res.body.fechaIngreso) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((ficha: IFicha) => {
            ficha.fechaIngreso = ficha.fechaIngreso != null ? dayjs(ficha.fechaIngreso) : null;
        });
        return res;
    }

    pushFileToStorage(archivos: File, ficha: IFicha): Observable<HttpEvent<{}>> {
        const formdata: FormData = new FormData();
        formdata.append('fileUpload', archivos);
        formdata.append('idFicha', ficha.id.toString());

        const req = new HttpRequest('POST', `${this.resourceUrl}/post`, formdata, {
            reportProgress: true,
            responseType: 'text'
        });
        return this.http.request(req);
    }

    getFiles(id: number): Observable<any> {
        return this.http.get<any>(`${this.resourceUrl}/getallfiles/${id}`);
    }

    getFile(nombreArchivo: String): Observable<String> {
        const ruta = `${this.resourceUrl}/files/${nombreArchivo}`;
        return this.http.get<any>(ruta);
    }

    busquedaConFiltros(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams()
            .set('apellido', req.query[0])
            .set('nombre', req.query[1])
            .set('profesionales', req.query[2])
            .set('especialidades', req.query[3]);
        return this.http.get<IFicha[]>(`${this.resourceUrl}/filtros`, { params: options, observe: 'response' });
    }

    getFilesName(id: number): Observable<String[]> {
        return this.http.get<String[]>(`${this.resourceUrl}/getallfiles/${id}`);
    }

    existeFichaIdPac(id: number): Observable<HttpResponse<any>> {
        return this.http.get<any>(`${this.resourceUrl}/busquedaXIdPac/${id}`, { observe: 'response' });
    }

    public downloadExcelFile() {
        const url = 'http://localhost:8080/upload/Fichas';
        const encodedAuth = window.localStorage.getItem('encodedAuth');

        return this.http
            .get(url, {
                headers: new HttpHeaders({
                    Authorization: 'Basic ' + encodedAuth,
                    'Content-Type': 'application/octet-stream'
                }),
                responseType: 'blob'
            })
            .pipe(
                tap(
                    // Log the result or error
                    data => console.log('You received data'),
                    error => console.log(error)
                )
            );
    }
}
