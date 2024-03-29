import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    CodigoPostalComponent,
    CodigoPostalDetailComponent,
    CodigoPostalUpdateComponent,
    CodigoPostalDeletePopupComponent,
    CodigoPostalDeleteDialogComponent,
    codigoPostalRoute,
    codigoPostalPopupRoute
} from './';

const ENTITY_STATES = [...codigoPostalRoute, ...codigoPostalPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CodigoPostalComponent,
        CodigoPostalDetailComponent,
        CodigoPostalUpdateComponent,
        CodigoPostalDeleteDialogComponent,
        CodigoPostalDeletePopupComponent
    ],
    entryComponents: [
        CodigoPostalComponent,
        CodigoPostalUpdateComponent,
        CodigoPostalDeleteDialogComponent,
        CodigoPostalDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebCodigoPostalModule {}
