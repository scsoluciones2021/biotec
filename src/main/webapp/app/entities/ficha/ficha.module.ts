import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    FichaComponent,
    FichaDetailComponent,
    FichaUpdateComponent,
    FichaDeletePopupComponent,
    FichaDeleteDialogComponent,
    fichaRoute,
    fichaPopupRoute
} from './';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { FileUploadModule } from 'primeng/fileupload';
import { ToolbarModule } from 'primeng/toolbar';
import { MultiSelectModule } from 'primeng/multiselect';
import { TreeModule } from 'primeng/primeng';
import { PanelModule } from 'primeng/panel';

const ENTITY_STATES = [...fichaRoute, ...fichaPopupRoute];
@NgModule({
    imports: [
        GestWebSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        BrowserAnimationsModule,
        ReactiveFormsModule,
        AutoCompleteModule,
        FileUploadModule,
        ToolbarModule,
        TreeModule,
        MultiSelectModule,
        PanelModule
    ],
    declarations: [FichaComponent, FichaDetailComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
    entryComponents: [FichaComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebFichaModule {}
