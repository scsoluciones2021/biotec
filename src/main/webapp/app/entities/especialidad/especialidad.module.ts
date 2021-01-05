import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
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
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
export class CpsjEspecialidadModule {}
