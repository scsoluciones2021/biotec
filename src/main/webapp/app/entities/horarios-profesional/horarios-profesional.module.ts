import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    HorariosProfesionalComponent,
    HorariosProfesionalDetailComponent,
    HorariosProfesionalUpdateComponent,
    HorariosProfesionalDeletePopupComponent,
    HorariosProfesionalDeleteDialogComponent,
    horariosProfesionalRoute,
    horariosProfesionalPopupRoute
} from './';

import { MultiSelectModule } from 'primeng/multiselect';
import { CheckboxModule } from 'primeng/checkbox';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { KeyFilterModule } from 'primeng/keyfilter';
import { InputMaskModule } from 'primeng/inputmask';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { ForDiasPipe } from '../../shared/util/forDias.pipe';
import { CalendarModule } from 'primeng/calendar';

import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

const ENTITY_STATES = [...horariosProfesionalRoute, ...horariosProfesionalPopupRoute];

@NgModule({
    imports: [
        GestWebSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        // Módulos para días
        MultiSelectModule,
        // CheckboxModule,
        // Para los íconos
        FontAwesomeModule,
        // Para los horarios
        KeyFilterModule,
        InputMaskModule,
        CalendarModule,
        // Para los profesionales
        BrowserAnimationsModule,
        ReactiveFormsModule,
        AutoCompleteModule
    ],
    declarations: [
        HorariosProfesionalComponent,
        HorariosProfesionalDetailComponent,
        HorariosProfesionalUpdateComponent,
        HorariosProfesionalDeleteDialogComponent,
        HorariosProfesionalDeletePopupComponent,
        // Para mostrar días
        ForDiasPipe
    ],
    entryComponents: [
        HorariosProfesionalComponent,
        HorariosProfesionalUpdateComponent,
        HorariosProfesionalDeleteDialogComponent,
        HorariosProfesionalDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebHorariosProfesionalModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
