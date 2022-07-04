import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared/shared.module';
import { Gruposcie10Component } from './gruposcie10.component';
import { Gruposcie10DetailComponent } from './gruposcie10-detail.component';
import { Gruposcie10UpdateComponent } from './gruposcie10-update.component';
import { Gruposcie10DeleteDialogComponent } from './gruposcie10-delete-dialog.component';
import { gruposcie10Route } from './gruposcie10.route';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(gruposcie10Route), FormsModule, ReactiveFormsModule],
    declarations: [Gruposcie10Component, Gruposcie10DetailComponent, Gruposcie10UpdateComponent, Gruposcie10DeleteDialogComponent],
    entryComponents: [Gruposcie10DeleteDialogComponent]
})
export class GestWebGruposcie10Module {}
