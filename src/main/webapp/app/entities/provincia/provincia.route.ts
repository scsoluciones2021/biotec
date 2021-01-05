import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Provincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from './provincia.service';
import { ProvinciaComponent } from './provincia.component';
import { ProvinciaDetailComponent } from './provincia-detail.component';
import { ProvinciaUpdateComponent } from './provincia-update.component';
import { ProvinciaDeletePopupComponent } from './provincia-delete-dialog.component';
import { IProvincia } from 'app/shared/model/provincia.model';

@Injectable({ providedIn: 'root' })
export class ProvinciaResolve implements Resolve<IProvincia> {
    constructor(private service: ProvinciaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((provincia: HttpResponse<Provincia>) => provincia.body));
        }
        return of(new Provincia());
    }
}

export const provinciaRoute: Routes = [
    {
        path: 'provincia',
        component: ProvinciaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Provincias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provincia/:id/view',
        component: ProvinciaDetailComponent,
        resolve: {
            provincia: ProvinciaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Provincias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provincia/new',
        component: ProvinciaUpdateComponent,
        resolve: {
            provincia: ProvinciaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Provincias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'provincia/:id/edit',
        component: ProvinciaUpdateComponent,
        resolve: {
            provincia: ProvinciaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Provincias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const provinciaPopupRoute: Routes = [
    {
        path: 'provincia/:id/delete',
        component: ProvinciaDeletePopupComponent,
        resolve: {
            provincia: ProvinciaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Provincias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
