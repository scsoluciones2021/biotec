import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfesional, IProfesionalTurno } from 'app/shared/model/profesional.model';
import { CpsjEntityModule } from '../entity.module';

type EntityResponseType = HttpResponse<IProfesional>;
type EntityArrayResponseType = HttpResponse<IProfesional[]>;

@Injectable({ providedIn: 'root' })
export class ProfesionalService {
    private resourceUrl = SERVER_API_URL + 'api/profesionals';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/profesionals';
    private resourceSearchUrl2 = SERVER_API_URL + 'api/_search/profesionals/busquedaIndividual';
    private resourceSearchUrl3 = SERVER_API_URL + 'api/_search/profesionals/busquedaTotalCustom';

    constructor(private http: HttpClient) {}

    create(profesional: IProfesional): Observable<EntityResponseType> {
        return this.http.post<IProfesional>(this.resourceUrl, profesional, { observe: 'response' });
    }

    update(profesional: IProfesional): Observable<EntityResponseType> {
        return this.http.put<IProfesional>(this.resourceUrl, profesional, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProfesional>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfesional[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    // Creado para buscar profesionales en el general de profesionales
    searchProfesional(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfesional[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }

    buscarProfesional(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('nombreStr', req);
        return this.http.get<IProfesional[]>(this.resourceSearchUrl2, { params: options, observe: 'response' });
    }

    // VER QUE TIENE QUE DEVOLVER -- Es para la selección de turnos (según la especialidad)
    buscarTodosArray(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfesionalTurno[]>(`${this.resourceSearchUrl}/todosTurno`, { params: options, observe: 'response' });
    }

    // Para buscar todos los profesionales sin paginación
    todos(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProfesional[]>(`${this.resourceUrl}/todos`, { params: options, observe: 'response' });
    }

    buscarPorUsuario(id: number): Observable<EntityResponseType> {
        return this.http.get<IProfesional>(`${this.resourceUrl}/usuario/${id}`, { observe: 'response' });
    }
}
