import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bloqueos } from 'app/shared/model/bloqueos.model';
import { BloqueosService } from './bloqueos.service';
import { BloqueosComponent } from './bloqueos.component';
import { BloqueosDetailComponent } from './bloqueos-detail.component';
import { BloqueosUpdateComponent } from './bloqueos-update.component';
import { BloqueosDeletePopupComponent } from './bloqueos-delete-dialog.component';
import { IBloqueos } from 'app/shared/model/bloqueos.model';

@Injectable({ providedIn: 'root' })
export class BloqueosResolve implements Resolve<IBloqueos> {
    constructor(private service: BloqueosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bloqueos: HttpResponse<Bloqueos>) => bloqueos.body));
        }
        return of(new Bloqueos());
    }
}

export const bloqueosRoute: Routes = [
    {
        path: 'bloqueos',
        component: BloqueosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'GestWebApp.bloqueos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bloqueos/:id/view',
        component: BloqueosDetailComponent,
        resolve: {
            bloqueos: BloqueosResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'GestWebApp.bloqueos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bloqueos/new',
        component: BloqueosUpdateComponent,
        resolve: {
            bloqueos: BloqueosResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'GestWebApp.bloqueos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bloqueos/:id/edit',
        component: BloqueosUpdateComponent,
        resolve: {
            bloqueos: BloqueosResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'GestWebApp.bloqueos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bloqueosPopupRoute: Routes = [
    {
        path: 'bloqueos/:id/delete',
        component: BloqueosDeletePopupComponent,
        resolve: {
            bloqueos: BloqueosResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'GestWebApp.bloqueos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
