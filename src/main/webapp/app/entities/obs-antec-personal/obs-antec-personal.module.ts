import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    ObsAntecPersonalComponent,
    ObsAntecPersonalDetailComponent,
    ObsAntecPersonalUpdateComponent,
    ObsAntecPersonalDeletePopupComponent,
    ObsAntecPersonalDeleteDialogComponent,
    obsAntecPersonalRoute,
    obsAntecPersonalPopupRoute
} from './';

const ENTITY_STATES = [...obsAntecPersonalRoute, ...obsAntecPersonalPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ObsAntecPersonalComponent,
        ObsAntecPersonalDetailComponent,
        ObsAntecPersonalUpdateComponent,
        ObsAntecPersonalDeleteDialogComponent,
        ObsAntecPersonalDeletePopupComponent
    ],
    entryComponents: [
        ObsAntecPersonalComponent,
        ObsAntecPersonalUpdateComponent,
        ObsAntecPersonalDeleteDialogComponent,
        ObsAntecPersonalDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebObsAntecPersonalModule {}
