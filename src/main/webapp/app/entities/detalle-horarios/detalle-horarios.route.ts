import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosService } from './detalle-horarios.service';
import { DetalleHorariosComponent } from './detalle-horarios.component';
import { DetalleHorariosDetailComponent } from './detalle-horarios-detail.component';
import { DetalleHorariosUpdateComponent } from './detalle-horarios-update.component';
import { DetalleHorariosDeletePopupComponent } from './detalle-horarios-delete-dialog.component';
import { IDetalleHorarios } from 'app/shared/model/detalle-horarios.model';
import { DetalleHorariosModalComponent } from './detalle-horarios-modal.component';

@Injectable({ providedIn: 'root' })
export class DetalleHorariosResolve implements Resolve<IDetalleHorarios> {
    constructor(private service: DetalleHorariosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((detalleHorarios: HttpResponse<DetalleHorarios>) => detalleHorarios.body));
        }
        return of(new DetalleHorarios());
    }
}

export const detalleHorariosRoute: Routes = [
    {
        path: 'detalle-horarios',
        component: DetalleHorariosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GestWebApp.detalleHorarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-horarios/:id/view',
        component: DetalleHorariosDetailComponent,
        resolve: {
            detalleHorarios: DetalleHorariosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.detalleHorarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-horarios/new',
        component: DetalleHorariosUpdateComponent,
        resolve: {
            detalleHorarios: DetalleHorariosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.detalleHorarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'detalle-horarios/:id/edit',
        component: DetalleHorariosUpdateComponent,
        resolve: {
            detalleHorarios: DetalleHorariosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.detalleHorarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detalleHorariosPopupRoute: Routes = [
    {
        path: 'detalle-horarios/:id/delete',
        component: DetalleHorariosDeletePopupComponent,
        resolve: {
            detalleHorarios: DetalleHorariosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GestWebApp.detalleHorarios.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-horarios/newModal',
        component: DetalleHorariosModalComponent,
        resolve: {
            paciente: DetalleHorariosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Detalles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
