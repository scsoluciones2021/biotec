import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    IntoleranciaComponent,
    IntoleranciaDetailComponent,
    IntoleranciaUpdateComponent,
    IntoleranciaDeletePopupComponent,
    IntoleranciaDeleteDialogComponent,
    intoleranciaRoute,
    intoleranciaPopupRoute
} from './';

const ENTITY_STATES = [...intoleranciaRoute, ...intoleranciaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IntoleranciaComponent,
        IntoleranciaDetailComponent,
        IntoleranciaUpdateComponent,
        IntoleranciaDeleteDialogComponent,
        IntoleranciaDeletePopupComponent
    ],
    entryComponents: [
        IntoleranciaComponent,
        IntoleranciaUpdateComponent,
        IntoleranciaDeleteDialogComponent,
        IntoleranciaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebIntoleranciaModule {}
