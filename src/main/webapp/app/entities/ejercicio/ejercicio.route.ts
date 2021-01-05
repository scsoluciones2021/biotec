import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ejercicio } from 'app/shared/model/ejercicio.model';
import { EjercicioService } from './ejercicio.service';
import { EjercicioComponent } from './ejercicio.component';
import { EjercicioDetailComponent } from './ejercicio-detail.component';
import { EjercicioUpdateComponent } from './ejercicio-update.component';
import { EjercicioDeletePopupComponent } from './ejercicio-delete-dialog.component';
import { IEjercicio } from 'app/shared/model/ejercicio.model';

@Injectable({ providedIn: 'root' })
export class EjercicioResolve implements Resolve<IEjercicio> {
    constructor(private service: EjercicioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ejercicio: HttpResponse<Ejercicio>) => ejercicio.body));
        }
        return of(new Ejercicio());
    }
}

export const ejercicioRoute: Routes = [
    {
        path: 'ejercicio',
        component: EjercicioComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Ejercicios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ejercicio/:id/view',
        component: EjercicioDetailComponent,
        resolve: {
            ejercicio: EjercicioResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Ejercicios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ejercicio/new',
        component: EjercicioUpdateComponent,
        resolve: {
            ejercicio: EjercicioResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Ejercicios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ejercicio/:id/edit',
        component: EjercicioUpdateComponent,
        resolve: {
            ejercicio: EjercicioResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Ejercicios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ejercicioPopupRoute: Routes = [
    {
        path: 'ejercicio/:id/delete',
        component: EjercicioDeletePopupComponent,
        resolve: {
            ejercicio: EjercicioResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Ejercicios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
