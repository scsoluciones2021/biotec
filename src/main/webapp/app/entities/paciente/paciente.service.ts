import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams  } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaciente } from 'app/shared/model/paciente.model';

type EntityResponseType = HttpResponse<IPaciente>;
type EntityArrayResponseType = HttpResponse<IPaciente[]>;

@Injectable({ providedIn: 'root' })
export class PacienteService {
    private resourceUrl = SERVER_API_URL + 'api/pacientes';

    constructor(private http: HttpClient) {}

    create(paciente: IPaciente): Observable<EntityResponseType> {
        return this.http.post<IPaciente>(this.resourceUrl, paciente, { observe: 'response' });
    }

    update(paciente: IPaciente): Observable<EntityResponseType> {
        return this.http.put<IPaciente>(this.resourceUrl, paciente, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPaciente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPaciente[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    /* Creado para buscar pacientes en el general de pacientes
    searchPaciente(reqA?: any, reqN?: any, reqD? ): Observable<EntityArrayResponseType> {
        const options = reqA + reqN + reqD;//createRequestOption(reqA) & createRequestOption(reqN) & createRequestOption(reqD);
        return this.http.get<IPaciente[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }*/

    // Creado para buscar pacientes en el listado general de pacientes
    searchPaciente(req?: any): Observable<EntityArrayResponseType> {

        const options = new HttpParams().set('apellido', req.query[0])
        .set('nombre', req.query[1])
        .set('dni', req.query[2]);

        return this.http.get<IPaciente[]>(`${this.resourceUrl}/busqueda-general`, { params: options, observe: 'response' });
    }

    // Creado para buscar pacientes en la ficha
    public buscarPaciente(req?: any): Observable<EntityArrayResponseType> {
        const options = new HttpParams().set('nombreStr', req);
        return this.http.get<IPaciente[]>(`${this.resourceUrl}/busqueda-ficha`, { params: options, observe: 'response' });
    }

    // Creado para buscar pacientes en el turno
    public buscarPacienteXDNI(req?: any): Observable<EntityResponseType> {
        const options = new HttpParams().set('dni', req);
        return this.http.get<IPaciente>(`${this.resourceUrl}/busqueda-turno`, { params: options, observe: 'response' });
    }

}
