import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared/shared.module';
import { Diagnosticoscie10Component } from './diagnosticoscie10.component';
import { Diagnosticoscie10DetailComponent } from './diagnosticoscie10-detail.component';
import { Diagnosticoscie10UpdateComponent } from './diagnosticoscie10-update.component';
import { Diagnosticoscie10DeleteDialogComponent } from './diagnosticoscie10-delete-dialog.component';
import { diagnosticoscie10Route } from './diagnosticoscie10.route';

import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(diagnosticoscie10Route), FormsModule, ReactiveFormsModule],
    declarations: [
        Diagnosticoscie10Component,
        Diagnosticoscie10DetailComponent,
        Diagnosticoscie10UpdateComponent,
        Diagnosticoscie10DeleteDialogComponent
    ],
    entryComponents: [Diagnosticoscie10DeleteDialogComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebDiagnosticoscie10Module {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
