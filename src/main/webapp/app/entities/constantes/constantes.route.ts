import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Constantes } from 'app/shared/model/constantes.model';
import { ConstantesService } from './constantes.service';
import { ConstantesComponent } from './constantes.component';
import { ConstantesDetailComponent } from './constantes-detail.component';
import { ConstantesUpdateComponent } from './constantes-update.component';
import { ConstantesDeletePopupComponent } from './constantes-delete-dialog.component';
import { IConstantes } from 'app/shared/model/constantes.model';

@Injectable({ providedIn: 'root' })
export class ConstantesResolve implements Resolve<IConstantes> {
    constructor(private service: ConstantesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((constantes: HttpResponse<Constantes>) => constantes.body));
        }
        return of(new Constantes());
    }
}

export const constantesRoute: Routes = [
    {
        path: 'constantes',
        component: ConstantesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Constantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'constantes/:id/view',
        component: ConstantesDetailComponent,
        resolve: {
            constantes: ConstantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Constantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'constantes/new',
        component: ConstantesUpdateComponent,
        resolve: {
            constantes: ConstantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Constantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'constantes/:id/edit',
        component: ConstantesUpdateComponent,
        resolve: {
            constantes: ConstantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Constantes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const constantesPopupRoute: Routes = [
    {
        path: 'constantes/:id/delete',
        component: ConstantesDeletePopupComponent,
        resolve: {
            constantes: ConstantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Constantes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
