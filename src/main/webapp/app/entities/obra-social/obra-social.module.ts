import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    ObraSocialComponent,
    ObraSocialDetailComponent,
    ObraSocialUpdateComponent,
    ObraSocialDeletePopupComponent,
    ObraSocialDeleteDialogComponent,
    obraSocialRoute,
    obraSocialPopupRoute
} from './';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

const ENTITY_STATES = [...obraSocialRoute, ...obraSocialPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ObraSocialComponent,
        ObraSocialDetailComponent,
        ObraSocialUpdateComponent,
        ObraSocialDeleteDialogComponent,
        ObraSocialDeletePopupComponent
    ],
    entryComponents: [ObraSocialComponent, ObraSocialUpdateComponent, ObraSocialDeleteDialogComponent, ObraSocialDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebObraSocialModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
         if (languageKey !== undefined) {
           this.languageService.changeLanguage(languageKey);
         }
        });
       }
}
