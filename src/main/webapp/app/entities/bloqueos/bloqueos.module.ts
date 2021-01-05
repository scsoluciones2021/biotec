import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    BloqueosComponent,
    BloqueosDetailComponent,
    BloqueosUpdateComponent,
    BloqueosDeletePopupComponent,
    BloqueosDeleteDialogComponent,
    bloqueosRoute,
    bloqueosPopupRoute
} from './';
import { JhiLanguageService } from 'ng-jhipster/src/language';

const ENTITY_STATES = [...bloqueosRoute, ...bloqueosPopupRoute];

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BloqueosComponent,
        BloqueosDetailComponent,
        BloqueosUpdateComponent,
        BloqueosDeleteDialogComponent,
        BloqueosDeletePopupComponent
    ],
    entryComponents: [BloqueosComponent, BloqueosUpdateComponent, BloqueosDeleteDialogComponent, BloqueosDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjBloqueosModule {
   /* constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }*/
}
