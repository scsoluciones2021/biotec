import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    TurnoComponent,
    TurnoDetailComponent,
    TurnoUpdateComponent,
    TurnoDeletePopupComponent,
    TurnoDeleteDialogComponent,
    TurnoOnlineComponent,
    TurnoOnlineListadoComponent,
    turnoRoute,
    turnoPopupRoute
} from './';
import { OrderListModule } from 'primeng/orderlist';
import { PanelModule } from 'primeng/panel';
import { ButtonModule } from 'primeng/button';
import { KeyFilterModule } from 'primeng/keyfilter';
import { CalendarModule } from 'primeng/calendar';
import { ListboxModule } from 'primeng/listbox';
import { MessagesModule } from 'primeng/messages';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MultiSelectModule } from 'primeng/multiselect';
import { ToastModule } from 'primeng/toast';
import { ExcelService } from 'app/shared/util/excel.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TurnoProfesionalComponent } from './turno-profesional.component';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { GestWebPagoConsultaModule } from '../pago-consulta/pago-consulta.module';

const ENTITY_STATES = [...turnoRoute, ...turnoPopupRoute];

@NgModule({
    imports: [
        GestWebSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        OrderListModule,
        PanelModule,
        ButtonModule,
        KeyFilterModule,
        CalendarModule, // Para calendario pequeño
        //       FullCalendarModule, // Para calendario grande No funcion
        ListboxModule,
        FontAwesomeModule, // Para los íconos
        MultiSelectModule,
        MessagesModule, // Para los mensajes
        ToastModule, // Para mensajes,
        ProgressSpinnerModule,
        GestWebPagoConsultaModule
    ],
    declarations: [
        TurnoComponent,
        TurnoDetailComponent,
        TurnoUpdateComponent,
        TurnoDeleteDialogComponent,
        TurnoDeletePopupComponent,
        TurnoOnlineComponent,
        TurnoOnlineListadoComponent,
        TurnoProfesionalComponent
    ],
    entryComponents: [
        TurnoComponent,
        TurnoUpdateComponent,
        TurnoDeleteDialogComponent,
        TurnoDeletePopupComponent,
        TurnoOnlineComponent,
        TurnoOnlineListadoComponent,
        TurnoProfesionalComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [ExcelService]
})
export class GestWebTurnoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
