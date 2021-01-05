import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Turno } from 'app/shared/model/turno.model';
import { TurnoService } from './turno.service';
import { TurnoComponent } from './turno.component';
import { TurnoDetailComponent } from './turno-detail.component';
import { TurnoUpdateComponent } from './turno-update.component';
import { TurnoOnlineComponent } from './turno-online.component';
import { TurnoOnlineListadoComponent } from './turno-online-listado.component';
import { TurnoProfesionalComponent } from './turno-profesional.component';
import { TurnoDeletePopupComponent } from './turno-delete-dialog.component';
import { ITurno } from 'app/shared/model/turno.model';

@Injectable({ providedIn: 'root' })
export class TurnoResolve implements Resolve<ITurno> {
    constructor(private service: TurnoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((turno: HttpResponse<Turno>) => turno.body));
        }
        return of(new Turno());
    }
}

export const turnoRoute: Routes = [
    {
        path: 'turno',
        component: TurnoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno/:id/view',
        component: TurnoDetailComponent,
        resolve: {
            turno: TurnoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno/new',
        component: TurnoUpdateComponent,
        resolve: {
            turno: TurnoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno/:id/edit',
        component: TurnoUpdateComponent,
        resolve: {
            turno: TurnoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno-online/new',
        component: TurnoOnlineComponent,
        resolve: {
            turno: TurnoResolve
        },
        data: {
            authorities: ['ROLE_PACIENTE'],
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno-online-listado',
        component: TurnoOnlineListadoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_PACIENTE'],
            defaultSort: 'id,asc',
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'turno-profesional',
        component: TurnoProfesionalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_MEDICO'],
            defaultSort: 'id,asc',
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const turnoPopupRoute: Routes = [
    {
        path: 'turno/:id/delete',
        component: TurnoDeletePopupComponent,
        resolve: {
            turno: TurnoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Turnos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
