import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    RegimenComponent,
    RegimenDetailComponent,
    RegimenUpdateComponent,
    RegimenDeletePopupComponent,
    RegimenDeleteDialogComponent,
    regimenRoute,
    regimenPopupRoute
} from './';

const ENTITY_STATES = [...regimenRoute, ...regimenPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegimenComponent,
        RegimenDetailComponent,
        RegimenUpdateComponent,
        RegimenDeleteDialogComponent,
        RegimenDeletePopupComponent
    ],
    entryComponents: [RegimenComponent, RegimenUpdateComponent, RegimenDeleteDialogComponent, RegimenDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjRegimenModule {}
