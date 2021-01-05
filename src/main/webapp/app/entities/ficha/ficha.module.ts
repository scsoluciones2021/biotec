import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import {
    FichaComponent,
    FichaDetailComponent,
    FichaUpdateComponent,
    FichaDeletePopupComponent,
    FichaDeleteDialogComponent,
    fichaRoute,
    fichaPopupRoute
} from './';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { FileUploadModule } from 'primeng/fileupload';
import { ToolbarModule } from 'primeng/toolbar';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { Component } from '@angular/core';
const ENTITY_STATES = [...fichaRoute, ...fichaPopupRoute];
import { MultiSelectModule } from 'primeng/multiselect';
import { CpsjAntecedentesFamiliaresModule } from 'app/entities/antecedentes-familiares/antecedentes-familiares.module.ts';
import { CpsjAntecedentesPersonalesModule } from 'app/entities/antecedentes-personales/antecedentes-personales.module.ts';
import { TreeModule } from 'primeng/primeng';
import { PanelModule } from 'primeng/panel';

@NgModule({
    imports: [
        CpsjSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        BrowserAnimationsModule,
        ReactiveFormsModule,
        AutoCompleteModule,
        FileUploadModule,
        CpsjAntecedentesFamiliaresModule,
        CpsjAntecedentesPersonalesModule,
        ToolbarModule,
        TreeModule,
        MultiSelectModule,
        PanelModule
        /*  ToastModule,
    MessageService,
    Component*/
    ],
    declarations: [FichaComponent, FichaDetailComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
    entryComponents: [FichaComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjFichaModule {}
