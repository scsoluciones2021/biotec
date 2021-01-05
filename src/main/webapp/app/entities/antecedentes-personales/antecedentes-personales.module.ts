import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
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
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
export class CpsjAntecedentesPersonalesModule {}
