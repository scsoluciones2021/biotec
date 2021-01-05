import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bebida } from 'app/shared/model/bebida.model';
import { BebidaService } from './bebida.service';
import { BebidaComponent } from './bebida.component';
import { BebidaDetailComponent } from './bebida-detail.component';
import { BebidaUpdateComponent } from './bebida-update.component';
import { BebidaDeletePopupComponent } from './bebida-delete-dialog.component';
import { IBebida } from 'app/shared/model/bebida.model';

@Injectable({ providedIn: 'root' })
export class BebidaResolve implements Resolve<IBebida> {
    constructor(private service: BebidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bebida: HttpResponse<Bebida>) => bebida.body));
        }
        return of(new Bebida());
    }
}

export const bebidaRoute: Routes = [
    {
        path: 'bebida',
        component: BebidaComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Bebidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bebida/:id/view',
        component: BebidaDetailComponent,
        resolve: {
            bebida: BebidaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Bebidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bebida/new',
        component: BebidaUpdateComponent,
        resolve: {
            bebida: BebidaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Bebidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bebida/:id/edit',
        component: BebidaUpdateComponent,
        resolve: {
            bebida: BebidaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Bebidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bebidaPopupRoute: Routes = [
    {
        path: 'bebida/:id/delete',
        component: BebidaDeletePopupComponent,
        resolve: {
            bebida: BebidaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Bebidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
