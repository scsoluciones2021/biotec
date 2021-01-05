import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';
import { AdjuntosFichaService } from './adjuntos-ficha.service';
import { AdjuntosFichaComponent } from './adjuntos-ficha.component';
import { AdjuntosFichaDetailComponent } from './adjuntos-ficha-detail.component';
import { AdjuntosFichaUpdateComponent } from './adjuntos-ficha-update.component';
import { AdjuntosFichaDeletePopupComponent } from './adjuntos-ficha-delete-dialog.component';
import { IAdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';

@Injectable({ providedIn: 'root' })
export class AdjuntosFichaResolve implements Resolve<IAdjuntosFicha> {
    constructor(private service: AdjuntosFichaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((adjuntos_ficha: HttpResponse<AdjuntosFicha>) => adjuntos_ficha.body));
        }
        return of(new AdjuntosFicha());
    }
}

export const adjuntosFichaRoute: Routes = [
    {
        path: 'adjuntos-ficha',
        component: AdjuntosFichaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            defaultSort: 'id,asc',
            pageTitle: 'Adjuntos_fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjuntos-ficha/:id/view',
        component: AdjuntosFichaDetailComponent,
        resolve: {
            adjuntos_ficha: AdjuntosFichaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Adjuntos_fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjuntos-ficha/new',
        component: AdjuntosFichaUpdateComponent,
        resolve: {
            adjuntos_ficha: AdjuntosFichaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Adjuntos_fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adjuntos-ficha/:id/edit',
        component: AdjuntosFichaUpdateComponent,
        resolve: {
            adjuntos_ficha: AdjuntosFichaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Adjuntos_fichas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adjuntosFichaPopupRoute: Routes = [
    {
        path: 'adjuntos-ficha/:id/delete',
        component: AdjuntosFichaDeletePopupComponent,
        resolve: {
            adjuntos_ficha: AdjuntosFichaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Adjuntos_fichas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
