import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Profesional } from 'app/shared/model/profesional.model';
import { ProfesionalService } from './profesional.service';
import { ProfesionalComponent } from './profesional.component';
import { ProfesionalDetailComponent } from './profesional-detail.component';
import { ProfesionalUpdateComponent } from './profesional-update.component';
import { ProfesionalDeletePopupComponent } from './profesional-delete-dialog.component';
import { IProfesional } from 'app/shared/model/profesional.model';

@Injectable({ providedIn: 'root' })
export class ProfesionalResolve implements Resolve<IProfesional> {
    constructor(private service: ProfesionalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((profesional: HttpResponse<Profesional>) => profesional.body));
        }
        return of(new Profesional());
    }
}

export const profesionalRoute: Routes = [
    {
        path: 'profesional',
        component: ProfesionalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Profesionales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'profesional/:id/view',
        component: ProfesionalDetailComponent,
        resolve: {
            profesional: ProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profesionales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'profesional/new',
        component: ProfesionalUpdateComponent,
        resolve: {
            profesional: ProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profesionales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'profesional/:id/edit',
        component: ProfesionalUpdateComponent,
        resolve: {
            profesional: ProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profesionales'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profesionalPopupRoute: Routes = [
    {
        path: 'profesional/:id/delete',
        component: ProfesionalDeletePopupComponent,
        resolve: {
            profesional: ProfesionalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Profesionales'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
