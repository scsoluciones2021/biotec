import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ISubgruposcie10, Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';
import { Subgruposcie10Service } from './subgruposcie10.service';
import { Subgruposcie10Component } from './subgruposcie10.component';
import { Subgruposcie10DetailComponent } from './subgruposcie10-detail.component';
import { Subgruposcie10UpdateComponent } from './subgruposcie10-update.component';

@Injectable({ providedIn: 'root' })
export class Subgruposcie10Resolve implements Resolve<ISubgruposcie10> {
    constructor(private service: Subgruposcie10Service) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((subgruposcie10: HttpResponse<Subgruposcie10>) => subgruposcie10.body));
        }
        return of(new Subgruposcie10());
    }
}

export const subgruposcie10Route: Routes = [
    {
        path: 'subgruposcie10',
        component: Subgruposcie10Component,
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'gestWebApp.subgruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subgruposcie10/:id/view',
        component: Subgruposcie10DetailComponent,
        resolve: {
            subgruposcie10: Subgruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.subgruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subgruposcie10/new',
        component: Subgruposcie10UpdateComponent,
        resolve: {
            subgruposcie10: Subgruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.subgruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subgruposcie10/:id/edit',
        component: Subgruposcie10UpdateComponent,
        resolve: {
            subgruposcie10: Subgruposcie10Resolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'gestWebApp.subgruposcie10.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
