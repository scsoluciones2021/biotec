import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestWebSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { JhiLanguageService } from 'ng-jhipster';

@NgModule({
    imports: [GestWebSharedModule, RouterModule.forChild([HOME_ROUTE])],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebHomeModule {}
