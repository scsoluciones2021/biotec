import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    AgrupadorOSComponent,
    AgrupadorOSDetailComponent,
    AgrupadorOSUpdateComponent,
    AgrupadorOSDeletePopupComponent,
    AgrupadorOSDeleteDialogComponent,
    agrupadorOSRoute,
    agrupadorOSPopupRoute
} from './';

const ENTITY_STATES = [...agrupadorOSRoute, ...agrupadorOSPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AgrupadorOSComponent,
        AgrupadorOSDetailComponent,
        AgrupadorOSUpdateComponent,
        AgrupadorOSDeleteDialogComponent,
        AgrupadorOSDeletePopupComponent
    ],
    entryComponents: [AgrupadorOSComponent, AgrupadorOSUpdateComponent, AgrupadorOSDeleteDialogComponent, AgrupadorOSDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjAgrupadorOSModule {}
