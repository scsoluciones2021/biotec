import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HorariosProfesional } from 'app/shared/model/horarios-profesional.model';
import { HorariosProfesionalService } from './horarios-profesional.service';
import { HorariosProfesionalComponent } from './horarios-profesional.component';
import { HorariosProfesionalDetailComponent } from './horarios-profesional-detail.component';
import { HorariosProfesionalUpdateComponent } from './horarios-profesional-update.component';
import { HorariosProfesionalDeletePopupComponent } from './horarios-profesional-delete-dialog.component';
import { IHorariosProfesional } from 'app/shared/model/horarios-profesional.model';

@Injectable({ providedIn: 'root' })
export class HorariosProfesionalResolve implements Resolve<IHorariosProfesional> {
    constructor(private service: HorariosProfesionalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((horariosProfesional: HttpResponse<HorariosProfesional>) => horariosProfesional.body));
        }
        return of(new HorariosProfesional());
    }
}

export const horariosProfesionalRoute: Routes = [
    {
        path: 'horarios-profesional',
        component: HorariosProfesionalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Horarios Profesional'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horarios-profesional/:id/view',
        component: HorariosProfesionalDetailComponent,
        resolve: {
            horariosProfesional: HorariosProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Horarios Profesional'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horarios-profesional/new',
        component: HorariosProfesionalUpdateComponent,
        resolve: {
            horariosProfesional: HorariosProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Horarios Profesional'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'horarios-profesional/:id/edit',
        component: HorariosProfesionalUpdateComponent,
        resolve: {
            horariosProfesional: HorariosProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Horarios Profesional'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const horariosProfesionalPopupRoute: Routes = [
    {
        path: 'horarios-profesional/:id/delete',
        component: HorariosProfesionalDeletePopupComponent,
        resolve: {
            horariosProfesional: HorariosProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Horarios Profesional'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
