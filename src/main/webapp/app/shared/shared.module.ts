import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { GestWebSharedLibsModule, GestWebSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, FindLanguageFromKeyPipe } from './';

@NgModule({
    imports: [GestWebSharedLibsModule, GestWebSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, FindLanguageFromKeyPipe],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [GestWebSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebSharedModule {}
