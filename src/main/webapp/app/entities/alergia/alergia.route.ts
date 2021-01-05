import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Alergia } from 'app/shared/model/alergia.model';
import { AlergiaService } from './alergia.service';
import { AlergiaComponent } from './alergia.component';
import { AlergiaDetailComponent } from './alergia-detail.component';
import { AlergiaUpdateComponent } from './alergia-update.component';
import { AlergiaDeletePopupComponent } from './alergia-delete-dialog.component';
import { IAlergia } from 'app/shared/model/alergia.model';

@Injectable({ providedIn: 'root' })
export class AlergiaResolve implements Resolve<IAlergia> {
    constructor(private service: AlergiaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((alergia: HttpResponse<Alergia>) => alergia.body));
        }
        return of(new Alergia());
    }
}

export const alergiaRoute: Routes = [
    {
        path: 'alergia',
        component: AlergiaComponent,
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Alergias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'alergia/:id/view',
        component: AlergiaDetailComponent,
        resolve: {
            alergia: AlergiaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Alergias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'alergia/new',
        component: AlergiaUpdateComponent,
        resolve: {
            alergia: AlergiaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Alergias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'alergia/:id/edit',
        component: AlergiaUpdateComponent,
        resolve: {
            alergia: AlergiaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Alergias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const alergiaPopupRoute: Routes = [
    {
        path: 'alergia/:id/delete',
        component: AlergiaDeletePopupComponent,
        resolve: {
            alergia: AlergiaResolve
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            pageTitle: 'Alergias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
