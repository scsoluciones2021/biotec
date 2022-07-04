import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Estados } from 'app/shared/model/estados.model';
import { EstadosService } from './estados.service';
import { EstadosComponent } from './estados.component';
import { EstadosDetailComponent } from './estados-detail.component';
import { EstadosUpdateComponent } from './estados-update.component';
import { EstadosDeletePopupComponent } from './estados-delete-dialog.component';
import { IEstados } from 'app/shared/model/estados.model';

@Injectable({ providedIn: 'root' })
export class EstadosResolve implements Resolve<IEstados> {
    constructor(private service: EstadosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((estados: HttpResponse<Estados>) => estados.body));
        }
        return of(new Estados());
    }
}

export const estadosRoute: Routes = [
    {
        path: 'estados',
        component: EstadosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GestWebApp.estados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estados/:id/view',
        component: EstadosDetailComponent,
        resolve: {
            estados: EstadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.estados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estados/new',
        component: EstadosUpdateComponent,
        resolve: {
            estados: EstadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.estados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estados/:id/edit',
        component: EstadosUpdateComponent,
        resolve: {
            estados: EstadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.estados.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estadosPopupRoute: Routes = [
    {
        path: 'estados/:id/delete',
        component: EstadosDeletePopupComponent,
        resolve: {
            estados: EstadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.estados.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
