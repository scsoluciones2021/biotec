import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';
import { ObraSocialComponent } from './obra-social.component';
import { ObraSocialDetailComponent } from './obra-social-detail.component';
import { ObraSocialUpdateComponent } from './obra-social-update.component';
import { ObraSocialDeletePopupComponent } from './obra-social-delete-dialog.component';
import { IObraSocial } from 'app/shared/model/obra-social.model';

@Injectable({ providedIn: 'root' })
export class ObraSocialResolve implements Resolve<IObraSocial> {
    constructor(private service: ObraSocialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((obraSocial: HttpResponse<ObraSocial>) => obraSocial.body));
        }
        return of(new ObraSocial());
    }
}

export const obraSocialRoute: Routes = [
    {
        path: 'obra-social',
        component: ObraSocialComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Obra Social'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obra-social/:id/view',
        component: ObraSocialDetailComponent,
        resolve: {
            obraSocial: ObraSocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obra Social'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obra-social/new',
        component: ObraSocialUpdateComponent,
        resolve: {
            obraSocial: ObraSocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Obra Social'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obra-social/:id/edit',
        component: ObraSocialUpdateComponent,
        resolve: {
            obraSocial: ObraSocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ObraSocials'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obraSocialPopupRoute: Routes = [
    {
        path: 'obra-social/:id/delete',
        component: ObraSocialDeletePopupComponent,
        resolve: {
            obraSocial: ObraSocialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ObraSocials'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
