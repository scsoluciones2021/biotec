import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CpsjSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { JhiLanguageService } from 'ng-jhipster';

@NgModule({
    imports: [CpsjSharedModule, RouterModule.forChild([HOME_ROUTE])],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjHomeModule {}
