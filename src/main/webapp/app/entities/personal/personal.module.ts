import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import { GestWebAdminModule } from 'app/admin/admin.module';
import {
    PersonalComponent,
    PersonalDetailComponent,
    PersonalUpdateComponent,
    PersonalDeletePopupComponent,
    PersonalDeleteDialogComponent,
    personalRoute,
    personalPopupRoute
} from './';

const ENTITY_STATES = [...personalRoute, ...personalPopupRoute];

@NgModule({
    imports: [GestWebSharedModule, GestWebAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonalComponent,
        PersonalDetailComponent,
        PersonalUpdateComponent,
        PersonalDeleteDialogComponent,
        PersonalDeletePopupComponent
    ],
    entryComponents: [PersonalComponent, PersonalUpdateComponent, PersonalDeleteDialogComponent, PersonalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebPersonalModule {}
