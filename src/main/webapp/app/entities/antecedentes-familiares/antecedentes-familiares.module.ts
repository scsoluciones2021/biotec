import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    AntecedentesFamiliaresComponent,
    AntecedentesFamiliaresDetailComponent,
    AntecedentesFamiliaresUpdateComponent,
    AntecedentesFamiliaresDeletePopupComponent,
    AntecedentesFamiliaresDeleteDialogComponent,
    antecedentesFamiliaresRoute,
    antecedentesFamiliaresPopupRoute
} from './';

const ENTITY_STATES = [...antecedentesFamiliaresRoute, ...antecedentesFamiliaresPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AntecedentesFamiliaresComponent,
        AntecedentesFamiliaresDetailComponent,
        AntecedentesFamiliaresUpdateComponent,
        AntecedentesFamiliaresDeleteDialogComponent,
        AntecedentesFamiliaresDeletePopupComponent
    ],
    entryComponents: [
        AntecedentesFamiliaresComponent,
        AntecedentesFamiliaresUpdateComponent,
        AntecedentesFamiliaresDeleteDialogComponent,
        AntecedentesFamiliaresDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [AntecedentesFamiliaresUpdateComponent],
})
export class GestWebAntecedentesFamiliaresModule {}
