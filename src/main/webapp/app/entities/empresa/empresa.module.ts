import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    EmpresaComponent,
    EmpresaDetailComponent,
    EmpresaUpdateComponent,
    EmpresaDeletePopupComponent,
    EmpresaDeleteDialogComponent,
    empresaRoute,
    empresaPopupRoute
} from './';

const ENTITY_STATES = [...empresaRoute, ...empresaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmpresaComponent,
        EmpresaDetailComponent,
        EmpresaUpdateComponent,
        EmpresaDeleteDialogComponent,
        EmpresaDeletePopupComponent
    ],
    entryComponents: [EmpresaComponent, EmpresaUpdateComponent, EmpresaDeleteDialogComponent, EmpresaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebEmpresaModule {}
