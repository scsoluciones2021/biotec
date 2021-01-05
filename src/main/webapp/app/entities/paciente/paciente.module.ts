import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import { CpsjAdminModule } from 'app/admin/admin.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import {
    PacienteComponent,
    PacienteDetailComponent,
    PacienteUpdateComponent,
    PacienteDeletePopupComponent,
    PacienteDeleteDialogComponent,
    PacienteModalComponent,
    pacienteRoute,
    pacientePopupRoute
} from './';
import {MultiSelectModule} from 'primeng/multiselect';
import {ListboxModule} from 'primeng/listbox';
import {CalendarModule} from 'primeng/calendar';

const ENTITY_STATES = [...pacienteRoute, ...pacientePopupRoute];

@NgModule({
    imports: [CpsjSharedModule, CpsjAdminModule,
        MultiSelectModule,
        ListboxModule,
         RouterModule.forChild(ENTITY_STATES),
    BrowserAnimationsModule,
    ReactiveFormsModule,
    AutoCompleteModule,
    CalendarModule, // Para calendario pequeño
],
    declarations: [
        PacienteComponent,
        PacienteDetailComponent,
        PacienteUpdateComponent,
        PacienteDeleteDialogComponent,
        PacienteDeletePopupComponent,
        PacienteModalComponent,
    ],
    entryComponents: [PacienteComponent, PacienteUpdateComponent, PacienteDeleteDialogComponent, PacienteDeletePopupComponent, PacienteModalComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjPacienteModule {}
