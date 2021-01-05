import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from './codigo-postal.service';
import { CodigoPostalComponent } from './codigo-postal.component';
import { CodigoPostalDetailComponent } from './codigo-postal-detail.component';
import { CodigoPostalUpdateComponent } from './codigo-postal-update.component';
import { CodigoPostalDeletePopupComponent } from './codigo-postal-delete-dialog.component';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';

@Injectable({ providedIn: 'root' })
export class CodigoPostalResolve implements Resolve<ICodigoPostal> {
    constructor(private service: CodigoPostalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((codigoPostal: HttpResponse<CodigoPostal>) => codigoPostal.body));
        }
        return of(new CodigoPostal());
    }
}

export const codigoPostalRoute: Routes = [
    {
        path: 'codigo-postal',
        component: CodigoPostalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CodigoPostals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'codigo-postal/:id/view',
        component: CodigoPostalDetailComponent,
        resolve: {
            codigoPostal: CodigoPostalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CodigoPostals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'codigo-postal/new',
        component: CodigoPostalUpdateComponent,
        resolve: {
            codigoPostal: CodigoPostalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CodigoPostals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'codigo-postal/:id/edit',
        component: CodigoPostalUpdateComponent,
        resolve: {
            codigoPostal: CodigoPostalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CodigoPostals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const codigoPostalPopupRoute: Routes = [
    {
        path: 'codigo-postal/:id/delete',
        component: CodigoPostalDeletePopupComponent,
        resolve: {
            codigoPostal: CodigoPostalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CodigoPostals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
