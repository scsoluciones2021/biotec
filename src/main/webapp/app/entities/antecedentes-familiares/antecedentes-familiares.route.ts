import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';
import { AntecedentesFamiliaresComponent } from './antecedentes-familiares.component';
import { AntecedentesFamiliaresDetailComponent } from './antecedentes-familiares-detail.component';
import { AntecedentesFamiliaresUpdateComponent } from './antecedentes-familiares-update.component';
import { AntecedentesFamiliaresDeletePopupComponent } from './antecedentes-familiares-delete-dialog.component';
import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

@Injectable({ providedIn: 'root' })
export class AntecedentesFamiliaresResolve implements Resolve<IAntecedentesFamiliares> {
    constructor(private service: AntecedentesFamiliaresService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((antecedentesFamiliares: HttpResponse<AntecedentesFamiliares>) => antecedentesFamiliares.body));
        }
        return of(new AntecedentesFamiliares());
    }
}

export const antecedentesFamiliaresRoute: Routes = [
    {
        path: 'antecedentes-familiares',
        component: AntecedentesFamiliaresComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesFamiliares'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-familiares/:id/view',
        component: AntecedentesFamiliaresDetailComponent,
        resolve: {
            antecedentesFamiliares: AntecedentesFamiliaresResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesFamiliares'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-familiares/new',
        component: AntecedentesFamiliaresUpdateComponent,
        resolve: {
            antecedentesFamiliares: AntecedentesFamiliaresResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesFamiliares'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-familiares/:id/edit',
        component: AntecedentesFamiliaresUpdateComponent,
        resolve: {
            antecedentesFamiliares: AntecedentesFamiliaresResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesFamiliares'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const antecedentesFamiliaresPopupRoute: Routes = [
    {
        path: 'antecedentes-familiares/:id/delete',
        component: AntecedentesFamiliaresDeletePopupComponent,
        resolve: {
            antecedentesFamiliares: AntecedentesFamiliaresResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesFamiliares'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
