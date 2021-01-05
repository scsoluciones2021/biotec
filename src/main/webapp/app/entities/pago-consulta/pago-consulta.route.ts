import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PagoConsulta } from 'app/shared/model/pago-consulta.model';
import { PagoConsultaService } from './pago-consulta.service';
import { PagoConsultaComponent } from './pago-consulta.component';
import { PagoConsultaDetailComponent } from './pago-consulta-detail.component';
import { PagoConsultaUpdateComponent } from './pago-consulta-update.component';
import { PagoConsultaDeletePopupComponent } from './pago-consulta-delete-dialog.component';
import { IPagoConsulta } from 'app/shared/model/pago-consulta.model';

@Injectable({ providedIn: 'root' })
export class PagoConsultaResolve implements Resolve<IPagoConsulta> {
    constructor(private service: PagoConsultaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPagoConsulta> {
        const id = route.params['id'];
        if (id) {
            return this.service
                .find(id)
                .pipe(
                    filter((response: HttpResponse<PagoConsulta>) => response.ok),
                    map((pagoConsulta: HttpResponse<PagoConsulta>) => pagoConsulta.body)
                );
        }
        return of(new PagoConsulta());
    }
}

export const pagoConsultaRoute: Routes = [
    {
        path: 'pago-consulta',
        component: PagoConsultaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpsjApp.pagoConsulta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-consulta/:id/view',
        component: PagoConsultaDetailComponent,
        resolve: {
            pagoConsulta: PagoConsultaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpsjApp.pagoConsulta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-consulta/new',
        component: PagoConsultaUpdateComponent,
        resolve: {
            pagoConsulta: PagoConsultaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpsjApp.pagoConsulta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pago-consulta/:id/edit',
        component: PagoConsultaUpdateComponent,
        resolve: {
            pagoConsulta: PagoConsultaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpsjApp.pagoConsulta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pagoConsultaPopupRoute: Routes = [
    {
        path: 'pago-consulta/:id/delete',
        component: PagoConsultaDeletePopupComponent,
        resolve: {
            pagoConsulta: PagoConsultaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpsjApp.pagoConsulta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
