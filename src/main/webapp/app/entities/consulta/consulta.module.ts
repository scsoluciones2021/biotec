import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ToggleButtonComponent } from '../../layouts/toogle-button/toggle-button.component';
import { GestWebSharedModule } from 'app/shared';
import {
    ConsultaComponent,
    ConsultaDetailComponent,
    ConsultaUpdateComponent,
    ConsultaDeletePopupComponent,
    ConsultaDeleteDialogComponent,
    consultaRoute,
    consultaPopupRoute
} from './';
import { ConsultaModalComponent } from './consulta-modal.component';
const ENTITY_STATES = [...consultaRoute, ...consultaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConsultaComponent,
        ConsultaDetailComponent,
        ConsultaUpdateComponent,
        ConsultaDeleteDialogComponent,
        ConsultaDeletePopupComponent,
        ConsultaModalComponent,
        ToggleButtonComponent
    ],
    entryComponents: [
        ConsultaComponent,
        ConsultaUpdateComponent,
        ConsultaDeleteDialogComponent,
        ConsultaDeletePopupComponent,
        ConsultaModalComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebConsultaModule {}
