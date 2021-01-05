import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    FamiliarComponent,
    FamiliarDetailComponent,
    FamiliarUpdateComponent,
    FamiliarDeletePopupComponent,
    FamiliarDeleteDialogComponent,
    familiarRoute,
    familiarPopupRoute
} from './';

const ENTITY_STATES = [...familiarRoute, ...familiarPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FamiliarComponent,
        FamiliarDetailComponent,
        FamiliarUpdateComponent,
        FamiliarDeleteDialogComponent,
        FamiliarDeletePopupComponent
    ],
    entryComponents: [FamiliarComponent, FamiliarUpdateComponent, FamiliarDeleteDialogComponent, FamiliarDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjFamiliarModule {}
