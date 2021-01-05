import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Enfermedad } from 'app/shared/model/enfermedad.model';
import { EnfermedadService } from './enfermedad.service';
import { EnfermedadComponent } from './enfermedad.component';
import { EnfermedadDetailComponent } from './enfermedad-detail.component';
import { EnfermedadUpdateComponent } from './enfermedad-update.component';
import { EnfermedadDeletePopupComponent } from './enfermedad-delete-dialog.component';
import { IEnfermedad } from 'app/shared/model/enfermedad.model';

@Injectable({ providedIn: 'root' })
export class EnfermedadResolve implements Resolve<IEnfermedad> {
    constructor(private service: EnfermedadService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((enfermedad: HttpResponse<Enfermedad>) => enfermedad.body));
        }
        return of(new Enfermedad());
    }
}

export const enfermedadRoute: Routes = [
    {
        path: 'enfermedad',
        component: EnfermedadComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Enfermedads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enfermedad/:id/view',
        component: EnfermedadDetailComponent,
        resolve: {
            enfermedad: EnfermedadResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Enfermedads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enfermedad/new',
        component: EnfermedadUpdateComponent,
        resolve: {
            enfermedad: EnfermedadResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Enfermedads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'enfermedad/:id/edit',
        component: EnfermedadUpdateComponent,
        resolve: {
            enfermedad: EnfermedadResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Enfermedads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enfermedadPopupRoute: Routes = [
    {
        path: 'enfermedad/:id/delete',
        component: EnfermedadDeletePopupComponent,
        resolve: {
            enfermedad: EnfermedadResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Enfermedads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
