import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';
import { ObsAntecPersonalService } from './obs-antec-personal.service';
import { ObsAntecPersonalComponent } from './obs-antec-personal.component';
import { ObsAntecPersonalDetailComponent } from './obs-antec-personal-detail.component';
import { ObsAntecPersonalUpdateComponent } from './obs-antec-personal-update.component';
import { ObsAntecPersonalDeletePopupComponent } from './obs-antec-personal-delete-dialog.component';
import { IObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';

@Injectable({ providedIn: 'root' })
export class ObsAntecPersonalResolve implements Resolve<IObsAntecPersonal> {
    constructor(private service: ObsAntecPersonalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((obsAntecPersonal: HttpResponse<ObsAntecPersonal>) => obsAntecPersonal.body));
        }
        return of(new ObsAntecPersonal());
    }
}

export const obsAntecPersonalRoute: Routes = [
    {
        path: 'obs-antec-personal',
        component: ObsAntecPersonalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            defaultSort: 'id,asc',
            pageTitle: 'ObsAntecPersonals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-personal/:id/view',
        component: ObsAntecPersonalDetailComponent,
        resolve: {
            obsAntecPersonal: ObsAntecPersonalResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecPersonals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-personal/new',
        component: ObsAntecPersonalUpdateComponent,
        resolve: {
            obsAntecPersonal: ObsAntecPersonalResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecPersonals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-personal/:id/edit',
        component: ObsAntecPersonalUpdateComponent,
        resolve: {
            obsAntecPersonal: ObsAntecPersonalResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecPersonals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obsAntecPersonalPopupRoute: Routes = [
    {
        path: 'obs-antec-personal/:id/delete',
        component: ObsAntecPersonalDeletePopupComponent,
        resolve: {
            obsAntecPersonal: ObsAntecPersonalResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecPersonals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
