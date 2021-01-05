import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import { CpsjAdminModule } from 'app/admin/admin.module';
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
    imports: [CpsjSharedModule, CpsjAdminModule, RouterModule.forChild(ENTITY_STATES)],
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
export class CpsjPersonalModule {}
