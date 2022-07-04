import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    TituloShortComponent,
    TituloShortDetailComponent,
    TituloShortUpdateComponent,
    TituloShortDeletePopupComponent,
    TituloShortDeleteDialogComponent,
    tituloShortRoute,
    tituloShortPopupRoute
} from './';

const ENTITY_STATES = [...tituloShortRoute, ...tituloShortPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TituloShortComponent,
        TituloShortDetailComponent,
        TituloShortUpdateComponent,
        TituloShortDeleteDialogComponent,
        TituloShortDeletePopupComponent
    ],
    entryComponents: [TituloShortComponent, TituloShortUpdateComponent, TituloShortDeleteDialogComponent, TituloShortDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebTituloShortModule {}
