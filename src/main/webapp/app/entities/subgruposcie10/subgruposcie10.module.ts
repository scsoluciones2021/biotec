import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import { GestWebAdminModule } from 'app/admin/admin.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {
    Subgruposcie10Component,
    Subgruposcie10DetailComponent,
    Subgruposcie10UpdateComponent,
    Subgruposcie10DeleteDialogComponent,
    subgruposcie10Route
} from './';

@NgModule({
    imports: [GestWebSharedModule, GestWebAdminModule, RouterModule.forChild(subgruposcie10Route), FormsModule, ReactiveFormsModule],
    declarations: [
        Subgruposcie10Component,
        Subgruposcie10DetailComponent,
        Subgruposcie10UpdateComponent,
        Subgruposcie10DeleteDialogComponent
    ],
    entryComponents: [
        Subgruposcie10Component,
        Subgruposcie10DetailComponent,
        Subgruposcie10UpdateComponent,
        Subgruposcie10DeleteDialogComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebSubgruposcie10Module {}
