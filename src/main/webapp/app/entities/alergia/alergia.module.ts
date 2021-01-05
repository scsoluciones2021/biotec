import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    AlergiaComponent,
    AlergiaDetailComponent,
    AlergiaUpdateComponent,
    AlergiaDeletePopupComponent,
    AlergiaDeleteDialogComponent,
    alergiaRoute,
    alergiaPopupRoute
} from './';

const ENTITY_STATES = [...alergiaRoute, ...alergiaPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AlergiaComponent,
        AlergiaDetailComponent,
        AlergiaUpdateComponent,
        AlergiaDeleteDialogComponent,
        AlergiaDeletePopupComponent
    ],
    entryComponents: [AlergiaComponent, AlergiaUpdateComponent, AlergiaDeleteDialogComponent, AlergiaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjAlergiaModule {}
