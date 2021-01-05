import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MainService {
    private resourceUrl = SERVER_API_URL + 'carrusel';

    constructor(private http: HttpClient) {}

    getFiles(): any {
      return this.http.get<String[]>(`${this.resourceUrl}`);
    }
}
