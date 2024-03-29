import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    AdjuntosFichaComponent,
    AdjuntosFichaDetailComponent,
    AdjuntosFichaUpdateComponent,
    AdjuntosFichaDeletePopupComponent,
    AdjuntosFichaDeleteDialogComponent,
    adjuntosFichaRoute,
    adjuntosFichaPopupRoute
} from './';

const ENTITY_STATES = [...adjuntosFichaRoute, ...adjuntosFichaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdjuntosFichaComponent,
        AdjuntosFichaDetailComponent,
        AdjuntosFichaUpdateComponent,
        AdjuntosFichaDeleteDialogComponent,
        AdjuntosFichaDeletePopupComponent
    ],
    entryComponents: [
        AdjuntosFichaComponent,
        AdjuntosFichaUpdateComponent,
        AdjuntosFichaDeleteDialogComponent,
        AdjuntosFichaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebAdjuntosFichaModule {}
