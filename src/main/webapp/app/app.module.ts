import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager, JhiLanguageService } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { CpsjSharedModule } from 'app/shared';
import { CpsjCoreModule } from 'app/core';
import { CpsjAppRoutingModule } from './app-routing.module';
import { CpsjHomeModule } from './home/home.module';
import { CpsjAccountModule } from './account/account.module';
import { CpsjEntityModule } from './entities/entity.module';
import { GalleriaModule } from 'primeng/galleria';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent,  ActiveMenuDirective, ErrorComponent } from './layouts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { JhiLanguageHelper } from './core/language/language.helper';

@NgModule({
    imports: [
        BrowserModule,
        CpsjAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        CpsjSharedModule,
        CpsjCoreModule,
        CpsjHomeModule,
        CpsjAccountModule,
        CpsjEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        GalleriaModule,
        NgbModule.forRoot(),
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent,  ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent],
})
export class CpsjAppModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
         if (languageKey !== undefined) {
           this.languageService.changeLanguage(languageKey);
         }
        });
       }
}
