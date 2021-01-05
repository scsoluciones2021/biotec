import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
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
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
export class CpsjObsAntecPersonalModule {}
