import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

import { GestWebSharedModule } from 'app/shared';
import {
    PagoConsultaComponent,
    PagoConsultaDetailComponent,
    PagoConsultaUpdateComponent,
    PagoConsultaDeletePopupComponent,
    PagoConsultaDeleteDialogComponent,
    pagoConsultaRoute,
    pagoConsultaPopupRoute
} from './';

const ENTITY_STATES = [...pagoConsultaRoute, ...pagoConsultaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PagoConsultaComponent,
        PagoConsultaDetailComponent,
        PagoConsultaUpdateComponent,
        PagoConsultaDeleteDialogComponent,
        PagoConsultaDeletePopupComponent
    ],
    entryComponents: [
        PagoConsultaComponent,
        PagoConsultaUpdateComponent,
        PagoConsultaDeleteDialogComponent,
        PagoConsultaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    exports: [PagoConsultaUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebPagoConsultaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
