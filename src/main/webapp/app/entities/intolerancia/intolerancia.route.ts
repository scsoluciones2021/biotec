import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Intolerancia } from 'app/shared/model/intolerancia.model';
import { IntoleranciaService } from './intolerancia.service';
import { IntoleranciaComponent } from './intolerancia.component';
import { IntoleranciaDetailComponent } from './intolerancia-detail.component';
import { IntoleranciaUpdateComponent } from './intolerancia-update.component';
import { IntoleranciaDeletePopupComponent } from './intolerancia-delete-dialog.component';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

@Injectable({ providedIn: 'root' })
export class IntoleranciaResolve implements Resolve<IIntolerancia> {
    constructor(private service: IntoleranciaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((intolerancia: HttpResponse<Intolerancia>) => intolerancia.body));
        }
        return of(new Intolerancia());
    }
}

export const intoleranciaRoute: Routes = [
    {
        path: 'intolerancia',
        component: IntoleranciaComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Intolerancias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intolerancia/:id/view',
        component: IntoleranciaDetailComponent,
        resolve: {
            intolerancia: IntoleranciaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Intolerancias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intolerancia/new',
        component: IntoleranciaUpdateComponent,
        resolve: {
            intolerancia: IntoleranciaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Intolerancias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'intolerancia/:id/edit',
        component: IntoleranciaUpdateComponent,
        resolve: {
            intolerancia: IntoleranciaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Intolerancias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const intoleranciaPopupRoute: Routes = [
    {
        path: 'intolerancia/:id/delete',
        component: IntoleranciaDeletePopupComponent,
        resolve: {
            intolerancia: IntoleranciaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Intolerancias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
