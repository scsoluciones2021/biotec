import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    ConstantesComponent,
    ConstantesDetailComponent,
    ConstantesUpdateComponent,
    ConstantesDeletePopupComponent,
    ConstantesDeleteDialogComponent,
    constantesRoute,
    constantesPopupRoute
} from './';

const ENTITY_STATES = [...constantesRoute, ...constantesPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConstantesComponent,
        ConstantesDetailComponent,
        ConstantesUpdateComponent,
        ConstantesDeleteDialogComponent,
        ConstantesDeletePopupComponent
    ],
    entryComponents: [ConstantesComponent, ConstantesUpdateComponent, ConstantesDeleteDialogComponent, ConstantesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebConstantesModule {}
