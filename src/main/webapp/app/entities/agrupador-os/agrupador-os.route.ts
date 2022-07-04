import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AgrupadorOS } from 'app/shared/model/agrupador-os.model';
import { AgrupadorOSService } from './agrupador-os.service';
import { AgrupadorOSComponent } from './agrupador-os.component';
import { AgrupadorOSDetailComponent } from './agrupador-os-detail.component';
import { AgrupadorOSUpdateComponent } from './agrupador-os-update.component';
import { AgrupadorOSDeletePopupComponent } from './agrupador-os-delete-dialog.component';
import { IAgrupadorOS } from 'app/shared/model/agrupador-os.model';

@Injectable({ providedIn: 'root' })
export class AgrupadorOSResolve implements Resolve<IAgrupadorOS> {
    constructor(private service: AgrupadorOSService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((agrupadorOS: HttpResponse<AgrupadorOS>) => agrupadorOS.body));
        }
        return of(new AgrupadorOS());
    }
}

export const agrupadorOSRoute: Routes = [
    {
        path: 'agrupador-os',
        component: AgrupadorOSComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GestWebApp.agrupadorOS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'agrupador-os/:id/view',
        component: AgrupadorOSDetailComponent,
        resolve: {
            agrupadorOS: AgrupadorOSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.agrupadorOS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'agrupador-os/new',
        component: AgrupadorOSUpdateComponent,
        resolve: {
            agrupadorOS: AgrupadorOSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.agrupadorOS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'agrupador-os/:id/edit',
        component: AgrupadorOSUpdateComponent,
        resolve: {
            agrupadorOS: AgrupadorOSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.agrupadorOS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agrupadorOSPopupRoute: Routes = [
    {
        path: 'agrupador-os/:id/delete',
        component: AgrupadorOSDeletePopupComponent,
        resolve: {
            agrupadorOS: AgrupadorOSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.agrupadorOS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
