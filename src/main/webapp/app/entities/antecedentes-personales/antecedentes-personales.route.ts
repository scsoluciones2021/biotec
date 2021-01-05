import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';
import { AntecedentesPersonalesService } from './antecedentes-personales.service';
import { AntecedentesPersonalesComponent } from './antecedentes-personales.component';
import { AntecedentesPersonalesDetailComponent } from './antecedentes-personales-detail.component';
import { AntecedentesPersonalesUpdateComponent } from './antecedentes-personales-update.component';
import { AntecedentesPersonalesDeletePopupComponent } from './antecedentes-personales-delete-dialog.component';
import { IAntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

@Injectable({ providedIn: 'root' })
export class AntecedentesPersonalesResolve implements Resolve<IAntecedentesPersonales> {
    constructor(private service: AntecedentesPersonalesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((antecedentesPersonales: HttpResponse<AntecedentesPersonales>) => antecedentesPersonales.body));
        }
        return of(new AntecedentesPersonales());
    }
}

export const antecedentesPersonalesRoute: Routes = [
    {
        path: 'antecedentes-personales',
        component: AntecedentesPersonalesComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesPersonales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-personales/:id/view',
        component: AntecedentesPersonalesDetailComponent,
        resolve: {
            antecedentesPersonales: AntecedentesPersonalesResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesPersonales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-personales/new',
        component: AntecedentesPersonalesUpdateComponent,
        resolve: {
            antecedentesPersonales: AntecedentesPersonalesResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesPersonales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'antecedentes-personales/:id/edit',
        component: AntecedentesPersonalesUpdateComponent,
        resolve: {
            antecedentesPersonales: AntecedentesPersonalesResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesPersonales'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const antecedentesPersonalesPopupRoute: Routes = [
    {
        path: 'antecedentes-personales/:id/delete',
        component: AntecedentesPersonalesDeletePopupComponent,
        resolve: {
            antecedentesPersonales: AntecedentesPersonalesResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'AntecedentesPersonales'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
