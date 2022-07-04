import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import { GestWebAdminModule } from 'app/admin/admin.module';
import {
    ProfesionalComponent,
    ProfesionalDetailComponent,
    ProfesionalUpdateComponent,
    ProfesionalDeletePopupComponent,
    ProfesionalDeleteDialogComponent,
    profesionalRoute,
    profesionalPopupRoute
} from './';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

const ENTITY_STATES = [...profesionalRoute, ...profesionalPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, GestWebAdminModule, RouterModule.forChild(ENTITY_STATES), AutoCompleteModule],
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
export class GestWebProfesionalModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
