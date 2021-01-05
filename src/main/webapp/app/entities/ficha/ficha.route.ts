import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ficha } from 'app/shared/model/ficha.model';
import { FichaService } from './ficha.service';
import { FichaComponent } from './ficha.component';
import { FichaDetailComponent } from './ficha-detail.component';
import { FichaUpdateComponent } from './ficha-update.component';
import { FichaDeletePopupComponent } from './ficha-delete-dialog.component';
import { IFicha } from 'app/shared/model/ficha.model';

@Injectable({ providedIn: 'root' })
export class FichaResolve implements Resolve<IFicha> {
    constructor(private service: FichaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ficha: HttpResponse<Ficha>) => ficha.body));
        }
        return of(new Ficha());
    }
}

export const fichaRoute: Routes = [
    {
        path: 'ficha',
        component: FichaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MEDICO'],
            defaultSort: 'id,asc',
            pageTitle: 'Fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ficha/:id/view',
        component: FichaDetailComponent,
        resolve: {
            ficha: FichaResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MEDICO'],
            pageTitle: 'Fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ficha/new',
        component: FichaUpdateComponent,
        resolve: {
            ficha: FichaResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MEDICO'],
            pageTitle: 'Fichas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ficha/:id/edit',
        component: FichaUpdateComponent,
        resolve: {
            ficha: FichaResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MEDICO'],
            pageTitle: 'Fichas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fichaPopupRoute: Routes = [
    {
        path: 'ficha/:id/delete',
        component: FichaDeletePopupComponent,
        resolve: {
            ficha: FichaResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_MEDICO'],
            pageTitle: 'Fichas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
