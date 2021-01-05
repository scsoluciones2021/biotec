import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TituloShort } from 'app/shared/model/titulo-short.model';
import { TituloShortService } from './titulo-short.service';
import { TituloShortComponent } from './titulo-short.component';
import { TituloShortDetailComponent } from './titulo-short-detail.component';
import { TituloShortUpdateComponent } from './titulo-short-update.component';
import { TituloShortDeletePopupComponent } from './titulo-short-delete-dialog.component';
import { ITituloShort } from 'app/shared/model/titulo-short.model';

@Injectable({ providedIn: 'root' })
export class TituloShortResolve implements Resolve<ITituloShort> {
    constructor(private service: TituloShortService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tituloShort: HttpResponse<TituloShort>) => tituloShort.body));
        }
        return of(new TituloShort());
    }
}

export const tituloShortRoute: Routes = [
    {
        path: 'titulo-short',
        component: TituloShortComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TituloShorts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titulo-short/:id/view',
        component: TituloShortDetailComponent,
        resolve: {
            tituloShort: TituloShortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TituloShorts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titulo-short/new',
        component: TituloShortUpdateComponent,
        resolve: {
            tituloShort: TituloShortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TituloShorts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titulo-short/:id/edit',
        component: TituloShortUpdateComponent,
        resolve: {
            tituloShort: TituloShortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TituloShorts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tituloShortPopupRoute: Routes = [
    {
        path: 'titulo-short/:id/delete',
        component: TituloShortDeletePopupComponent,
        resolve: {
            tituloShort: TituloShortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TituloShorts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
