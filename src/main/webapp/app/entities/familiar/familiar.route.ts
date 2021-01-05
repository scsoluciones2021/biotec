import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Familiar } from 'app/shared/model/familiar.model';
import { FamiliarService } from './familiar.service';
import { FamiliarComponent } from './familiar.component';
import { FamiliarDetailComponent } from './familiar-detail.component';
import { FamiliarUpdateComponent } from './familiar-update.component';
import { FamiliarDeletePopupComponent } from './familiar-delete-dialog.component';
import { IFamiliar } from 'app/shared/model/familiar.model';

@Injectable({ providedIn: 'root' })
export class FamiliarResolve implements Resolve<IFamiliar> {
    constructor(private service: FamiliarService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((familiar: HttpResponse<Familiar>) => familiar.body));
        }
        return of(new Familiar());
    }
}

export const familiarRoute: Routes = [
    {
        path: 'familiar',
        component: FamiliarComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Familiars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'familiar/:id/view',
        component: FamiliarDetailComponent,
        resolve: {
            familiar: FamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Familiars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'familiar/new',
        component: FamiliarUpdateComponent,
        resolve: {
            familiar: FamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Familiars'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'familiar/:id/edit',
        component: FamiliarUpdateComponent,
        resolve: {
            familiar: FamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Familiars'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const familiarPopupRoute: Routes = [
    {
        path: 'familiar/:id/delete',
        component: FamiliarDeletePopupComponent,
        resolve: {
            familiar: FamiliarResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Familiars'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
