import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    EjercicioComponent,
    EjercicioDetailComponent,
    EjercicioUpdateComponent,
    EjercicioDeletePopupComponent,
    EjercicioDeleteDialogComponent,
    ejercicioRoute,
    ejercicioPopupRoute
} from './';

const ENTITY_STATES = [...ejercicioRoute, ...ejercicioPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EjercicioComponent,
        EjercicioDetailComponent,
        EjercicioUpdateComponent,
        EjercicioDeleteDialogComponent,
        EjercicioDeletePopupComponent
    ],
    entryComponents: [EjercicioComponent, EjercicioUpdateComponent, EjercicioDeleteDialogComponent, EjercicioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjEjercicioModule {}
