import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    ObsAntecFamiliarComponent,
    ObsAntecFamiliarDetailComponent,
    ObsAntecFamiliarUpdateComponent,
    ObsAntecFamiliarDeletePopupComponent,
    ObsAntecFamiliarDeleteDialogComponent,
    obsAntecFamiliarRoute,
    obsAntecFamiliarPopupRoute
} from './';

const ENTITY_STATES = [...obsAntecFamiliarRoute, ...obsAntecFamiliarPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ObsAntecFamiliarComponent,
        ObsAntecFamiliarDetailComponent,
        ObsAntecFamiliarUpdateComponent,
        ObsAntecFamiliarDeleteDialogComponent,
        ObsAntecFamiliarDeletePopupComponent
    ],
    entryComponents: [
        ObsAntecFamiliarComponent,
        ObsAntecFamiliarUpdateComponent,
        ObsAntecFamiliarDeleteDialogComponent,
        ObsAntecFamiliarDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebObsAntecFamiliarModule {}
