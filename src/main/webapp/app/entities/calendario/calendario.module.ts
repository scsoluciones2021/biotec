import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    CalendarioComponent,
    CalendarioDetailComponent,
    CalendarioUpdateComponent,
    CalendarioDeletePopupComponent,
    CalendarioDeleteDialogComponent,
    calendarioRoute,
    calendarioPopupRoute,
} from './';

// Calendario
import { FlatpickrModule } from 'angularx-flatpickr';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

const ENTITY_STATES = [...calendarioRoute, ...calendarioPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES),
        CommonModule,
    FormsModule,
    NgbModalModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
        provide: DateAdapter,
        useFactory: adapterFactory
      }),
    ],
    declarations: [
        CalendarioComponent,
        CalendarioDetailComponent,
        CalendarioUpdateComponent,
        CalendarioDeleteDialogComponent,
        CalendarioDeletePopupComponent
    ],
    entryComponents: [CalendarioComponent, CalendarioUpdateComponent, CalendarioDeleteDialogComponent, CalendarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjCalendarioModule {}
