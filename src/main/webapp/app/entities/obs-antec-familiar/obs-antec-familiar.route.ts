import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';
import { ObsAntecFamiliarService } from './obs-antec-familiar.service';
import { ObsAntecFamiliarComponent } from './obs-antec-familiar.component';
import { ObsAntecFamiliarDetailComponent } from './obs-antec-familiar-detail.component';
import { ObsAntecFamiliarUpdateComponent } from './obs-antec-familiar-update.component';
import { ObsAntecFamiliarDeletePopupComponent } from './obs-antec-familiar-delete-dialog.component';
import { IObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';

@Injectable({ providedIn: 'root' })
export class ObsAntecFamiliarResolve implements Resolve<IObsAntecFamiliar> {
    constructor(private service: ObsAntecFamiliarService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((obsAntecFamiliar: HttpResponse<ObsAntecFamiliar>) => obsAntecFamiliar.body));
        }
        return of(new ObsAntecFamiliar());
    }
}

export const obsAntecFamiliarRoute: Routes = [
    {
        path: 'obs-antec-familiar',
        component: ObsAntecFamiliarComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            defaultSort: 'id,asc',
            pageTitle: 'ObsAntecFamiliars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-familiar/:id/view',
        component: ObsAntecFamiliarDetailComponent,
        resolve: {
            obsAntecFamiliar: ObsAntecFamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecFamiliars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-familiar/new',
        component: ObsAntecFamiliarUpdateComponent,
        resolve: {
            obsAntecFamiliar: ObsAntecFamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecFamiliars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'obs-antec-familiar/:id/edit',
        component: ObsAntecFamiliarUpdateComponent,
        resolve: {
            obsAntecFamiliar: ObsAntecFamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecFamiliars'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const obsAntecFamiliarPopupRoute: Routes = [
    {
        path: 'obs-antec-familiar/:id/delete',
        component: ObsAntecFamiliarDeletePopupComponent,
        resolve: {
            obsAntecFamiliar: ObsAntecFamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'ObsAntecFamiliars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
