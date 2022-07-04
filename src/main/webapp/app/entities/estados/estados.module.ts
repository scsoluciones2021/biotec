import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    EstadosComponent,
    EstadosDetailComponent,
    EstadosUpdateComponent,
    EstadosDeletePopupComponent,
    EstadosDeleteDialogComponent,
    estadosRoute,
    estadosPopupRoute
} from './';

const ENTITY_STATES = [...estadosRoute, ...estadosPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstadosComponent,
        EstadosDetailComponent,
        EstadosUpdateComponent,
        EstadosDeleteDialogComponent,
        EstadosDeletePopupComponent
    ],
    entryComponents: [EstadosComponent, EstadosUpdateComponent, EstadosDeleteDialogComponent, EstadosDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebEstadosModule {}
