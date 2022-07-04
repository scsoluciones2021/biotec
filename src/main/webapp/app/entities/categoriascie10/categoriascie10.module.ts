import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared/shared.module';
import { Categoriascie10Component } from './categoriascie10.component';
import { Categoriascie10DetailComponent } from './categoriascie10-detail.component';
import { Categoriascie10UpdateComponent } from './categoriascie10-update.component';
import { Categoriascie10DeleteDialogComponent } from './categoriascie10-delete-dialog.component';
import { categoriascie10Route } from './categoriascie10.route';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(categoriascie10Route), FormsModule, ReactiveFormsModule],
    declarations: [
        Categoriascie10Component,
        Categoriascie10DetailComponent,
        Categoriascie10UpdateComponent,
        Categoriascie10DeleteDialogComponent
    ],
    entryComponents: [Categoriascie10DeleteDialogComponent]
})
export class GestWebCategoriascie10Module {}
