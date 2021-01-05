import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Personal } from 'app/shared/model/personal.model';
import { PersonalService } from './personal.service';
import { PersonalComponent } from './personal.component';
import { PersonalDetailComponent } from './personal-detail.component';
import { PersonalUpdateComponent } from './personal-update.component';
import { PersonalDeletePopupComponent } from './personal-delete-dialog.component';
import { IPersonal } from 'app/shared/model/personal.model';

@Injectable({ providedIn: 'root' })
export class PersonalResolve implements Resolve<IPersonal> {
    constructor(private service: PersonalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((personal: HttpResponse<Personal>) => personal.body));
        }
        return of(new Personal());
    }
}

export const personalRoute: Routes = [
    {
        path: 'personal',
        component: PersonalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal/:id/view',
        component: PersonalDetailComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal/new',
        component: PersonalUpdateComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personal/:id/edit',
        component: PersonalUpdateComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personalPopupRoute: Routes = [
    {
        path: 'personal/:id/delete',
        component: PersonalDeletePopupComponent,
        resolve: {
            personal: PersonalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
