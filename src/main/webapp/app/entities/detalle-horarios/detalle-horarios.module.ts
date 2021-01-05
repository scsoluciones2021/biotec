import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    DetalleHorariosComponent,
    DetalleHorariosDetailComponent,
    DetalleHorariosUpdateComponent,
    DetalleHorariosDeletePopupComponent,
    DetalleHorariosDeleteDialogComponent,
    detalleHorariosRoute,
    detalleHorariosPopupRoute
} from './';
import { CheckboxModule } from 'primeng/checkbox';
import { InputMaskModule } from 'primeng/inputmask';
import { DetalleHorariosModalComponent } from './detalle-horarios-modal.component';

const ENTITY_STATES = [...detalleHorariosRoute, ...detalleHorariosPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES),
        // Para los días
        CheckboxModule,
        // Para la hora desde/hasta
        InputMaskModule],
    declarations: [
        DetalleHorariosComponent,
        DetalleHorariosDetailComponent,
        DetalleHorariosUpdateComponent,
        DetalleHorariosDeleteDialogComponent,
        DetalleHorariosDeletePopupComponent,
        DetalleHorariosModalComponent,
    ],
    entryComponents: [
        DetalleHorariosComponent,
        DetalleHorariosUpdateComponent,
        DetalleHorariosDeleteDialogComponent,
        DetalleHorariosDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjDetalleHorariosModule {}
