import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

// import { Authority } from 'app/entities/subgruposcie10/node_modules/app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiagnosticoscie10, Diagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';
import { Diagnosticoscie10Service } from './diagnosticoscie10.service';
import { Diagnosticoscie10Component } from './diagnosticoscie10.component';
import { Diagnosticoscie10DetailComponent } from './diagnosticoscie10-detail.component';
import { Diagnosticoscie10UpdateComponent } from './diagnosticoscie10-update.component';

@Injectable({ providedIn: 'root' })
export class Diagnosticoscie10Resolve implements Resolve<IDiagnosticoscie10> {
    constructor(private service: Diagnosticoscie10Service, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot): Observable<IDiagnosticoscie10> | Observable<never> {
        const id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(
                flatMap((diagnosticoscie10: HttpResponse<Diagnosticoscie10>) => {
                    if (diagnosticoscie10.body) {
                        return of(diagnosticoscie10.body);
                    } else {
                        this.router.navigate(['404']);
                        return EMPTY;
                    }
                })
            );
        }
        return of(new Diagnosticoscie10());
    }
}

export const diagnosticoscie10Route: Routes = [
    {
        path: 'diagnosticoscie10',
        component: Diagnosticoscie10Component,
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'gestWebApp.diagnosticoscie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnosticoscie10/:id/view',
        component: Diagnosticoscie10DetailComponent,
        resolve: {
            diagnosticoscie10: Diagnosticoscie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.diagnosticoscie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnosticoscie10/new',
        component: Diagnosticoscie10UpdateComponent,
        resolve: {
            diagnosticoscie10: Diagnosticoscie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.diagnosticoscie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnosticoscie10/:id/edit',
        component: Diagnosticoscie10UpdateComponent,
        resolve: {
            diagnosticoscie10: Diagnosticoscie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.diagnosticoscie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
