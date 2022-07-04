import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

// import { Authority } from 'app/entities/subgruposcie10/node_modules/app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGruposcie10, Gruposcie10 } from 'app/shared/model/gruposcie10.model';
import { Gruposcie10Service } from './gruposcie10.service';
import { Gruposcie10Component } from './gruposcie10.component';
import { Gruposcie10DetailComponent } from './gruposcie10-detail.component';
import { Gruposcie10UpdateComponent } from './gruposcie10-update.component';

@Injectable({ providedIn: 'root' })
export class Gruposcie10Resolve implements Resolve<IGruposcie10> {
    constructor(private service: Gruposcie10Service, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot): Observable<IGruposcie10> | Observable<never> {
        const id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(
                flatMap((gruposcie10: HttpResponse<Gruposcie10>) => {
                    if (gruposcie10.body) {
                        return of(gruposcie10.body);
                    } else {
                        this.router.navigate(['404']);
                        return EMPTY;
                    }
                })
            );
        }
        return of(new Gruposcie10());
    }
}

export const gruposcie10Route: Routes = [
    {
        path: 'gruposcie10',
        component: Gruposcie10Component,
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'gestWebApp.gruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruposcie10/:id/view',
        component: Gruposcie10DetailComponent,
        resolve: {
            gruposcie10: Gruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.gruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruposcie10/new',
        component: Gruposcie10UpdateComponent,
        resolve: {
            gruposcie10: Gruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.gruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gruposcie10/:id/edit',
        component: Gruposcie10UpdateComponent,
        resolve: {
            gruposcie10: Gruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.gruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
