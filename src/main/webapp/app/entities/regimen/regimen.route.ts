import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Regimen } from 'app/shared/model/regimen.model';
import { RegimenService } from './regimen.service';
import { RegimenComponent } from './regimen.component';
import { RegimenDetailComponent } from './regimen-detail.component';
import { RegimenUpdateComponent } from './regimen-update.component';
import { RegimenDeletePopupComponent } from './regimen-delete-dialog.component';
import { IRegimen } from 'app/shared/model/regimen.model';

@Injectable({ providedIn: 'root' })
export class RegimenResolve implements Resolve<IRegimen> {
    constructor(private service: RegimenService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((regimen: HttpResponse<Regimen>) => regimen.body));
        }
        return of(new Regimen());
    }
}

export const regimenRoute: Routes = [
    {
        path: 'regimen',
        component: RegimenComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Regimen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regimen/:id/view',
        component: RegimenDetailComponent,
        resolve: {
            regimen: RegimenResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Regimen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regimen/new',
        component: RegimenUpdateComponent,
        resolve: {
            regimen: RegimenResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Regimen'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regimen/:id/edit',
        component: RegimenUpdateComponent,
        resolve: {
            regimen: RegimenResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Regimen'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regimenPopupRoute: Routes = [
    {
        path: 'regimen/:id/delete',
        component: RegimenDeletePopupComponent,
        resolve: {
            regimen: RegimenResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Regimen'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
