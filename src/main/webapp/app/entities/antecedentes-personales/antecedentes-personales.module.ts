import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    AntecedentesPersonalesComponent,
    AntecedentesPersonalesDetailComponent,
    AntecedentesPersonalesUpdateComponent,
    AntecedentesPersonalesDeletePopupComponent,
    AntecedentesPersonalesDeleteDialogComponent,
    antecedentesPersonalesRoute,
    antecedentesPersonalesPopupRoute
} from './';

const ENTITY_STATES = [...antecedentesPersonalesRoute, ...antecedentesPersonalesPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AntecedentesPersonalesComponent,
        AntecedentesPersonalesDetailComponent,
        AntecedentesPersonalesUpdateComponent,
        AntecedentesPersonalesDeleteDialogComponent,
        AntecedentesPersonalesDeletePopupComponent
    ],
    entryComponents: [
        AntecedentesPersonalesComponent,
        AntecedentesPersonalesUpdateComponent,
        AntecedentesPersonalesDeleteDialogComponent,
        AntecedentesPersonalesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [AntecedentesPersonalesUpdateComponent],
})
export class GestWebAntecedentesPersonalesModule {}
