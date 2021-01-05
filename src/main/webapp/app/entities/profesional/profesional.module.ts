import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import { CpsjAdminModule } from 'app/admin/admin.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import {
    ProfesionalComponent,
    ProfesionalDetailComponent,
    ProfesionalUpdateComponent,
    ProfesionalDeletePopupComponent,
    ProfesionalDeleteDialogComponent,
    profesionalRoute,
    profesionalPopupRoute
} from './';


const ENTITY_STATES = [...profesionalRoute, ...profesionalPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, CpsjAdminModule, RouterModule.forChild(ENTITY_STATES), AutoCompleteModule, ],
    declarations: [
        ProfesionalComponent,
        ProfesionalDetailComponent,
        ProfesionalUpdateComponent,
        ProfesionalDeleteDialogComponent,
        ProfesionalDeletePopupComponent
    ],
    entryComponents: [ProfesionalComponent, ProfesionalUpdateComponent, ProfesionalDeleteDialogComponent, ProfesionalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjProfesionalModule {}
