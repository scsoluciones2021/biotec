import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

// import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriascie10, Categoriascie10 } from 'app/shared/model/categoriascie10.model';
import { Categoriascie10Service } from './categoriascie10.service';
import { Categoriascie10Component } from './categoriascie10.component';
import { Categoriascie10DetailComponent } from './categoriascie10-detail.component';
import { Categoriascie10UpdateComponent } from './categoriascie10-update.component';

@Injectable({ providedIn: 'root' })
export class Categoriascie10Resolve implements Resolve<ICategoriascie10> {
    constructor(private service: Categoriascie10Service, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot): Observable<ICategoriascie10> | Observable<never> {
        const id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(
                flatMap((categoriascie10: HttpResponse<Categoriascie10>) => {
                    if (categoriascie10.body) {
                        return of(categoriascie10.body);
                    } else {
                        this.router.navigate(['404']);
                        return EMPTY;
                    }
                })
            );
        }
        return of(new Categoriascie10());
    }
}

export const categoriascie10Route: Routes = [
    {
        path: 'categoriascie10',
        component: Categoriascie10Component,
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'gestWebApp.categoriascie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoriascie10/:id/view',
        component: Categoriascie10DetailComponent,
        resolve: {
            categoriascie10: Categoriascie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.categoriascie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoriascie10/new',
        component: Categoriascie10UpdateComponent,
        resolve: {
            categoriascie10: Categoriascie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.categoriascie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'categoriascie10/:id/edit',
        component: Categoriascie10UpdateComponent,
        resolve: {
            categoriascie10: Categoriascie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.categoriascie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
