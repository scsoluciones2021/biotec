import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    EspecialidadComponent,
    EspecialidadDetailComponent,
    EspecialidadUpdateComponent,
    EspecialidadDeletePopupComponent,
    EspecialidadDeleteDialogComponent,
    especialidadRoute,
    especialidadPopupRoute
} from './';

const ENTITY_STATES = [...especialidadRoute, ...especialidadPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EspecialidadComponent,
        EspecialidadDetailComponent,
        EspecialidadUpdateComponent,
        EspecialidadDeleteDialogComponent,
        EspecialidadDeletePopupComponent
    ],
    entryComponents: [
        EspecialidadComponent,
        EspecialidadUpdateComponent,
        EspecialidadDeleteDialogComponent,
        EspecialidadDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebEspecialidadModule {}
