import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    EnfermedadComponent,
    EnfermedadDetailComponent,
    EnfermedadUpdateComponent,
    EnfermedadDeletePopupComponent,
    EnfermedadDeleteDialogComponent,
    enfermedadRoute,
    enfermedadPopupRoute
} from './';

const ENTITY_STATES = [...enfermedadRoute, ...enfermedadPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnfermedadComponent,
        EnfermedadDetailComponent,
        EnfermedadUpdateComponent,
        EnfermedadDeleteDialogComponent,
        EnfermedadDeletePopupComponent
    ],
    entryComponents: [EnfermedadComponent, EnfermedadUpdateComponent, EnfermedadDeleteDialogComponent, EnfermedadDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjEnfermedadModule {}
