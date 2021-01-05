import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    DiagnosticoComponent,
    DiagnosticoDetailComponent,
    DiagnosticoUpdateComponent,
    DiagnosticoDeletePopupComponent,
    DiagnosticoDeleteDialogComponent,
    diagnosticoRoute,
    diagnosticoPopupRoute
} from './';

const ENTITY_STATES = [...diagnosticoRoute, ...diagnosticoPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiagnosticoComponent,
        DiagnosticoDetailComponent,
        DiagnosticoUpdateComponent,
        DiagnosticoDeleteDialogComponent,
        DiagnosticoDeletePopupComponent
    ],
    entryComponents: [DiagnosticoComponent, DiagnosticoUpdateComponent, DiagnosticoDeleteDialogComponent, DiagnosticoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjDiagnosticoModule {}
