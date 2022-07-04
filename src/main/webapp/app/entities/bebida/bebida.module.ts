import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import {
    BebidaComponent,
    BebidaDetailComponent,
    BebidaUpdateComponent,
    BebidaDeletePopupComponent,
    BebidaDeleteDialogComponent,
    bebidaRoute,
    bebidaPopupRoute
} from './';

const ENTITY_STATES = [...bebidaRoute, ...bebidaPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BebidaComponent, BebidaDetailComponent, BebidaUpdateComponent, BebidaDeleteDialogComponent, BebidaDeletePopupComponent],
    entryComponents: [BebidaComponent, BebidaUpdateComponent, BebidaDeleteDialogComponent, BebidaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebBebidaModule {}
